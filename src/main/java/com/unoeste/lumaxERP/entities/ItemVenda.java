package com.unoeste.lumaxERP.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "item_venda")
public class ItemVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itv_id")
    private Long id;

    @Column(name = "itv_quantidade")
    private Integer quantidade;

    @Column(name = "itv_valor_unitario")
    private Double valorUnitario;

    @Column(name = "itv_casco_entregue")
    private Boolean cascoEntregue;

    @Column(name = "itv_numero_serie")
    private String numeroSerie;

    @Column(name = "itv_meses_garantia")
    private Integer mesesGarantia;

    @ManyToOne
    @JoinColumn(name = "ven_id", nullable = false)
    @JsonIgnore
    private Venda venda;

    @ManyToOne
    @JoinColumn(name = "prod_id", nullable = false)
    @JsonIgnoreProperties("foto")
    private Produto produto;

    public ItemVenda() {
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

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Boolean getCascoEntregue() {
        return cascoEntregue;
    }

    public void setCascoEntregue(Boolean cascoEntregue) {
        this.cascoEntregue = cascoEntregue;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public Integer getMesesGarantia() {
        return mesesGarantia;
    }

    public void setMesesGarantia(Integer mesesGarantia) {
        this.mesesGarantia = mesesGarantia;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}
