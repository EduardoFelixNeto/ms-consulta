package br.com.ufpr.tads.dac.msconsulta.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ConsultaDTO {
    public Long id;
    public String codigo;
    public LocalDateTime dataHora;
    public String especialidadeCodigo;
    public String medico;
    public BigDecimal valor;
    public int vagas;
    public String status;
}

