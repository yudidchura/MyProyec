package alojamiento.sis414.alojamiento.controller;

import alojamiento.sis414.alojamiento.model.Cliente;
import alojamiento.sis414.alojamiento.repository.ClienteRepository;
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
import java.util.Optional;

@SecurityRequirement(name = "bearerAuth")
@RestController
@CrossOrigin(origins = "http://localhost:3000")

//@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/clientes")
@Tag(name = "Cliente", description = "This endpoint permits create, read, update and delete operations for clientes")
public class ClienteController {

    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);
    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @GetMapping
    @Operation(summary = "Get all clientes", tags = {"Cliente"})
    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    @PostMapping
    @Operation(
            summary = "Create a new cliente",
            tags = {"Cliente"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Cliente created successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad Request")
            }
    )
    public ResponseEntity<Cliente> createCliente(@RequestBody Cliente clienteRequest) {
        clienteRequest.setId(null);
        Cliente cliente = clienteRepository.save(clienteRequest);
        return new ResponseEntity<>(cliente, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a cliente",
            tags = {"Cliente"},
            responses = {
                    @ApiResponse(responseCode = "204", description = "Cliente was deleted successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad Request")
            }
    )
    public ResponseEntity<String> deleteCliente(@PathVariable Long id) {
        clienteRepository.deleteById(id);
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable Long id, @RequestBody Cliente clienteRequest) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            cliente.setNombres(clienteRequest.getNombres());
            cliente.setApellidos(clienteRequest.getApellidos());
            cliente.setCi(clienteRequest.getCi());
            cliente.setTelefono(clienteRequest.getTelefono());
            clienteRepository.save(cliente);
            return new ResponseEntity<>(cliente, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}