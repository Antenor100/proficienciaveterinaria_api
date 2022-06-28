package br.com.devantenor.clinivet.repositories;

import br.com.devantenor.clinivet.entities.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
}