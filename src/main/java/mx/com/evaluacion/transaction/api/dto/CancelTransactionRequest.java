package mx.com.evaluacion.transaction.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Datos necesarios para solicitar la cancelación de una transacción.
 */
public class CancelTransactionRequest {

    @NotNull(message = "El id es obligatorio")
    private Long id;

    @NotBlank(message = "La referencia es obligatoria")
    private String referencia;

    @NotBlank(message = "El estatus es obligatorio")
    private String estatus;

    /**
     * Constructor por defecto.
     */
    public CancelTransactionRequest() {
    }

    /**
     * Obtiene el identificador de la transacción.
     *
     * @return identificador de la transacción
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador de la transacción.
     *
     * @param id identificador de la transacción
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene la referencia de la transacción.
     *
     * @return referencia de la transacción
     */
    public String getReferencia() {
        return referencia;
    }

    /**
     * Establece la referencia de la transacción.
     *
     * @param referencia referencia de la transacción
     */
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    /**
     * Obtiene el estatus solicitado.
     *
     * @return estatus
     */
    public String getEstatus() {
        return estatus;
    }

    /**
     * Establece el estatus solicitado.
     *
     * @param estatus estatus
     */
    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
}
