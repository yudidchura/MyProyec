package alojamiento.sis414.alojamiento.controller;

import alojamiento.sis414.alojamiento.model.Usuario;
import alojamiento.sis414.alojamiento.repository.UsuarioRepository;
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
@RequestMapping("/usuarios")
@Tag(name = "Usuario", description = "This endpoint permits create, read, update and delete operations")
public class UsuarioController {
    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public List<Usuario> getUsuarios() {
        return this.usuarioRepository.findAll();
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a usuario",
            tags = {"Usuario"},
            responses = {
                    @ApiResponse(responseCode = "204", description = "Usuario deleted successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad Request")
            }
    )
    public ResponseEntity<String> deleteUsuario(@PathVariable Long id) {
        this.usuarioRepository.deleteById(id);
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }

    @PostMapping
    @Operation(
            summary = "Create a new usuario",
            tags = {"Usuario"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Usuario created successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad Request")
            }
    )
    public ResponseEntity<Usuario> addUsuario(@RequestBody Usuario usuarioRequest) {
        usuarioRequest.setId(null);
        Usuario usuario = this.usuarioRepository.save(usuarioRequest);
        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }
}

