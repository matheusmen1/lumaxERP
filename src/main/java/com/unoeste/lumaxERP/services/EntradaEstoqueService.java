package com.unoeste.lumaxERP.services;

import com.unoeste.lumaxERP.DTO.EntradaEstoqueRequest;
import com.unoeste.lumaxERP.entities.EntradaEstoque;
import com.unoeste.lumaxERP.entities.Fornecedor;
import com.unoeste.lumaxERP.entities.ItemEntradaEstoque;
import com.unoeste.lumaxERP.entities.Produto;
import com.unoeste.lumaxERP.entities.Usuario;
import com.unoeste.lumaxERP.repositories.EntradaEstoqueRepository;
import com.unoeste.lumaxERP.repositories.FornecedorRepository;
import com.unoeste.lumaxERP.repositories.ProdutoRepository;
import com.unoeste.lumaxERP.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EntradaEstoqueService {

    @Autowired
    private EntradaEstoqueRepository repository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private FornecedorRepository fornecedorRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<EntradaEstoque> getAll() {
        return repository.findAllByOrderByDataEntradaDesc();
    }

    public List<EntradaEstoque> getByFiltro(String filtro) {
        return repository.getByFiltro(filtro);
    }

    public List<EntradaEstoque> getByFornecedor(Long fornecedorId) {
        return repository.getByFornecedor(fornecedorId);
    }

    public EntradaEstoque getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    /** Registra a entrada de mercadoria e soma as quantidades ao estoque numa unica transacao. */
    @Transactional
    public EntradaEstoque registrar(EntradaEstoqueRequest req, Long usuarioId) {
        if (req == null || req.itens() == null || req.itens().isEmpty())
            throw new RegraNegocioException("Informe ao menos um item para a entrada");

        Fornecedor fornecedor = req.fornecedorId() == null ? null
                : fornecedorRepository.findById(req.fornecedorId()).orElse(null);
        if (fornecedor == null) throw new RegraNegocioException("Fornecedor não encontrado");

        Usuario usuario = usuarioId == null ? null : usuarioRepository.findById(usuarioId).orElse(null);
        if (usuario == null) throw new RegraNegocioException("Usuário da entrada não encontrado");

        EntradaEstoque entrada = new EntradaEstoque();
        entrada.setFornecedor(fornecedor);
        entrada.setUsuario(usuario);
        entrada.setDataEntrada(LocalDateTime.now());

        for (EntradaEstoqueRequest.ItemEntradaRequest it : req.itens()) {
            if (it.produtoId() == null) throw new RegraNegocioException("Produto não informado em um dos itens");
            Produto produto = produtoRepository.findById(it.produtoId()).orElse(null);
            if (produto == null) throw new RegraNegocioException("Produto " + it.produtoId() + " não encontrado");

            if (produto.getFornecedor() == null || !produto.getFornecedor().getId().equals(fornecedor.getId()))
                throw new RegraNegocioException("O produto " + produto.getNome() + " não pertence ao fornecedor " + fornecedor.getRazaoSocial());

            int quantidade = it.quantidade() == null ? 0 : it.quantidade();
            if (quantidade <= 0)
                throw new RegraNegocioException("Quantidade inválida para o produto " + produto.getNome());
            if (it.custoUnitario() == null || it.custoUnitario() < 0)
                throw new RegraNegocioException("Custo unitário inválido para o produto " + produto.getNome());

            ItemEntradaEstoque item = new ItemEntradaEstoque();
            item.setEntrada(entrada);
            item.setProduto(produto);
            item.setQuantidade(quantidade);
            item.setCustoUnitario(Math.round(it.custoUnitario() * 100.0) / 100.0);
            entrada.getItens().add(item);
        }

        for (ItemEntradaEstoque item : entrada.getItens())
            produtoRepository.aumentarEstoque(item.getProduto().getId(), item.getQuantidade());

        return repository.save(entrada);
    }

    /** Exclui a entrada e retira do estoque o que ela havia somado. Bloqueia se parte ja foi vendida. */
    @Transactional
    public boolean delete(Long id) {
        EntradaEstoque entrada = repository.findById(id).orElse(null);
        if (entrada == null) return false;
        for (ItemEntradaEstoque item : entrada.getItens()) {
            if (produtoRepository.baixarEstoque(item.getProduto().getId(), item.getQuantidade()) == 0)
                throw new RegraNegocioException("Não é possível excluir: parte do estoque de "
                        + item.getProduto().getNome() + " desta entrada já foi vendida");
        }
        repository.deleteById(id);
        return true;
    }
}
