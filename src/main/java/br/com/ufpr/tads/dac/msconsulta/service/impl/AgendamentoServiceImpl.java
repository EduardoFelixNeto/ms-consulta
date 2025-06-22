package br.com.ufpr.tads.dac.msconsulta.service.impl;

import br.com.ufpr.tads.dac.msconsulta.client.UsuarioClient;
import br.com.ufpr.tads.dac.msconsulta.dto.AgendamentoDTO;
import br.com.ufpr.tads.dac.msconsulta.dto.CompraPontosDTO;
import br.com.ufpr.tads.dac.msconsulta.dto.DebitoPontosDTO;
import br.com.ufpr.tads.dac.msconsulta.entity.Agendamento;
import br.com.ufpr.tads.dac.msconsulta.entity.Consulta;
import br.com.ufpr.tads.dac.msconsulta.entity.StatusAgendamento;
import br.com.ufpr.tads.dac.msconsulta.entity.StatusConsulta;
import br.com.ufpr.tads.dac.msconsulta.repository.AgendamentoRepository;
import br.com.ufpr.tads.dac.msconsulta.repository.ConsultaRepository;
import br.com.ufpr.tads.dac.msconsulta.service.AgendamentoService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AgendamentoServiceImpl implements AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final ConsultaRepository consultaRepository;
    private final UsuarioClient usuarioClient;

    public AgendamentoServiceImpl(AgendamentoRepository agendamentoRepository, ConsultaRepository consultaRepository, UsuarioClient usuarioClient) {
        this.agendamentoRepository = agendamentoRepository;
        this.consultaRepository = consultaRepository;
        this.usuarioClient = usuarioClient;
    }

    @Override
    public AgendamentoDTO agendarConsulta(Long idConsulta, AgendamentoDTO dto) {
        Consulta consulta = consultaRepository.findById(idConsulta)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));

        Agendamento agendamento = new Agendamento();
        agendamento.setConsulta(consulta);
        agendamento.setIdPaciente(dto.getIdPaciente());
        agendamento.setCodigoAgendamento(UUID.randomUUID().toString());
        agendamento.setStatus(StatusAgendamento.CRIADO);
        agendamento.setPontosUtilizados(dto.getPontosUtilizados());

        BigDecimal valorPontos = BigDecimal.valueOf(dto.getPontosUtilizados()).multiply(BigDecimal.valueOf(1)); // 1 ponto = R$1,00
        BigDecimal valorFinal = consulta.getValor().subtract(valorPontos);
        agendamento.setValorPagoComplementar(valorFinal.max(BigDecimal.ZERO));
        agendamento.setDataHoraAgendamento(LocalDateTime.now());

        agendamentoRepository.save(agendamento);

        if (dto.getPontosUtilizados() > 0) {
            DebitoPontosDTO debito = new DebitoPontosDTO();
            debito.setQuantidade(dto.getPontosUtilizados());
            debito.setDescricao("AGENDAMENTO DE CONSULTA");

            usuarioClient.debitarPontos(dto.getIdPaciente(), debito);
        }

        return toDTO(agendamento);
    }

    @Override
    public void realizarCheckin(Long idAgendamento) {
        Agendamento agendamento = agendamentoRepository.findById(idAgendamento).orElseThrow();

        if (agendamento.getStatus() != StatusAgendamento.CRIADO) {
            throw new IllegalStateException("Só é possível fazer check-in de agendamentos com status CRIADO.");
        }

        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime dataConsulta = agendamento.getDataHoraAgendamento();
        long horasFaltando = Duration.between(agora, dataConsulta).toHours();

        if (horasFaltando > 48 || horasFaltando < 0) {
            throw new IllegalStateException("Check-in permitido apenas nas 48h anteriores à consulta.");
        }

        agendamento.setStatus(StatusAgendamento.CHECKIN);
        agendamentoRepository.save(agendamento);
    }

    @Override
    public void confirmarComparecimento(Long idAgendamento) {
        Agendamento a = agendamentoRepository.findById(idAgendamento).orElseThrow();
        a.setStatus(StatusAgendamento.COMPARECEU);
        agendamentoRepository.save(a);
    }

    @Override
    public void cancelarAgendamento(Long idAgendamento) {
        Agendamento agendamento = agendamentoRepository.findById(idAgendamento)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));

        // Verifica se o status permite cancelamento
        if (agendamento.getStatus() != StatusAgendamento.CRIADO &&
                agendamento.getStatus() != StatusAgendamento.CHECKIN) {
            throw new IllegalStateException("Agendamento só pode ser cancelado se estiver com status CRIADO ou CHECKIN");
        }

        // Atualiza o status
        agendamento.setStatus(StatusAgendamento.CANCELADO);
        agendamentoRepository.save(agendamento);

        // Devolve os pontos, se necessário
        if (agendamento.getPontosUtilizados() > 0) {
            // Ao invés de debitar, vamos "comprar" pontos de volta (crédito)
            CompraPontosDTO devolucao = new CompraPontosDTO(); // reutiliza a DTO de débito com lógica de crédito
            devolucao.setQuantidadePontos(agendamento.getPontosUtilizados());
            devolucao.setDescricao("CANCELAMENTO DE AGENDAMENTO");

            usuarioClient.comprarPontos(agendamento.getIdPaciente(), devolucao); // devolve pontos ao paciente
        }
    }

    private AgendamentoDTO toDTO(Agendamento a) {
        AgendamentoDTO dto = new AgendamentoDTO();
        dto.id = a.getId();
        dto.codigoAgendamento = a.getCodigoAgendamento();
        dto.idConsulta = a.getConsulta().getId();
        dto.idPaciente = a.getIdPaciente();
        dto.dataHoraAgendamento = a.getDataHoraAgendamento();
        dto.pontosUtilizados = a.getPontosUtilizados();
        dto.valorPagoComplementar = a.getValorPagoComplementar();
        dto.status = a.getStatus().name();
        return dto;
    }

    private String gerarCodigo() {
        return "AG" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }

    @Override
    public List<AgendamentoDTO> listarPorPaciente(Long pacienteId) {
        List<Agendamento> agendamentos = agendamentoRepository.findByIdPaciente(pacienteId);
        return agendamentos.stream()
                .map(this::toDTO)
                .toList();
    }

}

