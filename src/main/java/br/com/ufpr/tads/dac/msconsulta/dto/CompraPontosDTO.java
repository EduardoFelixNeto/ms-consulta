package br.com.ufpr.tads.dac.msconsulta.dto;

import java.math.BigDecimal;

public class CompraPontosDTO {
    public int quantidadePontos;
    public BigDecimal valorReais;
    public String descricao;

    public int getQuantidadePontos() {
        return quantidadePontos;
    }

    public void setQuantidadePontos(int quantidadePontos) {
        this.quantidadePontos = quantidadePontos;
    }

    public BigDecimal getValorReais() {
        return valorReais;
    }

    public void setValorReais(BigDecimal valorReais) {
        this.valorReais = valorReais;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
