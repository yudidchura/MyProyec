package alojamiento.sis414.alojamiento.controller;

import alojamiento.sis414.alojamiento.model.Persona;
import alojamiento.sis414.alojamiento.repository.PersonaRepository;
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
@CrossOrigin(origins = "http://localhost:3000")

//@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/personas")
@Tag(name = "Persona", description = "This endpoint permits create, read, update and delete operations for personas")
public class PersonaController {

    private static final Logger logger = LoggerFactory.getLogger(PersonaController.class);
    private final PersonaRepository personaRepository;

    public PersonaController(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    @GetMapping
    @Operation(summary = "Get all personas", tags = {"Persona"})
    public List<Persona> getAllPersonas() {
        return personaRepository.findAll();
    }

    @PostMapping
    @Operation(
            summary = "Create a new persona",
            tags = {"Persona"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Persona created successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad Request")
            }
    )
    public ResponseEntity<Persona> createPersona(@RequestBody Persona personaRequest) {
        personaRequest.setId(null);
        Persona persona = personaRepository.save(personaRequest);
        return new ResponseEntity<>(persona, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a persona",
            tags = {"Persona"},
            responses = {
                    @ApiResponse(responseCode = "204", description = "Persona was deleted successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad Request")
            }
    )
    public ResponseEntity<String> deletePersona(@PathVariable Long id) {
        personaRepository.deleteById(id);
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }
}