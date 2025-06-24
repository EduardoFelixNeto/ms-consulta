package br.com.ufpr.tads.dac.msconsulta.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigoAgendamento;

    @ManyToOne
    @JoinColumn(name = "consulta_id")
    private Consulta consulta;

    private Long idPaciente;

    @Enumerated(EnumType.STRING)
    private StatusAgendamento status;

    private int pontosUtilizados;

    private BigDecimal valorPagoComplementar;

    private LocalDateTime dataHoraAgendamento;

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

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public Long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public StatusAgendamento getStatus() {
        return status;
    }

    public void setStatus(StatusAgendamento status) {
        this.status = status;
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
}

