package com.unoeste.lumaxERP.repositories;

import com.unoeste.lumaxERP.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query(value = "SELECT * FROM cliente WHERE cli_nome ILIKE CONCAT('%', :filtro, '%') OR cli_cpf ILIKE CONCAT('%', :filtro, '%') OR cli_email ILIKE CONCAT('%', :filtro, '%')", nativeQuery = true)
    public List<Cliente> getByFiltro(@Param("filtro") String filtro);

    @Query(value = "SELECT * FROM cliente WHERE cli_cpf = :cpf", nativeQuery = true)
    public Cliente getByCpf(@Param("cpf") String cpf);

    @Query(value = "SELECT * FROM cliente WHERE cli_cidade ILIKE CONCAT('%', :cidade, '%') ORDER BY cli_nome", nativeQuery = true)
    public List<Cliente> getByCidade(@Param("cidade") String cidade);
}
