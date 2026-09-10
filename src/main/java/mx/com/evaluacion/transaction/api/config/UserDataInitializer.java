package mx.com.evaluacion.transaction.api.config;

import mx.com.evaluacion.transaction.api.entity.UserEntity;
import mx.com.evaluacion.transaction.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Inicializa el usuario administrador utilizado para la evaluación.
 */
@Configuration
public class UserDataInitializer {

    /**
     * Crea un usuario inicial si todavía no existe.
     *
     * @param userRepository repositorio de usuarios
     * @param passwordEncoder codificador BCrypt
     * @param username usuario inicial
     * @param password contraseña inicial
     * @return proceso de inicialización
     */
    @Bean
    public CommandLineRunner initializeUser(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            @Value("${security.admin.username}") String username,
            @Value("${security.admin.password}") String password) {

        return args -> {

            if (password == null || password.isBlank()) {
                throw new IllegalStateException(
                        "La variable ADMIN_PASSWORD es obligatoria"
                );
            }

            if (userRepository.findByUsername(username).isPresent()) {
                return;
            }

            UserEntity user = new UserEntity();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));

            userRepository.save(user);
        };
    }
}
