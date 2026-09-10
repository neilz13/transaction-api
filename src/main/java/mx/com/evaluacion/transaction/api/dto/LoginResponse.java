package mx.com.evaluacion.transaction.api.dto;

/**
 * Representa la respuesta generada después de un login exitoso.
 */
public class LoginResponse {

    private String token;

    /**
     * Constructor vacío requerido para serialización.
     */
    public LoginResponse() {
    }

    /**
     * Crea una respuesta de login.
     *
     * @param token token JWT
     */
    public LoginResponse(String token) {
        this.token = token;
    }

    /**
     * Obtiene el token JWT.
     *
     * @return token JWT
     */
    public String getToken() {
        return token;
    }

    /**
     * Establece el token JWT.
     *
     * @param token token JWT
     */
    public void setToken(String token) {
        this.token = token;
    }
}
