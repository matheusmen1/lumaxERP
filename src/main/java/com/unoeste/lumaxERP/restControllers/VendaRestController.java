package com.unoeste.lumaxERP.restControllers;

import com.unoeste.lumaxERP.DTO.VendaRequest;
import com.unoeste.lumaxERP.entities.Erro;
import com.unoeste.lumaxERP.entities.Venda;
import com.unoeste.lumaxERP.security.JWTTokenProvider;
import com.unoeste.lumaxERP.services.RegraNegocioException;
import com.unoeste.lumaxERP.services.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apis/venda")
@CrossOrigin
public class VendaRestController {

    @Autowired
    private VendaService service;

    @GetMapping
    public ResponseEntity<Object> getAll() {
        List<Venda> list = service.getAll();
        if (!list.isEmpty())
            return ResponseEntity.ok().body(list);
        else
            return ResponseEntity.badRequest().body(new Erro("Nenhuma Venda Encontrada"));
    }

    @GetMapping("/filtro/{filtro}")
    public ResponseEntity<Object> getByFiltro(@PathVariable("filtro") String filtro) {
        List<Venda> list = service.getByFiltro(filtro);
        if (!list.isEmpty())
            return ResponseEntity.ok().body(list);
        else
            return ResponseEntity.badRequest().body(new Erro("Nenhuma Venda Encontrada"));
    }

    @GetMapping("/cliente/{id}")
    public ResponseEntity<Object> getByCliente(@PathVariable("id") Long id) {
        List<Venda> list = service.getByCliente(id);
        if (!list.isEmpty())
            return ResponseEntity.ok().body(list);
        else
            return ResponseEntity.badRequest().body(new Erro("Nenhuma Venda Encontrada para este Cliente"));
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<Object> getByUsuario(@PathVariable("id") Long id) {
        List<Venda> list = service.getByUsuario(id);
        if (!list.isEmpty())
            return ResponseEntity.ok().body(list);
        else
            return ResponseEntity.badRequest().body(new Erro("Nenhuma Venda Encontrada para este Vendedor"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") Long id) {
        Venda venda = service.getById(id);
        if (venda != null)
            return ResponseEntity.ok().body(venda);
        else
            return ResponseEntity.badRequest().body(new Erro("Venda Não Encontrada"));
    }

    // O vendedor (usuario) vem sempre do token JWT, nunca do corpo da requisicao.
    @PostMapping
    public ResponseEntity<Object> add(@RequestBody VendaRequest request,
                                      @RequestHeader("Authorization") String token) {
        try {
            Venda venda = service.registrar(request, JWTTokenProvider.getUsuarioIdFromToken(token));
            return ResponseEntity.ok().body(venda);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(new Erro(e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new Erro("Erro ao Registrar Venda"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        try {
            if (service.delete(id))
                return ResponseEntity.ok().build();
            return ResponseEntity.badRequest().body(new Erro("Venda Não Encontrada"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new Erro("Erro ao Apagar Venda"));
        }
    }
}
