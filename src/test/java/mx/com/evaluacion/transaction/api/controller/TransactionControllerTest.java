package mx.com.evaluacion.transaction.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import mx.com.evaluacion.transaction.api.client.TransactionServiceClient;
import mx.com.evaluacion.transaction.api.dto.TransactionRequest;
import mx.com.evaluacion.transaction.api.dto.TransactionResponse;
import mx.com.evaluacion.transaction.api.dto.TransactionServiceRequest;
import mx.com.evaluacion.transaction.api.service.EncryptionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Pruebas unitarias del controlador de transacciones.
 */
@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

    @Mock
    private TransactionServiceClient transactionServiceClient;

    @Mock
    private EncryptionService encryptionService;

    @InjectMocks
    private TransactionController controller;

    /**
     * Verifica el registro exitoso de una transacción.
     */
    @Test
    void shouldCreateTransactionSuccessfully() {

        TransactionRequest request = new TransactionRequest();
        request.setOperacion("venta");
        request.setImporte(new BigDecimal("100.00"));
        request.setCliente("Angel");
        request.setSecreto("encrypted-secret");

        when(encryptionService.decrypt("encrypted-secret"))
                .thenReturn("secret");

        TransactionResponse expectedResponse =
                new TransactionResponse();

        expectedResponse.setId(1L);
        expectedResponse.setEstatus("APROBADA");
        expectedResponse.setReferencia("123456");
        expectedResponse.setOperacion("venta");

        when(transactionServiceClient.create(
                any(TransactionServiceRequest.class)))
                .thenReturn(expectedResponse);

        ResponseEntity<TransactionResponse> response =
                controller.createTransaction(request);

        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );

        assertNotNull(response.getBody());

        assertEquals(
                1L,
                response.getBody().getId()
        );

        assertEquals(
                "APROBADA",
                response.getBody().getEstatus()
        );

        verify(encryptionService)
                .decrypt("encrypted-secret");

        verify(transactionServiceClient)
                .create(any(TransactionServiceRequest.class));
    }
}
