package br.com.devantenor.clinivet.repositories;

import br.com.devantenor.clinivet.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query(value = "SELECT u FROM Usuario u WHERE u.estado = 1")
    public List<Usuario> findAllAtivos();

    public Usuario findByEmail(String email);
}