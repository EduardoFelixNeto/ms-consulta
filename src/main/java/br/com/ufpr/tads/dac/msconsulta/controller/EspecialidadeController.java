package br.com.ufpr.tads.dac.msconsulta.controller;

import br.com.ufpr.tads.dac.msconsulta.entity.Especialidade;
import br.com.ufpr.tads.dac.msconsulta.repository.EspecialidadeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/especialidades")
public class EspecialidadeController {

    private final EspecialidadeRepository especialidadeRepository;

    public EspecialidadeController(EspecialidadeRepository especialidadeRepository) {
        this.especialidadeRepository = especialidadeRepository;
    }

    @GetMapping
    public ResponseEntity<List<Especialidade>> listarTodas() {
        List<Especialidade> lista = especialidadeRepository.findAll();
        return ResponseEntity.ok(lista);
    }
}
