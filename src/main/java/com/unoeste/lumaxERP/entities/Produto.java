package com.unoeste.lumaxERP.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "produto")
public class Produto
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prod_id")
    private Long id;

    @Column(name = "prod_nome")
    private String nome;

    @Column(name = "prod_descricao")
    private String descricao;

    @Column(name = "prod_valor")
    private Double valor;

    @Column(name = "prod_foto")
    private byte[] foto;

    @Column(name = "prod_valor_casco")
    private Double valorCasco;

    @ManyToOne
    @JoinColumn(name = "cat_id", nullable = false)
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "for_id", nullable = false)
    private Fornecedor fornecedor;

    public Produto() {
    }

    public Produto(String nome, String descricao, Double valor, byte[] foto, Double valorCasco, Categoria categoria, Fornecedor fornecedor) {

        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
        this.foto = foto;
        this.valorCasco = valorCasco;
        this.categoria = categoria;
        this.fornecedor = fornecedor;
    }

    // Getters e Setters
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

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public Double getValorCasco() {
        return valorCasco;
    }

    public void setValorCasco(Double valorCasco) {
        this.valorCasco = valorCasco;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }
}

