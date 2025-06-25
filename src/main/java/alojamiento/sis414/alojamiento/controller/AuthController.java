package alojamiento.sis414.alojamiento.controller;

import alojamiento.sis414.alojamiento.model.Usuario;
import alojamiento.sis414.alojamiento.repository.UsuarioRepository;
import alojamiento.sis414.alojamiento.services.TokenBlackListService;
import alojamiento.sis414.alojamiento.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TokenBlackListService blackListService;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Usuario userRequest) {
        Optional<Usuario> existingUser = usuarioRepository.findByUsername(userRequest.getUsername());

        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El nombre de usuario ya existe.");
        }

        userRequest.setId(null);
        Usuario savedUser = usuarioRepository.save(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Usuario userRequest) {
        Optional<Usuario> userOpt = usuarioRepository.findByUsername(userRequest.getUsername());

        if (userOpt.isPresent() && userOpt.get().getPassword().equals(userRequest.getPassword())) {
            String token = jwtUtil.generateToken(userRequest.getUsername());
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(token);
        } else {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String token = jwtUtil.getRequestToken(request);
        System.out.println("Token en logout: " + token); // <-- debug

        if (token != null) {
            blackListService.addToken(token);
            logger.info("Token agregado a la blacklist: " + token);
        } else {
            return ResponseEntity.status(401).body("Token no proporcionado");
        }

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("Logout exitoso");
    }

}