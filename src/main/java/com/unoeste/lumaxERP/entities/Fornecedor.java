package com.unoeste.lumaxERP.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "fornecedor")
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "for_id")
    private Long id;

    @Column(name = "for_razao_social")
    private String razaoSocial;

    @Column(name = "for_cnpj")
    private String cnpj;

    @Column(name = "for_telefone")
    private String telefone;

    @Column(name = "for_email")
    private String email;

    @Column(name = "for_endereco")
    private String endereco;

    @Column(name = "for_cidade")
    private String cidade;

    @Column(name = "for_cep")
    private String cep;

    @Column(name = "for_numero")
    private Integer numero;

    @Column(name = "for_estado")
    private String estado;

    @Column(name = "for_bairro")
    private String bairro;

    public Fornecedor(String razaoSocial, String cnpj, String telefone, String email, String endereco, String cidade, String cep, Integer numero, String estado, String bairro) {
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
        this.cidade = cidade;
        this.cep = cep;
        this.numero = numero;
        this.estado = estado;
        this.bairro = bairro;
    }

    public Fornecedor() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }


}
