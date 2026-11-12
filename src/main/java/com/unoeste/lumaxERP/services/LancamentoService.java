package com.unoeste.lumaxERP.services;

import com.unoeste.lumaxERP.DTO.LancamentoRequest;
import com.unoeste.lumaxERP.entities.Lancamento;
import com.unoeste.lumaxERP.entities.TipoDespesa;
import com.unoeste.lumaxERP.entities.Usuario;
import com.unoeste.lumaxERP.repositories.LancamentoRepository;
import com.unoeste.lumaxERP.repositories.TipoDespesaRepository;
import com.unoeste.lumaxERP.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LancamentoService {

    @Autowired
    private LancamentoRepository repository;

    @Autowired
    private TipoDespesaRepository tipoDespesaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Lancamento> getAll() {
        return repository.findAllByOrderByDataHoraDesc();
    }

    public List<Lancamento> getByFiltro(String filtro) {
        return repository.getByFiltro(filtro);
    }

    public List<Lancamento> getByTipoDespesa(Long tipoDespesaId) {
        return repository.getByTipoDespesa(tipoDespesaId);
    }

    public List<Lancamento> getByUsuario(Long usuarioId) {
        return repository.getByUsuario(usuarioId);
    }

    public Lancamento getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Double getTotalDespesas() {
        return repository.totalDespesas();
    }

    private static double arredondar(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }

    @Transactional
    public Lancamento registrar(LancamentoRequest req, Long usuarioId) {
        if (req == null)
            throw new RegraNegocioException("Dados do lançamento inválidos");
        if (req.tipoDespesaId() == null)
            throw new RegraNegocioException("Selecione o tipo de despesa");
        if (req.valorTotal() == null || req.valorTotal() <= 0)
            throw new RegraNegocioException("O valor total da despesa deve ser maior que zero");

        TipoDespesa tipoDespesa = tipoDespesaRepository.findById(req.tipoDespesaId()).orElse(null);
        if (tipoDespesa == null)
            throw new RegraNegocioException("Tipo de despesa não encontrado");

        Usuario usuario = usuarioId == null ? null : usuarioRepository.findById(usuarioId).orElse(null);
        if (usuario == null)
            throw new RegraNegocioException("Usuário responsável não encontrado");

        Lancamento lancamento = new Lancamento();
        lancamento.setDataHora(req.dataHora() != null ? req.dataHora() : LocalDateTime.now());
        lancamento.setValorTotal(arredondar(req.valorTotal()));
        lancamento.setObservacao(req.observacao() != null && req.observacao().length() > 100
                ? req.observacao().substring(0, 100)
                : req.observacao());
        lancamento.setTipoDespesa(tipoDespesa);
        lancamento.setUsuario(usuario);

        return repository.save(lancamento);
    }

    @Transactional
    public Lancamento atualizar(LancamentoRequest req, Long usuarioId) {
        if (req == null || req.id() == null)
            throw new RegraNegocioException("Identificador do lançamento não informado para alteração");

        Lancamento existente = repository.findById(req.id()).orElse(null);
        if (existente == null)
            throw new RegraNegocioException("Lançamento não encontrado");

        if (req.tipoDespesaId() == null)
            throw new RegraNegocioException("Selecione o tipo de despesa");
        if (req.valorTotal() == null || req.valorTotal() <= 0)
            throw new RegraNegocioException("O valor total da despesa deve ser maior que zero");

        TipoDespesa tipoDespesa = tipoDespesaRepository.findById(req.tipoDespesaId()).orElse(null);
        if (tipoDespesa == null)
            throw new RegraNegocioException("Tipo de despesa não encontrado");

        if (req.dataHora() != null) {
            existente.setDataHora(req.dataHora());
        }
        existente.setValorTotal(arredondar(req.valorTotal()));
        existente.setObservacao(req.observacao() != null && req.observacao().length() > 100
                ? req.observacao().substring(0, 100)
                : req.observacao());
        existente.setTipoDespesa(tipoDespesa);

        if (usuarioId != null) {
            Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
            if (usuario != null) {
                existente.setUsuario(usuario);
            }
        }

        return repository.save(existente);
    }

    @Transactional
    public boolean delete(Long id) {
        if (id == null) return false;
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
