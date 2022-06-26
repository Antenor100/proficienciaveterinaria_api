package br.com.devantenor.clinivet.repositories;

import br.com.devantenor.clinivet.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    public Usuario findByEmail(String email);
}