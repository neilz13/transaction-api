package mx.com.evaluacion.transaction.api.controller;

import jakarta.validation.Valid;
import mx.com.evaluacion.transaction.api.dto.LoginRequest;
import mx.com.evaluacion.transaction.api.dto.LoginResponse;
import mx.com.evaluacion.transaction.api.security.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;



/**
 * Controlador encargado de la autenticación de usuarios.
 */
@Tag(
        name = "Autenticación",
        description = "Operaciones de autenticación"
    )
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    /**
     * Crea el controlador de autenticación.
     *
     * @param authenticationService servicio de autenticación
     */
    public AuthenticationController(
            AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Autentica las credenciales proporcionadas.
     *
     * @param request credenciales del usuario
     * @return respuesta indicando que la autenticación fue exitosa
     */
    
    @Operation(
        summary = "Iniciar sesión",
        description = "Autentica al usuario y genera un token JWT."
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        String token = authenticationService.authenticate(
                request.getUsername(),
                request.getPassword());

        return ResponseEntity.ok(
                new LoginResponse(token));
    }
}
