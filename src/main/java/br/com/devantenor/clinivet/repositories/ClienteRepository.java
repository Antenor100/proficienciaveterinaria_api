package br.com.devantenor.clinivet.repositories;

import br.com.devantenor.clinivet.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}