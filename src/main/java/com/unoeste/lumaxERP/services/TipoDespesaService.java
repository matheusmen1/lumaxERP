package com.unoeste.lumaxERP.services;

import com.unoeste.lumaxERP.entities.TipoDespesa;
import com.unoeste.lumaxERP.repositories.TipoDespesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoDespesaService {

    @Autowired
    private TipoDespesaRepository repository;

    public TipoDespesa save(TipoDespesa entity) {
        try {
            return repository.save(entity);
        } catch (Exception e) {
            return null;
        }
    }

    public List<TipoDespesa> getAll() {
        return repository.findAll();
    }

    public TipoDespesa getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public boolean delete(Long id) {
        try {
            TipoDespesa entity = repository.findById(id).orElse(null);
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
