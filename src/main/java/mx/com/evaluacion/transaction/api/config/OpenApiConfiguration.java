package mx.com.evaluacion.transaction.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de documentación OpenAPI de transaction-api.
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Transaction API",
                version = "1.0.0",
                description = """
                        API de entrada para la evaluación técnica de transacciones.
                        
                        Gestiona autenticación, validación de solicitudes,
                        cifrado AES-256-GCM y comunicación con
                        transaction-service mediante OpenFeign.
                        """
        )
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class OpenApiConfiguration {
}
