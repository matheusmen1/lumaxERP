package com.unoeste.lumaxERP.restControllers;

import com.unoeste.lumaxERP.entities.Erro;
import com.unoeste.lumaxERP.entities.TipoPagamento;
import com.unoeste.lumaxERP.services.TipoPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apis/tipo-pagamento")
public class TipoPagamentoRestController {

    @Autowired
    private TipoPagamentoService service;

    @GetMapping
    public ResponseEntity<Object> getAll() {
        List<TipoPagamento> list = service.getAll();
        if (!list.isEmpty())
            return ResponseEntity.ok().body(list);
        else
            return ResponseEntity.badRequest().body(new Erro("Nenhum Tipo de Pagamento Encontrado"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") Long id) {
        TipoPagamento entity = service.getById(id);
        if (entity != null) {
            return ResponseEntity.ok().body(entity);
        } else {
            return ResponseEntity.badRequest().body(new Erro("Tipo de Pagamento Não Encontrado"));
        }
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody TipoPagamento entity) {
        TipoPagamento savedEntity = service.save(entity);
        if (savedEntity != null) {
            return ResponseEntity.ok().body(savedEntity);
        } else {
            return ResponseEntity.badRequest().body(new Erro("Erro ao Gravar Tipo de Pagamento"));
        }
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody TipoPagamento entity) {
        TipoPagamento savedEntity = service.save(entity);
        if (savedEntity != null) {
            return ResponseEntity.ok().body(savedEntity);
        } else {
            return ResponseEntity.badRequest().body(new Erro("Erro ao Alterar Tipo de Pagamento"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        if (service.delete(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body(new Erro("Erro ao Apagar Tipo de Pagamento"));
        }
    }
}
