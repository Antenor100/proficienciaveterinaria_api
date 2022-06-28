package br.com.devantenor.clinivet.repositories;

import br.com.devantenor.clinivet.entities.ItemReceita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemReceitaRepository extends JpaRepository<ItemReceita, Long> {
    @Query("SELECT i FROM ItemReceita i WHERE i.receita.id = ?1")
    public List<ItemReceita> findAllByReceitaId(Long id);

    @Query("SELECT i FROM ItemReceita i WHERE i.remedio.id = ?1 AND i.receita.id = ?2")
    public ItemReceita findByRemedioIdAndReceitaId(Long remedioId, Long receitaId);
}