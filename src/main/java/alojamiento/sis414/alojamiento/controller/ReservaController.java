package alojamiento.sis414.alojamiento.controller;

import alojamiento.sis414.alojamiento.model.Reserva;
import alojamiento.sis414.alojamiento.repository.ReservaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/reservas")
@Tag(name = "Reserva", description = "This endpoint permits create, read, update and delete operations for reservas")
public class ReservaController {

    private static final Logger logger = LoggerFactory.getLogger(ReservaController.class);
    private final ReservaRepository reservaRepository;

    public ReservaController(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    @GetMapping
    @Operation(summary = "Get all reservas", tags = {"Reserva"})
    public List<Reserva> getAllReservas() {
        return reservaRepository.findAll();
    }

    @PostMapping
    @Operation(
            summary = "Create a new reserva",
            tags = {"Reserva"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Reserva created successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad Request")
            }
    )
    public ResponseEntity<Reserva> createReserva(@RequestBody Reserva reservaRequest) {
        reservaRequest.setId(null);
        Reserva reserva = reservaRepository.save(reservaRequest);
        return new ResponseEntity<>(reserva, HttpStatus.CREATED);
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