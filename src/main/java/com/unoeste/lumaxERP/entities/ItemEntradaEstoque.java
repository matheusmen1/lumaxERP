package com.unoeste.lumaxERP.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "item_entrada_estoque")
public class ItemEntradaEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iee_id")
    private Long id;

    @Column(name = "iee_quantidade")
    private Integer quantidade;

    @Column(name = "iee_custo_unitario")
    private Double custoUnitario;

    @ManyToOne
    @JoinColumn(name = "ete_id", nullable = false)
    @JsonIgnore
    private EntradaEstoque entrada;

    @ManyToOne
    @JoinColumn(name = "prod_id", nullable = false)
    @JsonIgnoreProperties("foto")
    private Produto produto;

    public ItemEntradaEstoque() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getCustoUnitario() {
        return custoUnitario;
    }

    public void setCustoUnitario(Double custoUnitario) {
        this.custoUnitario = custoUnitario;
    }

    public EntradaEstoque getEntrada() {
        return entrada;
    }

    public void setEntrada(EntradaEstoque entrada) {
        this.entrada = entrada;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}
