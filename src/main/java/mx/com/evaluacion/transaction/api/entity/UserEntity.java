package mx.com.evaluacion.transaction.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Representa un usuario registrado para autenticación.
 */
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "username",
            nullable = false,
            unique = true,
            length = 50
    )
    private String username;

    @Column(
            name = "password",
            nullable = false,
            length = 100
    )
    private String password;

    /**
     * Obtiene el identificador del usuario.
     *
     * @return identificador del usuario
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador del usuario.
     *
     * @param id identificador del usuario
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre de usuario.
     *
     * @return nombre de usuario
     */
    public String getUsername() {
        return username;
    }

    /**
     * Establece el nombre de usuario.
     *
     * @param username nombre de usuario
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Obtiene el hash de la contraseña.
     *
     * @return contraseña almacenada como hash BCrypt
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece el hash de la contraseña.
     *
     * @param password hash BCrypt
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
