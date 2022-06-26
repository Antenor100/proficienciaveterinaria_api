package br.com.devantenor.clinivet.entities;

import br.com.devantenor.clinivet.util.enums.Estado;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "animal")
@Data
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    private String nome;

    private String foto;

    private String raca;

    private String pelagem;

    private double peso;

    private int tipo;

    private Date dataNascimento;

    private Date dataCadastro;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Estado estado;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cliente", referencedColumnName = "id")
    @JsonIgnore
    private Cliente cliente;
}
