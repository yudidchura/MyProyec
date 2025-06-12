package alojamiento.sis414.alojamiento.controller;

import alojamiento.sis414.alojamiento.model.Habitacion;
import alojamiento.sis414.alojamiento.repository.HabitacionRepository;
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
@RequestMapping("/habitaciones")
@Tag(name = "Habitacion", description = "This endpoint permits create, read, update and delete operations for habitaciones")
public class HabitacionController {

    private static final Logger logger = LoggerFactory.getLogger(HabitacionController.class);
    private final HabitacionRepository habitacionRepository;

    public HabitacionController(HabitacionRepository habitacionRepository) {
        this.habitacionRepository = habitacionRepository;
    }

    @GetMapping
    @Operation(summary = "Get all habitaciones", tags = {"Habitacion"})
    public List<Habitacion> getAllHabitaciones() {
        return habitacionRepository.findAll();
    }

    @PostMapping
    @Operation(
            summary = "Create a new habitacion",
            tags = {"Habitacion"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Habitacion created successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad Request")
            }
    )
    public ResponseEntity<Habitacion> createHabitacion(@RequestBody Habitacion habitacionRequest) {
        habitacionRequest.setId(null);
        Habitacion habitacion = habitacionRepository.save(habitacionRequest);
        return new ResponseEntity<>(habitacion, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a habitacion",
            tags = {"Habitacion"},
            responses = {
                    @ApiResponse(responseCode = "204", description = "Habitacion was deleted successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad Request")
            }
    )
    public ResponseEntity<String> deleteHabitacion(@PathVariable Long id) {
        habitacionRepository.deleteById(id);
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }
}