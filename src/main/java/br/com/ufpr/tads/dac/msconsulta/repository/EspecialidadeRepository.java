package br.com.ufpr.tads.dac.msconsulta.repository;

import br.com.ufpr.tads.dac.msconsulta.entity.Especialidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EspecialidadeRepository extends JpaRepository<Especialidade, String> {
    Optional<Especialidade> findByCodigo(String codigoEspecialidade);
}
