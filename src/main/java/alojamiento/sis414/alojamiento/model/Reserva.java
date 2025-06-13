package alojamiento.sis414.alojamiento.model;

import jakarta.persistence.*;
import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @Schema(description = "Solo enviar el ID del cliente", implementation = ClienteIdOnly.class)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "habitacion_id")
    @Schema(description = "Solo enviar el ID de la habitación", implementation = HabitacionIdOnly.class)
    private Habitacion habitacion;

    public Reserva() {}

    public Reserva(LocalDate fechaEntrada, LocalDate fechaSalida, Cliente cliente, Habitacion habitacion) {
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.cliente = cliente;
        this.habitacion = habitacion;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getFechaEntrada() { return fechaEntrada; }
    public void setFechaEntrada(LocalDate fechaEntrada) { this.fechaEntrada = fechaEntrada; }

    public LocalDate getFechaSalida() { return fechaSalida; }
    public void setFechaSalida(LocalDate fechaSalida) { this.fechaSalida = fechaSalida; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Habitacion getHabitacion() { return habitacion; }
    public void setHabitacion(Habitacion habitacion) { this.habitacion = habitacion; }

    // 👇 Clases internas para mostrar solo el ID en Swagger
    public static class ClienteIdOnly {
        public Long id;
    }

    public static class HabitacionIdOnly {
        public Long id;
    }
}
