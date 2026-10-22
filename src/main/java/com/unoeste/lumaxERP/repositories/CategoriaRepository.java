package com.unoeste.lumaxERP.repositories;

import com.unoeste.lumaxERP.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    @Query(value = "SELECT * FROM categoria WHERE cat_nome ILIKE CONCAT('%', :filtro, '%') OR cat_descricao ILIKE CONCAT('%', :filtro, '%')", nativeQuery = true)
    public List<Categoria> getByFiltro(@Param("filtro") String filtro);
}
