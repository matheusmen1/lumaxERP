package com.unoeste.lumaxERP.services;

import com.unoeste.lumaxERP.entities.Usuario;
import com.unoeste.lumaxERP.repositories.UsuarioRepository;
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
}
