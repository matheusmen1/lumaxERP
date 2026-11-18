package com.unoeste.lumaxERP.repositories;

import com.unoeste.lumaxERP.entities.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {

    public List<Venda> findAllByOrderByDataVendaDesc();

    @Query(value = "SELECT v.* FROM venda v JOIN cliente c ON c.cli_id = v.cli_id WHERE c.cli_nome ILIKE CONCAT('%', :filtro, '%') OR c.cli_cpf ILIKE CONCAT('%', :filtro, '%') ORDER BY v.ven_data_venda DESC", nativeQuery = true)
    public List<Venda> getByFiltro(@Param("filtro") String filtro);

    @Query(value = "SELECT * FROM venda WHERE cli_id = :clienteId ORDER BY ven_data_venda DESC", nativeQuery = true)
    public List<Venda> getByCliente(@Param("clienteId") Long clienteId);

    @Query(value = "SELECT * FROM venda WHERE usu_id = :usuarioId ORDER BY ven_data_venda DESC", nativeQuery = true)
    public List<Venda> getByUsuario(@Param("usuarioId") Long usuarioId);

    public List<Venda> findByDataVendaBetweenOrderByDataVendaDesc(LocalDateTime inicio, LocalDateTime fim);
}
