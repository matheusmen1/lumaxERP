package com.unoeste.lumaxERP.services;

import com.unoeste.lumaxERP.DTO.MovimentacaoEstoque;
import com.unoeste.lumaxERP.entities.*;
import com.unoeste.lumaxERP.repositories.EntradaEstoqueRepository;
import com.unoeste.lumaxERP.repositories.LancamentoRepository;
import com.unoeste.lumaxERP.repositories.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/** Funcoes de saida da ERS: lancamentos, vendas e movimentacoes de estoque por periodo. */
@Service
@Transactional(readOnly = true)
public class RelatorioService {

    @Autowired
    private VendaRepository vendaRepository;
    @Autowired
    private LancamentoRepository lancamentoRepository;
    @Autowired
    private EntradaEstoqueRepository entradaEstoqueRepository;

    public List<Lancamento> lancamentosPorPeriodo(LocalDate inicio, LocalDate fim) {
        validarPeriodo(inicio, fim);
        return lancamentoRepository.findByDataHoraBetweenOrderByDataHoraDesc(inicio.atStartOfDay(), fim.atTime(LocalTime.MAX));
    }

    public List<Venda> vendasPorPeriodo(LocalDate inicio, LocalDate fim) {
        validarPeriodo(inicio, fim);
        return vendaRepository.findByDataVendaBetweenOrderByDataVendaDesc(inicio.atStartOfDay(), fim.atTime(LocalTime.MAX));
    }

    public List<MovimentacaoEstoque> movimentacoesPorPeriodo(LocalDate inicio, LocalDate fim) {
        validarPeriodo(inicio, fim);
        List<MovimentacaoEstoque> lista = new ArrayList<>();

        for (EntradaEstoque e : entradaEstoqueRepository.findByDataEntradaBetweenOrderByDataEntradaDesc(inicio.atStartOfDay(), fim.atTime(LocalTime.MAX))) {
            for (ItemEntradaEstoque i : e.getItens()) {
                lista.add(new MovimentacaoEstoque(
                        "E" + i.getId(), e.getDataEntrada(), "ENTRADA",
                        i.getProduto().getId(), i.getProduto().getNome(),
                        i.getQuantidade(), i.getCustoUnitario(),
                        e.getFornecedor() != null ? e.getFornecedor().getRazaoSocial() : null,
                        e.getId(), e.getUsuario() != null ? e.getUsuario().getNome() : null));
            }
        }

        for (Venda v : vendasPorPeriodo(inicio, fim)) {
            for (ItemVenda i : v.getItens()) {
                lista.add(new MovimentacaoEstoque(
                        "S" + i.getId(), v.getDataVenda(), "SAIDA",
                        i.getProduto().getId(), i.getProduto().getNome(),
                        i.getQuantidade(), i.getValorUnitario(),
                        v.getCliente() != null ? v.getCliente().getNome() : null,
                        v.getId(), v.getUsuario() != null ? v.getUsuario().getNome() : null));
            }
        }

        lista.sort(Comparator.comparing(MovimentacaoEstoque::data, Comparator.nullsLast(Comparator.reverseOrder())));
        return lista;
    }

    private void validarPeriodo(LocalDate inicio, LocalDate fim) {
        if (inicio == null || fim == null)
            throw new RegraNegocioException("Informe a data inicial e a data final");
        if (inicio.isAfter(fim))
            throw new RegraNegocioException("A data inicial não pode ser posterior à data final");
    }
}
