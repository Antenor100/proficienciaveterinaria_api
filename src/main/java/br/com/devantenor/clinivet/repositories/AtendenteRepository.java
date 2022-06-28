package br.com.devantenor.clinivet.repositories;

import br.com.devantenor.clinivet.entities.Atendente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtendenteRepository extends JpaRepository<Atendente, Long> {
}