package br.com.devantenor.clinivet.entities;

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

    private Date data_nascimento;

    private Date data_cadastro;

    private int estado;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cliente", referencedColumnName = "id")
    @JsonIgnore
    private Cliente cliente;
}
