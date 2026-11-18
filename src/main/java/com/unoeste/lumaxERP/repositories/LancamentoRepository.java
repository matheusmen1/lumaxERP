package com.unoeste.lumaxERP.repositories;

import com.unoeste.lumaxERP.entities.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

    public List<Lancamento> findAllByOrderByDataHoraDesc();

    @Query(value = "SELECT l.* FROM lancamento l JOIN tipo_despesa t ON t.tpd_id = l.tpd_id WHERE t.tpd_nome ILIKE CONCAT('%', :filtro, '%') OR l.lan_observacao ILIKE CONCAT('%', :filtro, '%') ORDER BY l.lan_data_hora DESC", nativeQuery = true)
    public List<Lancamento> getByFiltro(@Param("filtro") String filtro);

    @Query(value = "SELECT * FROM lancamento WHERE tpd_id = :tipoDespesaId ORDER BY lan_data_hora DESC", nativeQuery = true)
    public List<Lancamento> getByTipoDespesa(@Param("tipoDespesaId") Long tipoDespesaId);

    @Query(value = "SELECT * FROM lancamento WHERE usu_id = :usuarioId ORDER BY lan_data_hora DESC", nativeQuery = true)
    public List<Lancamento> getByUsuario(@Param("usuarioId") Long usuarioId);

    @Query(value = "SELECT COALESCE(SUM(lan_valor_total), 0) FROM lancamento", nativeQuery = true)
    public Double totalDespesas();

    public List<Lancamento> findByDataHoraBetweenOrderByDataHoraDesc(LocalDateTime inicio, LocalDateTime fim);
}
