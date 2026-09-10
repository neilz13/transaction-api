package mx.com.evaluacion.transaction.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Punto de entrada de la aplicación transaction-api.
 */
@SpringBootApplication
@EnableFeignClients
public class TransactionApiApplication {

    /**
     * Inicializa la aplicación.
     *
     * @param args argumentos recibidos al iniciar la aplicación
     */
    public static void main(String[] args) {
        SpringApplication.run(TransactionApiApplication.class, args);
    }
}
