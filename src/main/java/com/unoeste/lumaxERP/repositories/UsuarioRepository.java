package com.unoeste.lumaxERP.repositories;

import com.unoeste.lumaxERP.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query(value = "SELECT * FROM usuario WHERE usu_email = :email", nativeQuery = true)
    public Usuario getUsuarioByEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM usuario WHERE usu_nome ILIKE CONCAT('%', :filtro, '%') OR usu_email ILIKE CONCAT('%', :filtro, '%')", nativeQuery = true)
    public List<Usuario> getByFiltro(@Param("filtro") String filtro);

    @Query(value = "SELECT * FROM usuario WHERE usu_nivel = :nivel ORDER BY usu_nome", nativeQuery = true)
    public List<Usuario> getByNivel(@Param("nivel") Integer nivel);
}
