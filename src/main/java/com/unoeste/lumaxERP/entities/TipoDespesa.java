package com.unoeste.lumaxERP.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tipo_despesa")
public class TipoDespesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tpd_id")
    private Long id;

    @Column(name = "tpd_nome")
    private String nome;

    @Column(name = "tpd_descricao")
    private String descricao;

    public TipoDespesa(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public TipoDespesa() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


}
