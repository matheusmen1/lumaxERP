package com.unoeste.lumaxERP.DTO;

import java.time.LocalDateTime;

public record LancamentoRequest(
        Long id,
        Long tipoDespesaId,
        Double valorTotal,
        String observacao,
        LocalDateTime dataHora
) {
}
