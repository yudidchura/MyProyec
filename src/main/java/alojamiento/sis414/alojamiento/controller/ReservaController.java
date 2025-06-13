package alojamiento.sis414.alojamiento.controller;

import alojamiento.sis414.alojamiento.model.Cliente;
import alojamiento.sis414.alojamiento.model.Habitacion;
import alojamiento.sis414.alojamiento.model.Reserva;
import alojamiento.sis414.alojamiento.repository.ClienteRepository;
import alojamiento.sis414.alojamiento.repository.HabitacionRepository;
import alojamiento.sis414.alojamiento.repository.ReservaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;



@SecurityRequirement(name = "bearerAuth")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/reservas")
@Tag(name = "Reserva", description = "This endpoint permits create, read, update and delete operations for reservas")
public class ReservaController {


    private static final Logger logger = LoggerFactory.getLogger(ReservaController.class);
    private final ReservaRepository reservaRepository;
    private final ClienteRepository clienteRepository;
    private final HabitacionRepository habitacionRepository;


    public ReservaController(ReservaRepository reservaRepository,
                              ClienteRepository clienteRepository,
                              HabitacionRepository habitacionRepository) {
        this.reservaRepository = reservaRepository;
        this.clienteRepository = clienteRepository;
        this.habitacionRepository = habitacionRepository;
    }

    @GetMapping
    @Operation(summary = "Get all reservas", tags = {"Reserva"})
    public List<Reserva> getAllReservas() {
        return reservaRepository.findAll();
    }


    @PostMapping
    @Operation(
            summary = "Crear una nueva reserva",
            description = "Permite registrar una reserva asociando un cliente y una habitación existentes mediante sus IDs.",
            tags = {"Reserva"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Reserva creada correctamente"),
                    @ApiResponse(responseCode = "400", description = "Error de validación o entidad no encontrada")
            }
    )
    public ResponseEntity<?> createReserva(@RequestBody Reserva reservaRequest) {
        if (reservaRequest.getCliente() == null || reservaRequest.getCliente().getId() == null ||
                reservaRequest.getHabitacion() == null || reservaRequest.getHabitacion().getId() == null) {
            return ResponseEntity.badRequest().body("Se requieren los IDs de cliente y habitación.");
        }

        Optional<Cliente> clienteOpt = clienteRepository.findById(reservaRequest.getCliente().getId());
        Optional<Habitacion> habitacionOpt = habitacionRepository.findById(reservaRequest.getHabitacion().getId());

        if (clienteOpt.isEmpty() || habitacionOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Cliente o habitación no encontrados.");
        }

        reservaRequest.setCliente(clienteOpt.get());
        reservaRequest.setHabitacion(habitacionOpt.get());
        reservaRequest.setId(null); // Evita que se mande un ID para sobreescritura

        Reserva nuevaReserva = reservaRepository.save(reservaRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaReserva);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a reserva",
            tags = {"Reserva"},
            responses = {
                    @ApiResponse(responseCode = "204", description = "Reserva was deleted successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad Request")
            }
    )
    public ResponseEntity<String> deleteReserva(@PathVariable Long id) {
        reservaRepository.deleteById(id);
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }
}