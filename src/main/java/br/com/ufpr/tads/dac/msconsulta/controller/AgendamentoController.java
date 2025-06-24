package br.com.ufpr.tads.dac.msconsulta.controller;

import br.com.ufpr.tads.dac.msconsulta.dto.AgendamentoDTO;
import br.com.ufpr.tads.dac.msconsulta.dto.CodigoAgendamentoDTO;
import br.com.ufpr.tads.dac.msconsulta.service.AgendamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<Void> confirmarComparecimento(@PathVariable String id) {
        agendamentoService.confirmarComparecimento(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelarAgendamento(@PathVariable Long id) {
        agendamentoService.cancelarAgendamento(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/por-paciente")
    public ResponseEntity<List<AgendamentoDTO>> listarPorPaciente(@RequestParam Long pacienteId) {
        List<AgendamentoDTO> agendamentos = agendamentoService.listarPorPaciente(pacienteId);
        return ResponseEntity.ok(agendamentos);
    }

    @GetMapping("/proximas-48h")
    public ResponseEntity<List<AgendamentoDTO>> listarProximas48Horas() {
        List<AgendamentoDTO> agendamentos = agendamentoService.listarProximas48Horas();
        return ResponseEntity.ok(agendamentos);
    }

    @PostMapping("/comparecer")
    public ResponseEntity<Void> confirmarComparecimentoPorCodigo(@RequestBody CodigoAgendamentoDTO dto) {
        agendamentoService.confirmarComparecimentoPorCodigo(dto.getCodigoAgendamento());
        return ResponseEntity.ok().build();
    }

}
