package com.unoeste.lumaxERP.restControllers;

import com.unoeste.lumaxERP.entities.Erro;
import com.unoeste.lumaxERP.entities.Usuario;
import com.unoeste.lumaxERP.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apis/usuario")
public class UsuarioRestController {

    @Autowired
    private UsuarioService service;

    @GetMapping
    public ResponseEntity<Object> getAll() {
        List<Usuario> list = service.getAll();
        if (!list.isEmpty())
            return ResponseEntity.ok().body(list);
        else
            return ResponseEntity.badRequest().body(new Erro("Nenhum Usuário Encontrado"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") Long id) {
        Usuario entity = service.getById(id);
        if (entity != null) {
            return ResponseEntity.ok().body(entity);
        } else {
            return ResponseEntity.badRequest().body(new Erro("Usuário Não Encontrado"));
        }
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Usuario entity) {
        Usuario savedEntity = service.save(entity);
        if (savedEntity != null) {
            return ResponseEntity.ok().body(savedEntity);
        } else {
            return ResponseEntity.badRequest().body(new Erro("Erro ao Gravar Usuário"));
        }
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody Usuario entity) {
        Usuario savedEntity = service.save(entity);
        if (savedEntity != null) {
            return ResponseEntity.ok().body(savedEntity);
        } else {
            return ResponseEntity.badRequest().body(new Erro("Erro ao Alterar Usuário"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        if (service.delete(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body(new Erro("Erro ao Apagar Usuário"));
        }
    }
}
