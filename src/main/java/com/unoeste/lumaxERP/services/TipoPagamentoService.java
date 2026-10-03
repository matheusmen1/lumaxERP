package com.unoeste.lumaxERP.services;

import com.unoeste.lumaxERP.entities.TipoPagamento;
import com.unoeste.lumaxERP.repositories.TipoPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoPagamentoService {

    @Autowired
    private TipoPagamentoRepository repository;

    public TipoPagamento save(TipoPagamento entity) {
        try {
            return repository.save(entity);
        } catch (Exception e) {
            return null;
        }
    }

    public List<TipoPagamento> getAll() {
        return repository.findAll();
    }

    public TipoPagamento getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public boolean delete(Long id) {
        try {
            TipoPagamento entity = repository.findById(id).orElse(null);
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
