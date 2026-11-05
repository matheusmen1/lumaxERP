package com.unoeste.lumaxERP.services;

import com.unoeste.lumaxERP.entities.Cliente;
import com.unoeste.lumaxERP.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    public Cliente save(Cliente entity) {
        try {
            return repository.save(entity);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Cliente> getAll() {
        return repository.findAll();
    }

    public List<Cliente> getByFiltro(String filtro) {
        return repository.getByFiltro(filtro);
    }

    public Cliente getByCpf(String cpf) {
        return repository.getByCpf(cpf);
    }

    public List<Cliente> getByCidade(String cidade) {
        return repository.getByCidade(cidade);
    }

    public Cliente getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public boolean delete(Long id) {
        try {
            Cliente entity = repository.findById(id).orElse(null);
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
