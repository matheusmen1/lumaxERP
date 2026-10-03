package com.unoeste.lumaxERP.restControllers;

import com.unoeste.lumaxERP.entities.Erro;
import com.unoeste.lumaxERP.entities.Cliente;
import com.unoeste.lumaxERP.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apis/cliente")
public class ClienteRestController {

    @Autowired
    private ClienteService service;

    @GetMapping
    public ResponseEntity<Object> getAll() {
        List<Cliente> list = service.getAll();
        if (!list.isEmpty())
            return ResponseEntity.ok().body(list);
        else
            return ResponseEntity.badRequest().body(new Erro("Nenhum Cliente Encontrado"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") Long id) {
        Cliente entity = service.getById(id);
        if (entity != null) {
            return ResponseEntity.ok().body(entity);
        } else {
            return ResponseEntity.badRequest().body(new Erro("Cliente Não Encontrado"));
        }
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Cliente entity) {
        Cliente savedEntity = service.save(entity);
        if (savedEntity != null) {
            return ResponseEntity.ok().body(savedEntity);
        } else {
            return ResponseEntity.badRequest().body(new Erro("Erro ao Gravar Cliente"));
        }
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody Cliente entity) {
        Cliente savedEntity = service.save(entity);
        if (savedEntity != null) {
            return ResponseEntity.ok().body(savedEntity);
        } else {
            return ResponseEntity.badRequest().body(new Erro("Erro ao Alterar Cliente"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        if (service.delete(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body(new Erro("Erro ao Apagar Cliente"));
        }
    }
}
