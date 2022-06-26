package br.com.devantenor.clinivet.entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Endereco")
@Data
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    private String rua;

    private Integer numero;

    private String bairro;

    private String complemento;

    private String cidade;

    private String estado;

    private String cep;
}
