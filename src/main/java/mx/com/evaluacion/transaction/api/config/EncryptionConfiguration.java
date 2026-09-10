package mx.com.evaluacion.transaction.api.config;

import mx.com.evaluacion.transaction.api.service.EncryptionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración relacionada con los servicios de cifrado.
 */
@Configuration
public class EncryptionConfiguration {

    /**
     * Crea el servicio de cifrado utilizando la clave configurada
     * mediante una variable de entorno.
     *
     * @param encryptionKey clave AES de 256 bits
     * @return servicio de cifrado
     */
    @Bean
    public EncryptionService encryptionService(
            @Value("${encryption.key}") String encryptionKey) {

        return new EncryptionService(encryptionKey);
    }
}
