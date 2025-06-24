package br.com.ufpr.tads.dac.msconsulta.service;

import br.com.ufpr.tads.dac.msconsulta.dto.ConsultaCadastroDTO;
import br.com.ufpr.tads.dac.msconsulta.dto.ConsultaDTO;
import br.com.ufpr.tads.dac.msconsulta.dto.ConsultaDashboardDTO;

import java.util.List;

public interface ConsultaService {
    ConsultaDTO cadastrarConsulta(ConsultaDTO dto);
    List<ConsultaDTO> buscarConsultas(String especialidade, String medico);
    void cancelarConsulta(Long id);
    void finalizarConsulta(Long id);

    List<ConsultaDashboardDTO> listarConsultasProximas48Horas();

    void cancelarConsultaComAgendamentos(Long id);

    void realizarConsulta(Long id);

    ConsultaDTO cadastrarNovaConsulta(ConsultaCadastroDTO dto);
}

