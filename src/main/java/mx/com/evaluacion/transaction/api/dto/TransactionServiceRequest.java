package mx.com.evaluacion.transaction.api.dto;

import java.math.BigDecimal;

/**
 * Representa la información que transaction-api envía a transaction-service.
 */
public class TransactionServiceRequest {

    /**
     * Tipo de operación.
     */
    private String operacion;

    /**
     * Importe de la transacción.
     */
    private BigDecimal importe;

    /**
     * Cliente asociado.
     */
    private String cliente;

    /**
     * Secreto descifrado.
     */
    private String secreto;

    /**
     * Constructor vacío requerido para la serialización.
     */
    public TransactionServiceRequest() {
    }

    /**
     * Constructor de la solicitud.
     *
     * @param operacion operación
     * @param importe importe
     * @param cliente cliente
     * @param secreto secreto descifrado
     */
    public TransactionServiceRequest(
            String operacion,
            BigDecimal importe,
            String cliente,
            String secreto) {
        this.operacion = operacion;
        this.importe = importe;
        this.cliente = cliente;
        this.secreto = secreto;
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

    /**
     * Obtiene el importe.
     *
     * @return importe
     */
    public BigDecimal getImporte() {
        return importe;
    }

    /**
     * Establece el importe.
     *
     * @param importe importe
     */
    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    /**
     * Obtiene el cliente.
     *
     * @return cliente
     */
    public String getCliente() {
        return cliente;
    }

    /**
     * Establece el cliente.
     *
     * @param cliente cliente
     */
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    /**
     * Obtiene el secreto.
     *
     * @return secreto
     */
    public String getSecreto() {
        return secreto;
    }

    /**
     * Establece el secreto.
     *
     * @param secreto secreto
     */
    public void setSecreto(String secreto) {
        this.secreto = secreto;
    }
}
