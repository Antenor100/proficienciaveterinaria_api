package br.com.devantenor.clinivet.entities;

import br.com.devantenor.clinivet.util.enums.Estado;
import br.com.devantenor.clinivet.util.enums.UserType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "usuario")
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Estado estado;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date dataCadastro;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private UserType tipoUsuario;

    private int quantidadeAcessos;
}
