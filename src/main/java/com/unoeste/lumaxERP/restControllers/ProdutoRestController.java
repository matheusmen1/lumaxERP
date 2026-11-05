package com.unoeste.lumaxERP.restControllers;

import com.unoeste.lumaxERP.entities.Erro;
import com.unoeste.lumaxERP.entities.Produto;
import com.unoeste.lumaxERP.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apis/produto")
@CrossOrigin
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

    @GetMapping("/filtro/{filtro}")
    public ResponseEntity<Object> getByFiltro(@PathVariable("filtro") String filtro) {
        List<Produto> produtos = produtoService.getByFiltro(filtro);
        if (!produtos.isEmpty())
            return ResponseEntity.ok().body(produtos);
        else
            return ResponseEntity.badRequest().body(new Erro("Nenhum Produto Encontrado"));
    }

    @GetMapping("/fornecedor/{id}")
    public ResponseEntity<Object> getByFornecedor(@PathVariable("id") Long id) {
        List<Produto> produtos = produtoService.getByFornecedor(id);
        if (!produtos.isEmpty())
            return ResponseEntity.ok().body(produtos);
        else
            return ResponseEntity.badRequest().body(new Erro("Nenhum Produto Encontrado para este Fornecedor"));
    }

    @GetMapping("/categoria/{id}")
    public ResponseEntity<Object> getByCategoria(@PathVariable("id") Long id) {
        List<Produto> produtos = produtoService.getByCategoria(id);
        if (!produtos.isEmpty())
            return ResponseEntity.ok().body(produtos);
        else
            return ResponseEntity.badRequest().body(new Erro("Nenhum Produto Encontrado para esta Categoria"));
    }

    @GetMapping("/disponiveis")
    public ResponseEntity<Object> getDisponiveis() {
        List<Produto> produtos = produtoService.getDisponiveis();
        if (!produtos.isEmpty())
            return ResponseEntity.ok().body(produtos);
        else
            return ResponseEntity.badRequest().body(new Erro("Nenhum Produto com Estoque Disponível"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") Long id) {
        Produto produto = produtoService.getById(id);
        if (produto != null) {
            return ResponseEntity.ok().body(produto);
        } else {
            return ResponseEntity.badRequest().body(new Erro("Produto Não Encontrado"));
        }
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Produto produto) {
        Produto novoProduto = produtoService.save(produto);
        if (novoProduto != null) {
            return ResponseEntity.ok().body(novoProduto);
        } else {
            return ResponseEntity.badRequest().body(new Erro("Erro ao Gravar Produto"));
        }
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody Produto produto) {
        Produto novoProduto = produtoService.save(produto);
        if (novoProduto != null) {
            return ResponseEntity.ok().body(novoProduto);
        } else {
            return ResponseEntity.badRequest().body(new Erro("Erro ao Alterar Produto"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        if (produtoService.delete(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body(new Erro("Erro ao Apagar Produto"));
        }
    }
}
