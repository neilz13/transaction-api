package mx.com.evaluacion.transaction.api.exception;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Manejador global de excepciones de transaction-api.
 *
 * Centraliza las respuestas de error de la API pública para evitar
 * exponer información interna de la aplicación.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja errores de validación de los objetos recibidos mediante
     * {@code @Valid}.
     *
     * @param exception excepción generada durante la validación
     * @return respuesta HTTP 400 con los errores de validación
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(
            MethodArgumentNotValidException exception) {

        Map<String, String> errors = new LinkedHashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }

    /**
     * Maneja parámetros inválidos proporcionados por el consumidor.
     *
     * @param exception excepción generada por un parámetro inválido
     * @return respuesta HTTP 400 con un mensaje controlado
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>>
    handleIllegalArgumentException(
            IllegalArgumentException exception) {

        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                exception.getMessage()
        );
    }

    /**
     * Construye una respuesta de error sin exponer información
     * interna de la aplicación.
     *
     * @param status código HTTP de la respuesta
     * @param message mensaje controlado para el consumidor
     * @return respuesta HTTP con el error
     */
    private ResponseEntity<Map<String, String>> buildErrorResponse(
            HttpStatus status,
            String message) {

        Map<String, String> error = new LinkedHashMap<>();
        error.put("error", message);

        return ResponseEntity
                .status(status)
                .body(error);
    }
}
