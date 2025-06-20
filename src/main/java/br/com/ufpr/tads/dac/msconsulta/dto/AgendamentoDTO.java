package br.com.ufpr.tads.dac.msconsulta.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AgendamentoDTO {
    public Long id;
    public String codigoAgendamento;
    public Long idConsulta;
    public Long idPaciente;
    public int pontosUtilizados;
    public BigDecimal valorPagoComplementar;
    public LocalDateTime dataHoraAgendamento;
    public String status;
}

