package br.com.devantenor.clinivet.repositories;

import br.com.devantenor.clinivet.entities.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
    @Query("SELECT a FROM Animal a WHERE a.cliente.id = ?1 AND a.estado = 1")
    List<Animal> findAllByClienteId(Long id);

    @Query("SELECT a FROM Animal a INNER JOIN Cliente c ON a.cliente = c WHERE UPPER(a.nome) LIKE CONCAT('%',UPPER(:name),'%') OR UPPER(c.nome) LIKE CONCAT('%',UPPER(:name),'%')")
    List<Animal> findAllByClienteOrAnimalName(@Param("name") String name);
}