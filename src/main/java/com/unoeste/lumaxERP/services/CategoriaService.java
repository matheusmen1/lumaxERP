package com.unoeste.lumaxERP.services;

import com.unoeste.lumaxERP.entities.Categoria;
import com.unoeste.lumaxERP.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    public Categoria save(Categoria entity) {
        try {
            return repository.save(entity);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Categoria> getAll() {
        return repository.findAll();
    }

    public Categoria getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public boolean delete(Long id) {
        try {
            Categoria entity = repository.findById(id).orElse(null);
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
