package com.unoeste.lumaxERP.services;

/** Violacao de regra de negocio (ex.: estoque insuficiente). A transacao e revertida e o controller responde 400. */
public class RegraNegocioException extends RuntimeException {
    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }
}
