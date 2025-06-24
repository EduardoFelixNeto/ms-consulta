// ConsultaDashboardDTO.java
package br.com.ufpr.tads.dac.msconsulta.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ConsultaDashboardDTO {
    public Long id;
    public String codigo;
    public LocalDateTime dataHora;
    public String especialidadeCodigo;
    public String medico;
    public BigDecimal valor;
    public int vagas;
    public String status;
    public List<AgendamentoResumoDTO> agendamentos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getEspecialidadeCodigo() {
        return especialidadeCodigo;
    }

    public void setEspecialidadeCodigo(String especialidadeCodigo) {
        this.especialidadeCodigo = especialidadeCodigo;
    }

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public int getVagas() {
        return vagas;
    }

    public void setVagas(int vagas) {
        this.vagas = vagas;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<AgendamentoResumoDTO> getAgendamentos() {
        return agendamentos;
    }

    public void setAgendamentos(List<AgendamentoResumoDTO> agendamentos) {
        this.agendamentos = agendamentos;
    }
}
