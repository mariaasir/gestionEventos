package demo.gestioneventos.Eventos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "eventos")

public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evento", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "fecha_evento", nullable = false)
    private LocalDate fechaEvento;

    @NotNull
    @Column(name = "entradas_disponibles", nullable = false)
    private Integer entradasDisponibles;

    @NotNull
    @Column(name = "precio", nullable = false)
    private Double precio;

}