package br.com.devantenor.clinivet.repositories;

import br.com.devantenor.clinivet.entities.Receita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {
}