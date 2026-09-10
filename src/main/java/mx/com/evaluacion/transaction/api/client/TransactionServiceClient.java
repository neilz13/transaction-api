package mx.com.evaluacion.transaction.api.client;

import mx.com.evaluacion.transaction.api.dto.CancelTransactionRequest;
import mx.com.evaluacion.transaction.api.dto.TransactionPageResponse;
import mx.com.evaluacion.transaction.api.dto.TransactionResponse;
import mx.com.evaluacion.transaction.api.dto.TransactionServiceRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Cliente Feign para comunicación con transaction-service.
 */
@FeignClient(
        name = "transaction-service",
        url = "${transaction.service.url}"
)
public interface TransactionServiceClient {

    /**
     * Registra una nueva transacción en transaction-service.
     *
     * @param request datos de la transacción
     * @return respuesta de la transacción
     */
    @PostMapping("/api/transactions")
    TransactionResponse create(
            @RequestBody TransactionServiceRequest request);

    /**
     * Solicita la cancelación de una transacción.
     *
     * @param request datos necesarios para cancelar la transacción
     * @return respuesta de la transacción cancelada
     */
    @PatchMapping("/api/transactions")
    TransactionResponse cancel(
                    @RequestBody CancelTransactionRequest request);

    /**
     * Consulta las transacciones de forma paginada.
     *
     * @param page      número de página
     * @param size      cantidad de registros por página
     * @param sortBy    campo por el cual ordenar
     * @param direction dirección del ordenamiento
     * @return página de transacciones
     */
    @GetMapping("/api/transactions")
    TransactionPageResponse findAll(
                    @RequestParam("page") int page,
                    @RequestParam("size") int size,
                    @RequestParam("sortBy") String sortBy,
                    @RequestParam("direction") String direction);

}

