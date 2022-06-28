package br.com.devantenor.clinivet.entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "itemReceita")
@Data
public class ItemReceita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    private int quantidade;

    private double dose;

    @ManyToOne
    @JoinColumn(name = "receitaId", nullable = false)
    private Receita receita;

    @OneToOne
    private Remedio remedio;
}
