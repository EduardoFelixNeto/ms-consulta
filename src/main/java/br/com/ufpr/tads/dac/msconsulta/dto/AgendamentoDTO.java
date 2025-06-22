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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoAgendamento() {
        return codigoAgendamento;
    }

    public void setCodigoAgendamento(String codigoAgendamento) {
        this.codigoAgendamento = codigoAgendamento;
    }

    public Long getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(Long idConsulta) {
        this.idConsulta = idConsulta;
    }

    public Long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getPontosUtilizados() {
        return pontosUtilizados;
    }

    public void setPontosUtilizados(int pontosUtilizados) {
        this.pontosUtilizados = pontosUtilizados;
    }

    public BigDecimal getValorPagoComplementar() {
        return valorPagoComplementar;
    }

    public void setValorPagoComplementar(BigDecimal valorPagoComplementar) {
        this.valorPagoComplementar = valorPagoComplementar;
    }

    public LocalDateTime getDataHoraAgendamento() {
        return dataHoraAgendamento;
    }

    public void setDataHoraAgendamento(LocalDateTime dataHoraAgendamento) {
        this.dataHoraAgendamento = dataHoraAgendamento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

