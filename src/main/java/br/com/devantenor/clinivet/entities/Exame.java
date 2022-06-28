package br.com.devantenor.clinivet.entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "exame")
@Data
public class Exame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    private boolean hemograma;

    private boolean colesterol;

    private boolean fosforo;

    private boolean calcio;

    private boolean glicose;

    private boolean magnesio;

    private boolean ureia;

    private boolean potassio;

    private String ultrasonografia;

    private String radiografia;

    private Date data;

    @OneToOne
    private Animal animal;

    @OneToOne
    private Atendente atendente;
}
