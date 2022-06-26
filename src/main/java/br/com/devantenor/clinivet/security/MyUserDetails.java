package br.com.devantenor.clinivet.security;

import br.com.devantenor.clinivet.entities.Usuario;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyUserDetails implements UserDetails {
    @Getter
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public MyUserDetails(Usuario usuario) {
        this.email = usuario.getEmail();
        this.password = usuario.getPassword();

        List<SimpleGrantedAuthority> simpleGrantedAuthorityList = new ArrayList<>();

        simpleGrantedAuthorityList.add(new SimpleGrantedAuthority(usuario.getTipoUsuario().roleName));

        this.authorities = simpleGrantedAuthorityList;
    }

    public static MyUserDetails create(Usuario usuario) {
        return new MyUserDetails(usuario);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
