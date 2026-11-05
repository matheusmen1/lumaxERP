package com.unoeste.lumaxERP.repositories;

import com.unoeste.lumaxERP.entities.EntradaEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntradaEstoqueRepository extends JpaRepository<EntradaEstoque, Long> {

    public List<EntradaEstoque> findAllByOrderByDataEntradaDesc();

    @Query(value = "SELECT e.* FROM entrada_estoque e JOIN fornecedor f ON f.for_id = e.for_id WHERE f.for_razao_social ILIKE CONCAT('%', :filtro, '%') OR f.for_cnpj ILIKE CONCAT('%', :filtro, '%') ORDER BY e.ete_data_entrada DESC", nativeQuery = true)
    public List<EntradaEstoque> getByFiltro(@Param("filtro") String filtro);

    @Query(value = "SELECT * FROM entrada_estoque WHERE for_id = :fornecedorId ORDER BY ete_data_entrada DESC", nativeQuery = true)
    public List<EntradaEstoque> getByFornecedor(@Param("fornecedorId") Long fornecedorId);
}
