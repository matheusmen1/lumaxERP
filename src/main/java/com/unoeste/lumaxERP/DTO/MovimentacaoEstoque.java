package com.unoeste.lumaxERP.DTO;

import java.time.LocalDateTime;

/**
 * Linha do relatorio de movimentacoes de estoque (Estoria 13).
 * Nao existe tabela propria: cada item de entrada (ENTRADA) ou de venda (SAIDA) vira uma movimentacao.
 */
public record MovimentacaoEstoque(
        String id,
        LocalDateTime data,
        String tipo,
        Long produtoId,
        String produtoNome,
        Integer quantidade,
        Double valorUnitario,
        String origem,
        Long documentoId,
        String usuarioNome
) {
}
