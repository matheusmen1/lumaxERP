package com.unoeste.lumaxERP.services;

import com.unoeste.lumaxERP.DTO.Acesso;
import com.unoeste.lumaxERP.entities.Usuario;
import com.unoeste.lumaxERP.repositories.UsuarioRepository;
import com.unoeste.lumaxERP.security.JWTTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public Usuario save(Usuario entity) {
        try {
            return repository.save(entity);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Usuario> getAll() {
        return repository.findAll();
    }

    public List<Usuario> getByFiltro(String filtro) {
        return repository.getByFiltro(filtro);
    }

    public List<Usuario> getByNivel(Integer nivel) {
        return repository.getByNivel(nivel);
    }

    public Usuario getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public boolean delete(Long id) {
        try {
            Usuario entity = repository.findById(id).orElse(null);
            if (entity != null) {
                repository.delete(entity);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String autenticar(Acesso acesso) {
        Usuario usuario = repository.getUsuarioByEmail(acesso.getEmail());
        String token = null;
        if (usuario != null && usuario.getSenha().equals(acesso.getSenha())) {
            token = JWTTokenProvider.getToken(usuario.getEmail(), usuario.getNivel(), usuario.getId());
        }
        return token;
    }
}
