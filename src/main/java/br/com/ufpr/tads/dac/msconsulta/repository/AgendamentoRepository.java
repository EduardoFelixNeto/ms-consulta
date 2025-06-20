package br.com.ufpr.tads.dac.msconsulta.repository;

import br.com.ufpr.tads.dac.msconsulta.entity.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    List<Agendamento> findByConsultaId(Long idConsulta);
}

