package com.unoeste.lumaxERP.DTO;

import java.util.List;

public record EntradaEstoqueRequest(Long fornecedorId, List<ItemEntradaRequest> itens) {

    public record ItemEntradaRequest(Long produtoId, Integer quantidade, Double custoUnitario) {
    }
}
