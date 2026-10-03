package com.unoeste.lumaxERP.restControllers;

import com.unoeste.lumaxERP.entities.Erro;
import com.unoeste.lumaxERP.entities.Produto;
import com.unoeste.lumaxERP.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/apis/produto")
public class ProdutoRestController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<Object> getAll() {
        List<Produto> produtos = produtoService.getAll();
        if (produtos.size() > 0)
            return ResponseEntity.ok().body(produtos);
        else
            return ResponseEntity.badRequest().body(new Erro("Nenhum Produto Encontrado"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") Long id)
    {
       Produto produto = produtoService.getById(id);
        if (produto != null)
        {
            return ResponseEntity.ok().body(produto);
        }
        else
        {
            return ResponseEntity.badRequest().body(new Erro("Produto Não Encontrado"));
        }
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Produto produto)
    {
        Produto novoProduto = produtoService.save(produto);
        if (novoProduto != null)
        {
            return ResponseEntity.ok().body(novoProduto);
        }
        else
        {
            return ResponseEntity.badRequest().body(new Erro("Erro ao Gravar Produto"));
        }
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody Produto produto)
    {
        Produto novoProduto = produtoService.save(produto);
        if (novoProduto != null)
        {
            return ResponseEntity.ok().body(novoProduto);
        }
        else
        {
            return ResponseEntity.badRequest().body(new Erro("Erro ao Alterar Produto"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id)
    {
        if (produtoService.delete(id))
        {
            return ResponseEntity.ok().build();
        }
        else
        {
            return ResponseEntity.badRequest().body(new Erro("Erro ao Apagar Produto"));
        }


    }
}
