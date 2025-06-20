package br.com.ufpr.tads.dac.msconsulta.service;

import br.com.ufpr.tads.dac.msconsulta.dto.ConsultaDTO;

import java.util.List;

public interface ConsultaService {
    ConsultaDTO cadastrarConsulta(ConsultaDTO dto);
    List<ConsultaDTO> buscarConsultas(String especialidade, String medico);
    void cancelarConsulta(Long id);
    void finalizarConsulta(Long id);
}

