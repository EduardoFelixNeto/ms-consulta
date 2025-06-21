package br.com.ufpr.tads.dac.msconsulta.service;

import br.com.ufpr.tads.dac.msconsulta.dto.AgendamentoDTO;

import java.util.List;

public interface AgendamentoService {
    AgendamentoDTO agendarConsulta(Long idConsulta, AgendamentoDTO dto);
    void realizarCheckin(Long idAgendamento);
    void confirmarComparecimento(Long idAgendamento);
    void cancelarAgendamento(Long idAgendamento);
    List<AgendamentoDTO> listarPorPaciente(Long pacienteId);
}

