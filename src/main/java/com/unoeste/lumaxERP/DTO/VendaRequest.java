package com.unoeste.lumaxERP.DTO;

import java.util.List;

public record VendaRequest(Long clienteId, Long tipoPagamentoId, List<ItemVendaRequest> itens) {

    public record ItemVendaRequest(Long produtoId, Integer quantidade, Boolean cascoEntregue,
                                   String numeroSerie, Integer mesesGarantia) {
    }
}
