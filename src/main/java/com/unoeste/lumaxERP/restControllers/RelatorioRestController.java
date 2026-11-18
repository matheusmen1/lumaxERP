package com.unoeste.lumaxERP.restControllers;

import com.unoeste.lumaxERP.entities.Erro;
import com.unoeste.lumaxERP.services.RegraNegocioException;
import com.unoeste.lumaxERP.services.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.function.Supplier;

/**
 * Relatorios por periodo (Estorias 11, 12 e 13). Acesso restrito a administradores pelo AccessFilter.
 * Formato das datas: yyyy-MM-dd. Periodo sem registros devolve lista vazia (200).
 */
@RestController
@RequestMapping("/apis/relatorio")
@CrossOrigin
public class RelatorioRestController {

    @Autowired
    private RelatorioService service;

    @GetMapping("/lancamentos")
    public ResponseEntity<Object> lancamentos(@RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
                                              @RequestParam("fim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        return responder(() -> service.lancamentosPorPeriodo(inicio, fim), "Erro ao Gerar Relatório de Lançamentos");
    }

    @GetMapping("/vendas")
    public ResponseEntity<Object> vendas(@RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
                                         @RequestParam("fim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        return responder(() -> service.vendasPorPeriodo(inicio, fim), "Erro ao Gerar Relatório de Vendas");
    }

    @GetMapping("/movimentacoes")
    public ResponseEntity<Object> movimentacoes(@RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
                                                @RequestParam("fim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        return responder(() -> service.movimentacoesPorPeriodo(inicio, fim), "Erro ao Gerar Relatório de Movimentações");
    }

    private ResponseEntity<Object> responder(Supplier<Object> consulta, String mensagemErro) {
        try {
            return ResponseEntity.ok().body(consulta.get());
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(new Erro(e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new Erro(mensagemErro));
        }
    }
}
