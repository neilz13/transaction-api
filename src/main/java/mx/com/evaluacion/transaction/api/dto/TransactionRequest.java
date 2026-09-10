package mx.com.evaluacion.transaction.api.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/**
 * Representa la información de entrada necesaria para registrar una transacción.
 *
 * <p>Los campos son validados antes de ejecutar la lógica de negocio para
 * garantizar que la información recibida cumpla con las reglas definidas
 * para la evaluación técnica.</p>
 */
public class TransactionRequest {

    /**
     * Operación realizada en la transacción.
     */
    @NotBlank(message = "La operación es obligatoria")
    @Pattern(
        regexp = "^[a-zA-Z]+$",
        message = "La operación solo puede contener caracteres"
    )
    @Size(
        max = 20,
        message = "La operación no puede exceder 20 caracteres"
    )
    private String operacion;

    /**
     * Importe monetario de la transacción.
     */
    @NotNull(message = "El importe es obligatorio")
    @DecimalMin(
        value = "0.01",
        message = "El importe debe ser mayor a 0"
    )
    @Digits(
        integer = 10,
        fraction = 2,
        message = "El importe debe tener máximo 10 dígitos enteros y 2 decimales"
    )
    private BigDecimal importe;

    /**
     * Nombre del cliente asociado a la transacción.
     */
    @NotBlank(message = "El cliente es obligatorio")
    @Size(
        min = 2,
        max = 100,
        message = "El cliente debe tener entre 2 y 100 caracteres"
    )
    @Pattern(
        regexp = "^[a-zA-ZÀ-ÿ\\s]+$",
        message = "El cliente solo puede contener caracteres"
    )
    private String cliente;

    /**
     * Valor secreto recibido para ser procesado por la API.
     */
    @NotBlank(message = "El secreto es obligatorio")
    @Size(
        max = 255,
        message = "El secreto no puede exceder 255 caracteres"
    )
    private String secreto;

    /**
     * Obtiene la operación de la transacción.
     *
     * @return operación de la transacción
     */
    public String getOperacion() {
        return operacion;
    }

    /**
     * Establece la operación de la transacción.
     *
     * @param operacion operación de la transacción
     */
    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    /**
     * Obtiene el importe de la transacción.
     *
     * @return importe de la transacción
     */
    public BigDecimal getImporte() {
        return importe;
    }

    /**
     * Establece el importe de la transacción.
     *
     * @param importe importe de la transacción
     */
    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    /**
     * Obtiene el cliente de la transacción.
     *
     * @return cliente de la transacción
     */
    public String getCliente() {
        return cliente;
    }

    /**
     * Establece el cliente de la transacción.
     *
     * @param cliente cliente de la transacción
     */
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    /**
     * Obtiene el secreto de la transacción.
     *
     * @return secreto de la transacción
     */
    public String getSecreto() {
        return secreto;
    }

    /**
     * Establece el secreto de la transacción.
     *
     * @param secreto secreto de la transacción
     */
    public void setSecreto(String secreto) {
        this.secreto = secreto;
    }
}
