package br.com.ufpr.tads.dac.msconsulta.controller;

import br.com.ufpr.tads.dac.msconsulta.dto.ConsultaCadastroDTO;
import br.com.ufpr.tads.dac.msconsulta.dto.ConsultaDTO;
import br.com.ufpr.tads.dac.msconsulta.dto.ConsultaDashboardDTO;
import br.com.ufpr.tads.dac.msconsulta.service.ConsultaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    private final ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @PostMapping
    public ResponseEntity<ConsultaDTO> cadastrar(@RequestBody ConsultaDTO dto) {
        return ResponseEntity.ok(consultaService.cadastrarConsulta(dto));
    }

    @GetMapping
    public ResponseEntity<List<ConsultaDTO>> buscarConsultas(
            @RequestParam(required = false) String especialidade,
            @RequestParam(required = false) String medico
    ) {
        return ResponseEntity.ok(consultaService.buscarConsultas(especialidade, medico));
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelarConsulta(@PathVariable Long id) {
        consultaService.cancelarConsulta(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/finalizar")
    public ResponseEntity<Void> finalizarConsulta(@PathVariable Long id) {
        consultaService.finalizarConsulta(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/dashboard-funcionario")
    public ResponseEntity<List<ConsultaDashboardDTO>> listarConsultas48h() {
        return ResponseEntity.ok(consultaService.listarConsultasProximas48Horas());
    }

    @PostMapping("/{id}/cancelar-consulta")
    public ResponseEntity<Void> cancelarConsultaComAgendamentos(@PathVariable Long id) {
        consultaService.cancelarConsultaComAgendamentos(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/realizar")
    public ResponseEntity<Void> realizarConsulta(@PathVariable Long id) {
        consultaService.realizarConsulta(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<ConsultaDTO> cadastrarConsulta(@RequestBody ConsultaCadastroDTO dto) {
        return ResponseEntity.ok(consultaService.cadastrarNovaConsulta(dto));
    }

}
