package br.com.devantenor.clinivet.repositories;

import br.com.devantenor.clinivet.entities.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
    List<Animal> findAllByClienteId(Long id);
}