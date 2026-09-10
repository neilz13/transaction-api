package mx.com.evaluacion.transaction.api.controller;

import jakarta.validation.Valid;
import mx.com.evaluacion.transaction.api.dto.CancelTransactionRequest;
import mx.com.evaluacion.transaction.api.dto.TransactionPageResponse;
import mx.com.evaluacion.transaction.api.dto.TransactionRequest;
import mx.com.evaluacion.transaction.api.dto.TransactionResponse;
import mx.com.evaluacion.transaction.api.dto.TransactionServiceRequest;
import mx.com.evaluacion.transaction.api.client.TransactionServiceClient;
import mx.com.evaluacion.transaction.api.service.EncryptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Set;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controller para operaciones de transacciones.
 */
@Tag(
        name = "Transacciones",
        description = "Operaciones de transacciones"
)
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionServiceClient transactionServiceClient;
    private final EncryptionService encryptionService;

    private static final Set<String> ALLOWED_SORT_FIELDS =
        Set.of(
                "id",
                "operacion",
                "importe",
                "cliente",
                "referencia",
                "estatus"
        );

    public TransactionController(
            TransactionServiceClient transactionServiceClient,
            EncryptionService encryptionService) {

        this.transactionServiceClient = transactionServiceClient;
        this.encryptionService = encryptionService;
    }

    @Operation(
        summary = "Registrar una transacción",
        description = "Registra una nueva transacción y la envía a transaction-service.",
        security = @SecurityRequirement(name = "bearerAuth")
        )
    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(
                    @Valid @RequestBody TransactionRequest request) {

            String decryptedSecret = encryptionService.decrypt(request.getSecreto());

            TransactionServiceRequest serviceRequest = new TransactionServiceRequest(
                            request.getOperacion(),
                            request.getImporte(),
                            request.getCliente(),
                            decryptedSecret);

            TransactionResponse response = transactionServiceClient.create(serviceRequest);

            return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Cancelar una transacción",
        description = "Cancela una transacción que actualmente se encuentre aprobada.",
        security = @SecurityRequirement(name = "bearerAuth")
        )
    @PatchMapping
    public ResponseEntity<TransactionResponse> cancelTransaction(
                    @Valid @RequestBody CancelTransactionRequest request) {

            TransactionResponse response = transactionServiceClient.cancel(request);

            return ResponseEntity.ok(response);
    }

    /**
     * Consulta las transacciones de forma paginada.
     *
     * @param page      número de página a consultar, comenzando en cero
     * @param size      cantidad de registros por página, entre 1 y 100
     * @param sortBy    campo permitido por el cual se ordenarán los resultados
     * @param direction dirección del ordenamiento: asc o desc
     * @return respuesta paginada de transacciones
     * @throws IllegalArgumentException si alguno de los parámetros
     *                                  de paginación u ordenamiento no es válido
     */
    @Operation(
        summary = "Consultar transacciones",
        description = "Obtiene las transacciones utilizando paginación y ordenamiento.",
        security = @SecurityRequirement(name = "bearerAuth")
        )
    @GetMapping
    public ResponseEntity<TransactionPageResponse> findAll(
                    @RequestParam(defaultValue = "0") int page,
                    @RequestParam(defaultValue = "10") int size,
                    @RequestParam(defaultValue = "id") String sortBy,
                    @RequestParam(defaultValue = "asc") String direction) {

            if (page < 0) {
                    throw new IllegalArgumentException(
                                    "El parámetro page no puede ser menor que 0");
            }

            if (size < 1 || size > 100) {
                    throw new IllegalArgumentException(
                                    "El parámetro size debe estar entre 1 y 100");
            }

            if (!ALLOWED_SORT_FIELDS.contains(sortBy)) {
                    throw new IllegalArgumentException(
                                    "Campo de ordenamiento no permitido");
            }

            if (!"asc".equalsIgnoreCase(direction)
                            && !"desc".equalsIgnoreCase(direction)) {
                    throw new IllegalArgumentException(
                                    "La dirección debe ser asc o desc");
            }

            TransactionPageResponse response = transactionServiceClient.findAll(
                            page,
                            size,
                            sortBy,
                            direction);

            return ResponseEntity.ok(response);
    }

}
