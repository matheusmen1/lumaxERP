package com.unoeste.lumaxERP.repositories;

import com.unoeste.lumaxERP.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query(value = "SELECT * FROM produto WHERE prod_nome ILIKE CONCAT('%', :filtro, '%') OR prod_descricao ILIKE CONCAT('%', :filtro, '%')", nativeQuery = true)
    public List<Produto> getByFiltro(@Param("filtro") String filtro);
}
