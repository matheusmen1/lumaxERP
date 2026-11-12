package com.unoeste.lumaxERP.restControllers;

import com.unoeste.lumaxERP.DTO.LancamentoRequest;
import com.unoeste.lumaxERP.entities.Erro;
import com.unoeste.lumaxERP.entities.Lancamento;
import com.unoeste.lumaxERP.security.JWTTokenProvider;
import com.unoeste.lumaxERP.services.LancamentoService;
import com.unoeste.lumaxERP.services.RegraNegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apis/lancamento")
@CrossOrigin
public class LancamentoRestController {

    @Autowired
    private LancamentoService service;

    @GetMapping
    public ResponseEntity<Object> getAll() {
        List<Lancamento> list = service.getAll();
        if (!list.isEmpty())
            return ResponseEntity.ok().body(list);
        else
            return ResponseEntity.badRequest().body(new Erro("Nenhum Lançamento Encontrado"));
    }

    @GetMapping("/filtro/{filtro}")
    public ResponseEntity<Object> getByFiltro(@PathVariable("filtro") String filtro) {
        List<Lancamento> list = service.getByFiltro(filtro);
        if (!list.isEmpty())
            return ResponseEntity.ok().body(list);
        else
            return ResponseEntity.badRequest().body(new Erro("Nenhum Lançamento Encontrado"));
    }

    @GetMapping("/tipo-despesa/{id}")
    public ResponseEntity<Object> getByTipoDespesa(@PathVariable("id") Long id) {
        List<Lancamento> list = service.getByTipoDespesa(id);
        if (!list.isEmpty())
            return ResponseEntity.ok().body(list);
        else
            return ResponseEntity.badRequest().body(new Erro("Nenhum Lançamento Encontrado para este Tipo de Despesa"));
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<Object> getByUsuario(@PathVariable("id") Long id) {
        List<Lancamento> list = service.getByUsuario(id);
        if (!list.isEmpty())
            return ResponseEntity.ok().body(list);
        else
            return ResponseEntity.badRequest().body(new Erro("Nenhum Lançamento Encontrado para este Usuário"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") Long id) {
        Lancamento lancamento = service.getById(id);
        if (lancamento != null)
            return ResponseEntity.ok().body(lancamento);
        else
            return ResponseEntity.badRequest().body(new Erro("Lançamento Não Encontrado"));
    }

    @GetMapping("/total")
    public ResponseEntity<Object> getTotal() {
        return ResponseEntity.ok().body(service.getTotalDespesas());
    }

    // O usuario responsavel vem sempre do token JWT, nunca do corpo da requisicao.
    @PostMapping
    public ResponseEntity<Object> add(@RequestBody LancamentoRequest request,
                                      @RequestHeader("Authorization") String token) {
        try {
            Lancamento lancamento = service.registrar(request, JWTTokenProvider.getUsuarioIdFromToken(token));
            return ResponseEntity.ok().body(lancamento);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(new Erro(e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new Erro("Erro ao Registrar Lançamento"));
        }
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody LancamentoRequest request,
                                         @RequestHeader("Authorization") String token) {
        try {
            Lancamento lancamento = service.atualizar(request, JWTTokenProvider.getUsuarioIdFromToken(token));
            return ResponseEntity.ok().body(lancamento);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(new Erro(e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new Erro("Erro ao Atualizar Lançamento"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        try {
            if (service.delete(id))
                return ResponseEntity.ok().build();
            return ResponseEntity.badRequest().body(new Erro("Lançamento Não Encontrado"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new Erro("Erro ao Apagar Lançamento"));
        }
    }
}
