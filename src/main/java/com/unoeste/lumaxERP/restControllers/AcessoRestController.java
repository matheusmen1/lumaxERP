package com.unoeste.lumaxERP.restControllers;

import com.unoeste.lumaxERP.DTO.Acesso;
import com.unoeste.lumaxERP.entities.Erro;
import com.unoeste.lumaxERP.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logar")
public class AcessoRestController {

    @Autowired
    private UsuarioService usuarioService;
    @PostMapping()
    public ResponseEntity<Object> logar (@RequestBody Acesso acesso)
    {
        String token = usuarioService.autenticar(acesso);
        if (token != null)
            return ResponseEntity.ok(token);
        else
            return ResponseEntity.badRequest().body(new Erro("Acesso Não Autorizado"));
    }
}
