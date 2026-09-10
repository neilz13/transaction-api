package mx.com.evaluacion.transaction.api.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado de autenticar las credenciales de los usuarios.
 */
@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    /**
     * Crea el servicio de autenticación.
     *
     * @param authenticationManager administrador de autenticación
     * @param jwtService servicio para generar tokens
     */
    public AuthenticationService(
            AuthenticationManager authenticationManager,
            JwtService jwtService) {

        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    /**
     * Autentica al usuario y genera un token JWT.
     *
     * @param username nombre de usuario
     * @param password contraseña
     * @return token JWT
     */
    public String authenticate(
            String username,
            String password) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                );

        Authentication authentication =
                authenticationManager.authenticate(
                        authenticationToken
                );

        return jwtService.generateToken(
                (org.springframework.security.core.userdetails.UserDetails)
                        authentication.getPrincipal()
        );
    }
}
