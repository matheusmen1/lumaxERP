package com.unoeste.lumaxERP.repositories;

import com.unoeste.lumaxERP.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query(value = "SELECT * FROM produto WHERE prod_nome ILIKE CONCAT('%', :filtro, '%') OR prod_descricao ILIKE CONCAT('%', :filtro, '%')", nativeQuery = true)
    public List<Produto> getByFiltro(@Param("filtro") String filtro);

    @Query(value = "SELECT * FROM produto WHERE for_id = :fornecedorId ORDER BY prod_nome", nativeQuery = true)
    public List<Produto> getByFornecedor(@Param("fornecedorId") Long fornecedorId);

    @Query(value = "SELECT * FROM produto WHERE cat_id = :categoriaId ORDER BY prod_nome", nativeQuery = true)
    public List<Produto> getByCategoria(@Param("categoriaId") Long categoriaId);

    @Query(value = "SELECT * FROM produto WHERE prod_estoque > 0 ORDER BY prod_nome", nativeQuery = true)
    public List<Produto> getDisponiveis();

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "UPDATE produto SET prod_estoque = prod_estoque - :qtd WHERE prod_id = :id AND prod_estoque >= :qtd", nativeQuery = true)
    public int baixarEstoque(@Param("id") Long id, @Param("qtd") int qtd);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "UPDATE produto SET prod_estoque = prod_estoque + :qtd WHERE prod_id = :id", nativeQuery = true)
    public int aumentarEstoque(@Param("id") Long id, @Param("qtd") int qtd);
}
