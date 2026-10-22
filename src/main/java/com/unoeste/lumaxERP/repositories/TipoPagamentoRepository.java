package com.unoeste.lumaxERP.repositories;

import com.unoeste.lumaxERP.entities.TipoPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoPagamentoRepository extends JpaRepository<TipoPagamento, Long> {

    @Query(value = "SELECT * FROM tipo_pagamento WHERE tpp_nome ILIKE CONCAT('%', :filtro, '%') OR tpp_descricao ILIKE CONCAT('%', :filtro, '%')", nativeQuery = true)
    public List<TipoPagamento> getByFiltro(@Param("filtro") String filtro);
}
