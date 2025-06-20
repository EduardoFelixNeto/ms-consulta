package br.com.ufpr.tads.dac.msconsulta.service.impl;

import br.com.ufpr.tads.dac.msconsulta.dto.ConsultaDTO;
import br.com.ufpr.tads.dac.msconsulta.entity.Consulta;
import br.com.ufpr.tads.dac.msconsulta.entity.Especialidade;
import br.com.ufpr.tads.dac.msconsulta.entity.StatusConsulta;
import br.com.ufpr.tads.dac.msconsulta.repository.ConsultaRepository;
import br.com.ufpr.tads.dac.msconsulta.repository.EspecialidadeRepository;
import br.com.ufpr.tads.dac.msconsulta.service.ConsultaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultaServiceImpl implements ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final EspecialidadeRepository especialidadeRepository;

    public ConsultaServiceImpl(ConsultaRepository consultaRepository, EspecialidadeRepository especialidadeRepository) {
        this.consultaRepository = consultaRepository;
        this.especialidadeRepository = especialidadeRepository;
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
}
