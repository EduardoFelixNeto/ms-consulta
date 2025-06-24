package br.com.ufpr.tads.dac.msconsulta.repository;

import br.com.ufpr.tads.dac.msconsulta.entity.Agendamento;
import br.com.ufpr.tads.dac.msconsulta.entity.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    List<Agendamento> findByConsultaId(Long idConsulta);

    List<Agendamento> findByIdPaciente(Long pacienteId);

    List<Agendamento> findByDataHoraAgendamentoBetween(LocalDateTime inicio, LocalDateTime fim);

    Optional<Agendamento> findByCodigoAgendamento(String codigoAgendamento);

    List<Agendamento> findByConsulta(Consulta consulta);
}

