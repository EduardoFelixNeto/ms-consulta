package br.com.ufpr.tads.dac.msconsulta.service.impl;

import br.com.ufpr.tads.dac.msconsulta.client.UsuarioClient;
import br.com.ufpr.tads.dac.msconsulta.dto.*;
import br.com.ufpr.tads.dac.msconsulta.entity.*;
import br.com.ufpr.tads.dac.msconsulta.repository.AgendamentoRepository;
import br.com.ufpr.tads.dac.msconsulta.repository.ConsultaRepository;
import br.com.ufpr.tads.dac.msconsulta.repository.EspecialidadeRepository;
import br.com.ufpr.tads.dac.msconsulta.service.ConsultaService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultaServiceImpl implements ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final EspecialidadeRepository especialidadeRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final UsuarioClient usuarioClient;


    public ConsultaServiceImpl(ConsultaRepository consultaRepository, EspecialidadeRepository especialidadeRepository, AgendamentoRepository agendamentoRepository, UsuarioClient usuarioClient) {
        this.consultaRepository = consultaRepository;
        this.especialidadeRepository = especialidadeRepository;
        this.agendamentoRepository = agendamentoRepository;
        this.usuarioClient = usuarioClient;
    }

    @Override
    public ConsultaDTO cadastrarConsulta(ConsultaDTO dto) {
        Especialidade esp = especialidadeRepository.findById(dto.especialidadeCodigo)
                .orElseThrow(() -> new RuntimeException("Especialidade não encontrada"));

        Consulta consulta = new Consulta();
        consulta.setDataHora(dto.dataHora);
        consulta.setEspecialidade(esp);
        consulta.setMedico(dto.medico);
        consulta.setValor(dto.valor);
        consulta.setVagas(dto.vagas);
        consulta.setStatus(StatusConsulta.DISPONIVEL);

        consulta = consultaRepository.save(consulta);

        return toDTO(consulta);
    }

    @Override
    public List<ConsultaDTO> buscarConsultas(String especialidade, String medico) {
        return consultaRepository.buscarPorFiltros(especialidade, medico).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void cancelarConsulta(Long id) {
        Consulta consulta = consultaRepository.findById(id).orElseThrow();
        consulta.setStatus(StatusConsulta.CANCELADA);
        consultaRepository.save(consulta);
    }

    @Override
    public void finalizarConsulta(Long id) {
        Consulta consulta = consultaRepository.findById(id).orElseThrow();
        consulta.setStatus(StatusConsulta.FINALIZADA);
        consultaRepository.save(consulta);
    }

    @Override
    public List<ConsultaDashboardDTO> listarConsultasProximas48Horas() {
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime limite = agora.plusHours(48);

        return consultaRepository.listarConsultasProximas48Horas(agora, limite)
                .stream()
                .map(this::toDashboardDTO)
                .collect(Collectors.toList());
    }

    private ConsultaDashboardDTO toDashboardDTO(Consulta c) {
        ConsultaDashboardDTO dto = new ConsultaDashboardDTO();
        dto.id = c.getId();
        dto.dataHora = c.getDataHora();
        dto.medico = c.getMedico();
        dto.valor = c.getValor();
        dto.vagas = c.getVagas();
        dto.status = c.getStatus().name();
        dto.especialidadeCodigo = c.getEspecialidade().getCodigo();
        dto.agendamentos = c.getAgendamentos().stream()
                .map(a -> {
                    AgendamentoResumoDTO ag = new AgendamentoResumoDTO();
                    ag.id = a.getId();
                    ag.codigoAgendamento = a.getCodigoAgendamento();
                    ag.status = a.getStatus().name();
                    return ag;
                })
                .collect(Collectors.toList());
        return dto;
    }



    private ConsultaDTO toDTO(Consulta c) {
        ConsultaDTO dto = new ConsultaDTO();
        dto.id = c.getId();
        dto.dataHora = c.getDataHora();
        dto.medico = c.getMedico();
        dto.valor = c.getValor();
        dto.vagas = c.getVagas();
        dto.status = c.getStatus().name();
        dto.especialidadeCodigo = c.getEspecialidade().getCodigo();
        return dto;
    }

    @Override
    public void cancelarConsultaComAgendamentos(Long idConsulta) {
        Consulta consulta = consultaRepository.findById(idConsulta)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));

        List<Agendamento> agendamentos = agendamentoRepository.findByConsulta(consulta);

        long totalConfirmados = agendamentos.stream()
                .filter(a -> a.getStatus() == StatusAgendamento.COMPARECEU)
                .count();

        if ((double) totalConfirmados / consulta.getVagas() >= 0.5) {
            throw new IllegalStateException("Não é possível cancelar: 50% ou mais dos pacientes já confirmaram presença.");
        }

        // Atualiza status da consulta
        consulta.setStatus(StatusConsulta.CANCELADA);
        consultaRepository.save(consulta);

        // Cancela todos os agendamentos e devolve pontos se necessário
        for (Agendamento a : agendamentos) {
            if (a.getStatus() != StatusAgendamento.CANCELADO) {
                a.setStatus(StatusAgendamento.CANCELADO);
                agendamentoRepository.save(a);

                if (a.getPontosUtilizados() > 0) {
                    CompraPontosDTO devolucao = new CompraPontosDTO();
                    devolucao.setQuantidadePontos(a.getPontosUtilizados());
                    devolucao.setDescricao("CANCELAMENTO DE CONSULTA");

                    usuarioClient.comprarPontos(a.getIdPaciente(), devolucao);
                }
            }
        }
    }

    @Override
    public void realizarConsulta(Long id) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));

        consulta.setStatus(StatusConsulta.REALIZADA);
        consultaRepository.save(consulta);

        List<Agendamento> agendamentos = agendamentoRepository.findByConsultaId(consulta.getId());

        for (Agendamento agendamento : agendamentos) {
            if (agendamento.getStatus() == StatusAgendamento.COMPARECEU) {
                agendamento.setStatus(StatusAgendamento.REALIZADO);
            } else {
                agendamento.setStatus(StatusAgendamento.FALTOU);
            }
        }

        agendamentoRepository.saveAll(agendamentos);
    }

    @Override
    @Transactional
    public ConsultaDTO cadastrarNovaConsulta(ConsultaCadastroDTO dto) {
        Especialidade especialidade = especialidadeRepository.findByCodigo(dto.codigoEspecialidade)
                .orElseThrow(() -> new RuntimeException("Especialidade não encontrada"));

        Consulta consulta = new Consulta();
        consulta.setDataHora(dto.dataHora);
        consulta.setMedico(dto.medico);
        consulta.setValor(dto.valor);
        consulta.setVagas(dto.vagas);
        consulta.setEspecialidade(especialidade);

        consultaRepository.save(consulta);

        return toDTO(consulta);
    }

}
