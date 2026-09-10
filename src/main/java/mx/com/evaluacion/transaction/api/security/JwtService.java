package mx.com.evaluacion.transaction.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Servicio encargado de generar y validar tokens JWT.
 */
@Service
public class JwtService {

    private final SecretKey secretKey;
    private final long expiration;

    /**
     * Crea el servicio JWT.
     *
     * @param secret secreto utilizado para firmar los tokens
     * @param expiration tiempo de expiración en milisegundos
     */
    public JwtService(
            @Value("${security.jwt.secret}") String secret,
            @Value("${security.jwt.expiration}") long expiration) {

        validateSecret(secret);

        this.secretKey = Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );

        this.expiration = expiration;
    }

    /**
     * Genera un token JWT para el usuario autenticado.
     *
     * @param userDetails información del usuario autenticado
     * @return token JWT
     */
    public String generateToken(UserDetails userDetails) {

        Date issuedAt = new Date();
        Date expirationDate =
                new Date(issuedAt.getTime() + expiration);

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(issuedAt)
                .expiration(expirationDate)
                .signWith(secretKey)
                .compact();
    }

    /**
     * Extrae el nombre de usuario contenido en el token.
     *
     * @param token token JWT
     * @return nombre de usuario
     */
    public String extractUsername(String token) {

        return getClaims(token).getSubject();
    }

    /**
     * Valida un token JWT contra la información del usuario.
     *
     * @param token token JWT
     * @param userDetails información del usuario
     * @return true si el token es válido
     */
    public boolean isTokenValid(
            String token,
            UserDetails userDetails) {

        String username = extractUsername(token);

        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return getClaims(token)
                .getExpiration()
                .before(new Date());
    }

    private Claims getClaims(String token) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private void validateSecret(String secret) {

        if (secret == null
                || secret.getBytes(StandardCharsets.UTF_8).length < 32) {

            throw new IllegalArgumentException(
                    "JWT_SECRET debe tener al menos 32 bytes"
            );
        }
    }
}
