package demo.gestioneventos.Compras;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import demo.gestioneventos.Eventos.Evento;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "compras")
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", nullable = false)
    private Integer id;

    @Size(max = 20)
    @NotNull
//    @Pattern(regexp = "^\\d{8}[A-Za-z]$\n" , message = "DNI en formato no válido")
    @Column(name = "dni", nullable = false, length = 20)
    private String dni;

    @Size(max = 255)
    @NotNull
//    @Pattern(regexp = "^[A-Za-záéíóúÁÉÍÓÚñÑüÜ\\s]+$\n",  message = "Nombre en formato no válido")
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Size(max = 255)
    @NotNull
//    @Pattern(regexp = "^[A-Za-záéíóúÁÉÍÓÚñÑüÜ\\s]+$\n",  message = "Apellidos en formato no válido")
    @Column(name = "apellidos", nullable = false)
    private String apellidos;

    @NotNull
    @Column(name = "fecha_compra", nullable = false)
    private LocalDate fechaCompra;

    @Size(max = 16)
    @NotNull
    @Column(name = "numero_tarjeta", nullable = false, length = 16)
    private String numeroTarjeta;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_evento", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Evento idEvento;

}