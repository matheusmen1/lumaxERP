package com.unoeste.lumaxERP.restControllers;

import com.unoeste.lumaxERP.entities.Erro;
import com.unoeste.lumaxERP.entities.Categoria;
import com.unoeste.lumaxERP.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apis/categoria")
public class CategoriaRestController {

    @Autowired
    private CategoriaService service;

    @GetMapping
    public ResponseEntity<Object> getAll() {
        List<Categoria> list = service.getAll();
        if (!list.isEmpty())
            return ResponseEntity.ok().body(list);
        else
            return ResponseEntity.badRequest().body(new Erro("Nenhum Categoria Encontrado"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") Long id) {
        Categoria entity = service.getById(id);
        if (entity != null) {
            return ResponseEntity.ok().body(entity);
        } else {
            return ResponseEntity.badRequest().body(new Erro("Categoria Não Encontrado"));
        }
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Categoria entity) {
        Categoria savedEntity = service.save(entity);
        if (savedEntity != null) {
            return ResponseEntity.ok().body(savedEntity);
        } else {
            return ResponseEntity.badRequest().body(new Erro("Erro ao Gravar Categoria"));
        }
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody Categoria entity) {
        Categoria savedEntity = service.save(entity);
        if (savedEntity != null) {
            return ResponseEntity.ok().body(savedEntity);
        } else {
            return ResponseEntity.badRequest().body(new Erro("Erro ao Alterar Categoria"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        if (service.delete(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body(new Erro("Erro ao Apagar Categoria"));
        }
    }
}
