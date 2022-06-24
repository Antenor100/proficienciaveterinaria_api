package br.com.devantenor.clinivet.entities;

import br.com.devantenor.clinivet.util.enums.UserType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "usuario")
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    private String email;
    private String encriptedPassword;
    private UserType tipoUsuario;
    private int quantidadeAcessos;
}
