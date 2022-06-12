package br.com.devantenor.clinivet.entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "anamnese")
@Data
public class Anamnese {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    private String problemaSistemaDigestivo;

    private String problemaSistemaUroGenital;

    private String problemaSistemaCardioRespiratorio;

    private String problemaSistemaNeurologico;

    private String problemaSistemaLocomotor;

    private String problemaPele;

    private String problemaOlhos;

    private String problemaOuvido;
}
