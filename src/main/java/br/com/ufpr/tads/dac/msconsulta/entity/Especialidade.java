package br.com.ufpr.tads.dac.msconsulta.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Especialidade {

    @Id
    private String codigo;

    private String nome;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

