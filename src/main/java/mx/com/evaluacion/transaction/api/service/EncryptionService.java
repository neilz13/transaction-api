package mx.com.evaluacion.transaction.api.service;

import javax.crypto.AEADBadTagException;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Servicio encargado de cifrar y descifrar información utilizando
 * AES-256-GCM.
 *
 * <p>El valor cifrado contiene el IV necesario para realizar
 * posteriormente el descifrado.</p>
 */
public class EncryptionService {

    /**
     * Algoritmo utilizado para el cifrado.
     */
    private static final String AES_ALGORITHM = "AES";

    /**
     * Transformación utilizada por AES.
     */
    private static final String AES_TRANSFORMATION = "AES/GCM/NoPadding";

    /**
     * Tamaño del IV utilizado por GCM.
     */
    private static final int IV_LENGTH = 12;

    /**
     * Tamaño de la etiqueta de autenticación en bits.
     */
    private static final int GCM_TAG_LENGTH = 128;

    private final SecretKeySpec secretKey;

    private final SecureRandom secureRandom;

    /**
     * Constructor del servicio.
     *
     * @param encryptionKey clave AES de 256 bits
     */
    public EncryptionService(String encryptionKey) {
        validateKey(encryptionKey);

        this.secretKey = new SecretKeySpec(
                encryptionKey.getBytes(StandardCharsets.UTF_8),
                AES_ALGORITHM
        );

        this.secureRandom = new SecureRandom();
    }

    /**
     * Cifra un texto utilizando AES-256-GCM.
     *
     * @param plainText texto que será cifrado
     * @return texto cifrado codificado en Base64
     */
    public String encrypt(String plainText) {
        try {
            byte[] iv = new byte[IV_LENGTH];
            secureRandom.nextBytes(iv);

            Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);

            GCMParameterSpec gcmSpec =
                    new GCMParameterSpec(GCM_TAG_LENGTH, iv);

            cipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmSpec);

            byte[] encrypted =
                    cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

            ByteBuffer buffer =
                    ByteBuffer.allocate(iv.length + encrypted.length);

            buffer.put(iv);
            buffer.put(encrypted);

            return Base64.getEncoder().encodeToString(buffer.array());

        } catch (GeneralSecurityException exception) {
            throw new IllegalStateException(
                    "No fue posible cifrar la información",
                    exception
            );
        }
    }

    /**
     * Descifra información previamente cifrada mediante AES-256-GCM.
     *
     * @param encryptedText texto cifrado codificado en Base64
     * @return texto original
     */
    public String decrypt(String encryptedText) {
        try {
            byte[] decoded =
                    Base64.getDecoder().decode(encryptedText);

            if (decoded.length <= IV_LENGTH) {
                throw new IllegalArgumentException(
                        "El valor cifrado no tiene un formato válido"
                );
            }

            ByteBuffer buffer = ByteBuffer.wrap(decoded);

            byte[] iv = new byte[IV_LENGTH];
            buffer.get(iv);

            byte[] encrypted = new byte[buffer.remaining()];
            buffer.get(encrypted);

            Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);

            GCMParameterSpec gcmSpec =
                    new GCMParameterSpec(GCM_TAG_LENGTH, iv);

            cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmSpec);

            byte[] decrypted = cipher.doFinal(encrypted);

            return new String(
                    decrypted,
                    StandardCharsets.UTF_8
            );

        } catch (AEADBadTagException exception) {
            throw new IllegalArgumentException(
                    "El valor cifrado no es válido",
                    exception
            );
        } catch (GeneralSecurityException |
                 IllegalArgumentException exception) {
            throw new IllegalArgumentException(
                    "No fue posible descifrar la información",
                    exception
            );
        }
    }

    /**
     * Valida que la clave proporcionada tenga exactamente 256 bits.
     *
     * @param encryptionKey clave a validar
     */
    private void validateKey(String encryptionKey) {
        if (encryptionKey == null
                || encryptionKey.getBytes(StandardCharsets.UTF_8).length != 32) {
            throw new IllegalArgumentException(
                    "La clave de cifrado debe tener exactamente 32 bytes"
            );
        }
    }
}
