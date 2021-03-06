package br.com.devantenor.clinivet.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "remedio")
@Data
public class Remedio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    private String nome;

    private String laboratorio;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date dataValidade;
}
