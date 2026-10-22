package com.unoeste.lumaxERP.repositories;

import com.unoeste.lumaxERP.entities.TipoDespesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoDespesaRepository extends JpaRepository<TipoDespesa, Long> {

    @Query(value = "SELECT * FROM tipo_despesa WHERE tpd_nome ILIKE CONCAT('%', :filtro, '%') OR tpd_descricao ILIKE CONCAT('%', :filtro, '%')", nativeQuery = true)
    public List<TipoDespesa> getByFiltro(@Param("filtro") String filtro);
}
