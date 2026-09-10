package mx.com.evaluacion.transaction.api.dto;

/**
 * Representa la respuesta recibida desde transaction-service.
 */
public class TransactionResponse {

    /**
     * Identificador de la transacción.
     */
    private Long id;

    /**
     * Estado de la transacción.
     */
    private String estatus;

    /**
     * Referencia de seis dígitos.
     */
    private String referencia;

    /**
     * Operación realizada.
     */
    private String operacion;

    /**
     * Constructor vacío requerido para la serialización.
     */
    public TransactionResponse() {
    }

    /**
     * Obtiene el identificador.
     *
     * @return identificador
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador.
     *
     * @param id identificador
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el estado.
     *
     * @return estado
     */
    public String getEstatus() {
        return estatus;
    }

    /**
     * Establece el estado.
     *
     * @param estatus estado
     */
    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    /**
     * Obtiene la referencia.
     *
     * @return referencia
     */
    public String getReferencia() {
        return referencia;
    }

    /**
     * Establece la referencia.
     *
     * @param referencia referencia
     */
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    /**
     * Obtiene la operación.
     *
     * @return operación
     */
    public String getOperacion() {
        return operacion;
    }

    /**
     * Establece la operación.
     *
     * @param operacion operación
     */
    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }
}
