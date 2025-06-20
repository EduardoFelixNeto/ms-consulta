package br.com.ufpr.tads.dac.msconsulta.service.impl;

import br.com.ufpr.tads.dac.msconsulta.dto.AgendamentoDTO;
import br.com.ufpr.tads.dac.msconsulta.entity.Agendamento;
import br.com.ufpr.tads.dac.msconsulta.entity.Consulta;
import br.com.ufpr.tads.dac.msconsulta.entity.StatusAgendamento;
import br.com.ufpr.tads.dac.msconsulta.entity.StatusConsulta;
import br.com.ufpr.tads.dac.msconsulta.repository.AgendamentoRepository;
import br.com.ufpr.tads.dac.msconsulta.repository.ConsultaRepository;
import br.com.ufpr.tads.dac.msconsulta.service.AgendamentoService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AgendamentoServiceImpl implements AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final ConsultaRepository consultaRepository;

    public AgendamentoServiceImpl(AgendamentoRepository agendamentoRepository, ConsultaRepository consultaRepository) {
        this.agendamentoRepository = agendamentoRepository;
        this.consultaRepository = consultaRepository;
    }

    @Override
    public AgendamentoDTO agendarConsulta(Long idConsulta, AgendamentoDTO dto) {
        Consulta consulta = consultaRepository.findById(idConsulta)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));

        if (consulta.getStatus() != StatusConsulta.DISPONIVEL) {
            throw new IllegalStateException("Consulta não está disponível");
        }

        Agendamento agendamento = new Agendamento();
        agendamento.setConsulta(consulta);
        agendamento.setIdPaciente(dto.idPaciente);
        agendamento.setDataHoraAgendamento(LocalDateTime.now());
        agendamento.setCodigoAgendamento(gerarCodigo());
        agendamento.setPontosUtilizados(dto.pontosUtilizados);
        agendamento.setValorPagoComplementar(dto.valorPagoComplementar);
        agendamento.setStatus(StatusAgendamento.CRIADO);

        agendamento = agendamentoRepository.save(agendamento);
        return toDTO(agendamento);
    }

    @Override
    public void realizarCheckin(Long idAgendamento) {
        Agendamento a = agendamentoRepository.findById(idAgendamento).orElseThrow();
        a.setStatus(StatusAgendamento.CHECKIN);
        agendamentoRepository.save(a);
    }

    @Override
    public void confirmarComparecimento(Long idAgendamento) {
        Agendamento a = agendamentoRepository.findById(idAgendamento).orElseThrow();
        a.setStatus(StatusAgendamento.COMPARECEU);
        agendamentoRepository.save(a);
    }

    @Override
    public void cancelarAgendamento(Long idAgendamento) {
        Agendamento a = agendamentoRepository.findById(idAgendamento).orElseThrow();
        a.setStatus(StatusAgendamento.CANCELADO);
        agendamentoRepository.save(a);
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
}

