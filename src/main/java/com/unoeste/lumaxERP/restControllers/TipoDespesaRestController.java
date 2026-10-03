package com.unoeste.lumaxERP.restControllers;

import com.unoeste.lumaxERP.entities.Erro;
import com.unoeste.lumaxERP.entities.TipoDespesa;
import com.unoeste.lumaxERP.services.TipoDespesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apis/tipo-despesa")
public class TipoDespesaRestController {

    @Autowired
    private TipoDespesaService service;

    @GetMapping
    public ResponseEntity<Object> getAll() {
        List<TipoDespesa> list = service.getAll();
        if (!list.isEmpty())
            return ResponseEntity.ok().body(list);
        else
            return ResponseEntity.badRequest().body(new Erro("Nenhum Tipo de Despesa Encontrado"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") Long id) {
        TipoDespesa entity = service.getById(id);
        if (entity != null) {
            return ResponseEntity.ok().body(entity);
        } else {
            return ResponseEntity.badRequest().body(new Erro("Tipo de Despesa Não Encontrado"));
        }
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody TipoDespesa entity) {
        TipoDespesa savedEntity = service.save(entity);
        if (savedEntity != null) {
            return ResponseEntity.ok().body(savedEntity);
        } else {
            return ResponseEntity.badRequest().body(new Erro("Erro ao Gravar Tipo de Despesa"));
        }
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody TipoDespesa entity) {
        TipoDespesa savedEntity = service.save(entity);
        if (savedEntity != null) {
            return ResponseEntity.ok().body(savedEntity);
        } else {
            return ResponseEntity.badRequest().body(new Erro("Erro ao Alterar Tipo de Despesa"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        if (service.delete(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body(new Erro("Erro ao Apagar Tipo de Despesa"));
        }
    }
}
