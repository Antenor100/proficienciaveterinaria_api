package br.com.devantenor.clinivet.repositories;

import br.com.devantenor.clinivet.entities.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
    @Query("SELECT a FROM Animal a WHERE a.cliente.id = ?1 AND a.estado = 1")
    List<Animal> findAllByClienteId(Long id);
}