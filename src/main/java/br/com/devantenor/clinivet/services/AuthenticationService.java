package br.com.devantenor.clinivet.services;

import br.com.devantenor.clinivet.entities.Usuario;
import br.com.devantenor.clinivet.repositories.UsuarioRepository;
import br.com.devantenor.clinivet.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    UsuarioRepository usuarioRepository;

    public Usuario getLoggedUser() {
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Usuario loggedUser = usuarioRepository.findByEmail(myUserDetails.getEmail());

        if (loggedUser != null) {
            return loggedUser;
        }

        return null;
    }

    public boolean loggedUserHasRoleAccess(String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals(role));
    }
}
