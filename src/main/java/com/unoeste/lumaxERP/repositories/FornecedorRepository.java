package com.unoeste.lumaxERP.repositories;

import com.unoeste.lumaxERP.entities.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

    @Query(value = "SELECT * FROM fornecedor WHERE for_razao_social ILIKE CONCAT('%', :filtro, '%') OR for_cnpj ILIKE CONCAT('%', :filtro, '%') OR for_email ILIKE CONCAT('%', :filtro, '%')", nativeQuery = true)
    public List<Fornecedor> getByFiltro(@Param("filtro") String filtro);
}
