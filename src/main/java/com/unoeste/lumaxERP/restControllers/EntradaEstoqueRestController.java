package com.unoeste.lumaxERP.restControllers;

import com.unoeste.lumaxERP.DTO.EntradaEstoqueRequest;
import com.unoeste.lumaxERP.entities.EntradaEstoque;
import com.unoeste.lumaxERP.entities.Erro;
import com.unoeste.lumaxERP.security.JWTTokenProvider;
import com.unoeste.lumaxERP.services.EntradaEstoqueService;
import com.unoeste.lumaxERP.services.RegraNegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apis/entrada-estoque")
@CrossOrigin
public class EntradaEstoqueRestController {

    @Autowired
    private EntradaEstoqueService service;

    @GetMapping
    public ResponseEntity<Object> getAll() {
        List<EntradaEstoque> list = service.getAll();
        if (!list.isEmpty())
            return ResponseEntity.ok().body(list);
        else
            return ResponseEntity.badRequest().body(new Erro("Nenhuma Entrada de Estoque Encontrada"));
    }

    @GetMapping("/filtro/{filtro}")
    public ResponseEntity<Object> getByFiltro(@PathVariable("filtro") String filtro) {
        List<EntradaEstoque> list = service.getByFiltro(filtro);
        if (!list.isEmpty())
            return ResponseEntity.ok().body(list);
        else
            return ResponseEntity.badRequest().body(new Erro("Nenhuma Entrada de Estoque Encontrada"));
    }

    @GetMapping("/fornecedor/{id}")
    public ResponseEntity<Object> getByFornecedor(@PathVariable("id") Long id) {
        List<EntradaEstoque> list = service.getByFornecedor(id);
        if (!list.isEmpty())
            return ResponseEntity.ok().body(list);
        else
            return ResponseEntity.badRequest().body(new Erro("Nenhuma Entrada de Estoque Encontrada para este Fornecedor"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") Long id) {
        EntradaEstoque entrada = service.getById(id);
        if (entrada != null)
            return ResponseEntity.ok().body(entrada);
        else
            return ResponseEntity.badRequest().body(new Erro("Entrada de Estoque Não Encontrada"));
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody EntradaEstoqueRequest request,
                                      @RequestHeader("Authorization") String token) {
        try {
            EntradaEstoque entrada = service.registrar(request, JWTTokenProvider.getUsuarioIdFromToken(token));
            return ResponseEntity.ok().body(entrada);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(new Erro(e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new Erro("Erro ao Registrar Entrada de Estoque"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        try {
            if (service.delete(id))
                return ResponseEntity.ok().build();
            return ResponseEntity.badRequest().body(new Erro("Entrada de Estoque Não Encontrada"));
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(new Erro(e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new Erro("Erro ao Apagar Entrada de Estoque"));
        }
    }
}
