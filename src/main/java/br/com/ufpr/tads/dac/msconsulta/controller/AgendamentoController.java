package br.com.ufpr.tads.dac.msconsulta.controller;

import br.com.ufpr.tads.dac.msconsulta.dto.AgendamentoDTO;
import br.com.ufpr.tads.dac.msconsulta.service.AgendamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    public AgendamentoController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    @PostMapping("/consulta/{idConsulta}/agendar")
    public ResponseEntity<AgendamentoDTO> agendar(@PathVariable Long idConsulta,
                                                  @RequestBody AgendamentoDTO dto) {
        return ResponseEntity.ok(agendamentoService.agendarConsulta(idConsulta, dto));
    }

    @PostMapping("/{id}/checkin")
    public ResponseEntity<Void> realizarCheckin(@PathVariable Long id) {
        agendamentoService.realizarCheckin(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/compareceu")
    public ResponseEntity<Void> confirmarComparecimento(@PathVariable Long id) {
        agendamentoService.confirmarComparecimento(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelarAgendamento(@PathVariable Long id) {
        agendamentoService.cancelarAgendamento(id);
        return ResponseEntity.ok().build();
    }
}
