package com.unoeste.lumaxERP.services;

import com.unoeste.lumaxERP.entities.Produto;
import com.unoeste.lumaxERP.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService
{

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto save(Produto produto) {
        try
        {
            return produtoRepository.save(produto);
        }catch (Exception e)
        {
            return null;
        }
    }

    public List<Produto> getAll()
    {
        return produtoRepository.findAll();
    }

    public Produto getById(Long id)
    {
        return produtoRepository.findById(id).orElse(null);
    }

    public boolean delete(Long id)
    {
        try{
         Produto produto = produtoRepository.findById(id).orElse(null);
         if (produto != null)
         {
             produtoRepository.delete(produto);
             return true;
         }
        return false;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
