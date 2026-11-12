package com.unoeste.lumaxERP.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "lancamento")
public class Lancamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lan_id")
    private Long id;

    @Column(name = "lan_data_hora", nullable = false)
    private LocalDateTime dataHora;

    @Column(name = "lan_valor_total", nullable = false)
    private Double valorTotal;

    @Column(name = "lan_observacao", length = 100)
    private String observacao;

    @ManyToOne
    @JoinColumn(name = "usu_id", nullable = false)
    @JsonIgnoreProperties("senha")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "tpd_id", nullable = false)
    private TipoDespesa tipoDespesa;

    public Lancamento() {
    }

    public Lancamento(LocalDateTime dataHora, Double valorTotal, String observacao, Usuario usuario, TipoDespesa tipoDespesa) {
        this.dataHora = dataHora;
        this.valorTotal = valorTotal;
        this.observacao = observacao;
        this.usuario = usuario;
        this.tipoDespesa = tipoDespesa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public TipoDespesa getTipoDespesa() {
        return tipoDespesa;
    }

    public void setTipoDespesa(TipoDespesa tipoDespesa) {
        this.tipoDespesa = tipoDespesa;
    }
}
