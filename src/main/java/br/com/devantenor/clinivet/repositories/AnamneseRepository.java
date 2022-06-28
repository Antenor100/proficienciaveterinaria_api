package br.com.devantenor.clinivet.repositories;

import br.com.devantenor.clinivet.entities.Anamnese;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnamneseRepository extends JpaRepository<Anamnese, Long> {
}