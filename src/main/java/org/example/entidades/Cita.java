package org.example.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Entity
public class Cita implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relaciones bidireccionales
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "medico_id")
    private Medico medico;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "sala_id")
    private Sala sala;

    @Builder
    public Cita(Long id, Paciente paciente, Medico medico, Sala sala, LocalDateTime fechaHora, BigDecimal costo, EstadoCita estado, String observaciones) {
        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.sala = sala;
        this.fechaHora = fechaHora;
        this.costo = costo;
        this.estado = estado;
        this.observaciones = observaciones;
    }

    private LocalDateTime fechaHora;

    @Column(nullable = false)
    private final BigDecimal costo;

    @Enumerated(EnumType.STRING)
    private EstadoCita estado = EstadoCita.PROGRAMADA;

    private String observaciones = "";

    public void addCitaToRelaciones() {
        if (paciente != null) {
            paciente.addCita(this);
        }
        if (medico != null) {
            medico.addCita(this);
        }
        if (sala != null) {
            sala.addCita(this);
        }
    }
}