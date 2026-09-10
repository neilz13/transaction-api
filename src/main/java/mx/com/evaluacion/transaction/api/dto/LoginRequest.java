package mx.com.evaluacion.transaction.api.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Representa las credenciales utilizadas para iniciar sesión.
 */
public class LoginRequest {

    @NotBlank(message = "El usuario es obligatorio")
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    /**
     * Obtiene el usuario.
     *
     * @return nombre de usuario
     */
    public String getUsername() {
        return username;
    }

    /**
     * Establece el usuario.
     *
     * @param username nombre de usuario
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Obtiene la contraseña.
     *
     * @return contraseña
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña.
     *
     * @param password contraseña
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
