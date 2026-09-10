package mx.com.evaluacion.transaction.api.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class EncryptionServiceTest {

    private static final String TEST_KEY =
            "0123456789abcdef0123456789abcdef";

    private final EncryptionService encryptionService =
            new EncryptionService(TEST_KEY);

    @Test
    void shouldEncryptAndDecryptText() {

        String original = "jejdjw134&3#$$";

        String encrypted =
                encryptionService.encrypt(original);

        String decrypted =
                encryptionService.decrypt(encrypted);

        System.out.println(encrypted);

        assertNotEquals(original, encrypted);
        assertEquals(original, decrypted);
    }
}
