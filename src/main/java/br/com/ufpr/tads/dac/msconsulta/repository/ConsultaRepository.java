package br.com.ufpr.tads.dac.msconsulta.repository;

import br.com.ufpr.tads.dac.msconsulta.entity.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    @Query("SELECT c FROM Consulta c " +
            "WHERE (:especialidade IS NULL OR c.especialidade.codigo = :especialidade) " +
            "AND (:medico IS NULL OR LOWER(c.medico) LIKE LOWER(CONCAT('%', :medico, '%')))")
    List<Consulta> buscarPorFiltros(@Param("especialidade") String especialidade,
                                    @Param("medico") String medico);

    @Query("SELECT c FROM Consulta c " +
            "WHERE c.dataHora BETWEEN :agora AND :limite " +
            "AND c.status IN ('DISPONIVEL', 'FINALIZADA') " +
            "ORDER BY c.dataHora ASC")
    List<Consulta> listarConsultasProximas48Horas(@Param("agora") LocalDateTime agora,
                                                  @Param("limite") LocalDateTime limite);

}

