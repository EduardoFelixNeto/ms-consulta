package br.com.ufpr.tads.dac.msconsulta.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ConsultaCadastroDTO {
    public LocalDateTime dataHora;
    public String medico;
    public String codigoEspecialidade;
    public BigDecimal valor;
    public Integer vagas;

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public String getCodigoEspecialidade() {
        return codigoEspecialidade;
    }

    public void setCodigoEspecialidade(String codigoEspecialidade) {
        this.codigoEspecialidade = codigoEspecialidade;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Integer getVagas() {
        return vagas;
    }

    public void setVagas(Integer vagas) {
        this.vagas = vagas;
    }
}

