package com.unoeste.lumaxERP.services;

import com.unoeste.lumaxERP.DTO.VendaRequest;
import com.unoeste.lumaxERP.entities.*;
import com.unoeste.lumaxERP.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VendaService {

    @Autowired
    private VendaRepository repository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private TipoPagamentoRepository tipoPagamentoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Venda> getAll() {
        return repository.findAllByOrderByDataVendaDesc();
    }

    public List<Venda> getByFiltro(String filtro) {
        return repository.getByFiltro(filtro);
    }

    public List<Venda> getByCliente(Long clienteId) {
        return repository.getByCliente(clienteId);
    }

    public List<Venda> getByUsuario(Long usuarioId) {
        return repository.getByUsuario(usuarioId);
    }

    public Venda getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    private static double arredondar(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }

    /**
     * Registra a venda e baixa o estoque numa unica transacao.
     * Qualquer erro (inclusive estoque insuficiente) reverte tudo.
     */
    @Transactional
    public Venda registrar(VendaRequest req, Long usuarioId) {
        if (req == null || req.itens() == null || req.itens().isEmpty())
            throw new RegraNegocioException("Informe ao menos um item para a venda");

        Cliente cliente = req.clienteId() == null ? null : clienteRepository.findById(req.clienteId()).orElse(null);
        if (cliente == null) throw new RegraNegocioException("Cliente não encontrado");

        TipoPagamento tipoPagamento = req.tipoPagamentoId() == null ? null
                : tipoPagamentoRepository.findById(req.tipoPagamentoId()).orElse(null);
        if (tipoPagamento == null) throw new RegraNegocioException("Tipo de pagamento não encontrado");

        Usuario usuario = usuarioId == null ? null : usuarioRepository.findById(usuarioId).orElse(null);
        if (usuario == null) throw new RegraNegocioException("Usuário da venda não encontrado");

        Venda venda = new Venda();
        venda.setCliente(cliente);
        venda.setTipoPagamento(tipoPagamento);
        venda.setUsuario(usuario);
        venda.setDataVenda(LocalDateTime.now());

        double total = 0;
        for (VendaRequest.ItemVendaRequest it : req.itens()) {
            if (it.produtoId() == null) throw new RegraNegocioException("Produto não informado em um dos itens");
            Produto produto = produtoRepository.findById(it.produtoId()).orElse(null);
            if (produto == null) throw new RegraNegocioException("Produto " + it.produtoId() + " não encontrado");

            int quantidade = it.quantidade() == null ? 0 : it.quantidade();
            if (quantidade <= 0)
                throw new RegraNegocioException("Quantidade inválida para o produto " + produto.getNome());

            String serie = it.numeroSerie() == null ? null : it.numeroSerie().trim();
            if (serie != null && serie.isEmpty()) serie = null;
            if (serie != null && quantidade != 1)
                throw new RegraNegocioException("Item com número de série deve ter quantidade 1 (" + produto.getNome() + ")");

            boolean casco = Boolean.TRUE.equals(it.cascoEntregue());
            double valorUnitario = produto.getValor();
            if (casco) {
                if (produto.getValorCasco() == null)
                    throw new RegraNegocioException("O produto " + produto.getNome() + " não possui valor de casco cadastrado");
                valorUnitario = Math.max(0, valorUnitario - produto.getValorCasco());
            }
            valorUnitario = arredondar(valorUnitario);

            ItemVenda item = new ItemVenda();
            item.setVenda(venda);
            item.setProduto(produto);
            item.setQuantidade(quantidade);
            item.setValorUnitario(valorUnitario);
            item.setCascoEntregue(casco);
            item.setNumeroSerie(serie);
            item.setMesesGarantia(it.mesesGarantia());
            venda.getItens().add(item);

            total += valorUnitario * quantidade;
        }
        venda.setValorTotal(arredondar(total));

        // Baixa atomica de estoque: 0 linhas afetadas = saldo insuficiente (faz rollback de tudo)
        for (ItemVenda item : venda.getItens()) {
            if (produtoRepository.baixarEstoque(item.getProduto().getId(), item.getQuantidade()) == 0) {
                Integer saldo = produtoRepository.findById(item.getProduto().getId()).map(Produto::getEstoque).orElse(0);
                throw new RegraNegocioException("Estoque insuficiente para " + item.getProduto().getNome()
                        + " (disponível: " + (saldo == null ? 0 : saldo) + ", solicitado: " + item.getQuantidade() + ")");
            }
        }

        return repository.save(venda);
    }

    /** Exclui a venda e devolve ao estoque as quantidades vendidas. */
    @Transactional
    public boolean delete(Long id) {
        Venda venda = repository.findById(id).orElse(null);
        if (venda == null) return false;
        for (ItemVenda item : venda.getItens())
            produtoRepository.aumentarEstoque(item.getProduto().getId(), item.getQuantidade());
        repository.deleteById(id);
        return true;
    }
}
