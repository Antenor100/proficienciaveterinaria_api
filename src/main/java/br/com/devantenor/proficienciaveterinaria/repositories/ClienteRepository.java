package br.com.devantenor.proficienciaveterinaria.repositories;

import br.com.devantenor.proficienciaveterinaria.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}