package br.com.devantenor.clinivet.repositories;

import br.com.devantenor.clinivet.entities.Exame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExameRepository extends JpaRepository<Exame, Long> {
}