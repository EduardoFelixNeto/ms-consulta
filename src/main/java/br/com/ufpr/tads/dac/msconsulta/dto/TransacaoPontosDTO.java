package br.com.ufpr.tads.dac.msconsulta.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransacaoPontosDTO {
    public LocalDateTime dataHora;
    public String tipo;
    public String descricao;
    public int quantidadePontos;
    public BigDecimal valorReais;
}
