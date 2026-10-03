package com.unoeste.lumaxERP.repositories;

import com.unoeste.lumaxERP.entities.TipoDespesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoDespesaRepository extends JpaRepository<TipoDespesa, Long> {
}
