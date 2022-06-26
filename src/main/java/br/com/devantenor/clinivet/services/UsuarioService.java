package br.com.devantenor.clinivet.services;

import br.com.devantenor.clinivet.entities.Usuario;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public Usuario encryptUserPassword(Usuario usuario) {
        if (usuario.getPassword() != null) {
            usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
            return usuario;
        }
        return null;
    }
}
