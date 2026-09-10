package mx.com.evaluacion.transaction.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.com.evaluacion.transaction.api.entity.UserEntity;

/**
 * Repositorio para acceder a los usuarios de la aplicación.
 */
public interface UserRepository
        extends JpaRepository<UserEntity, Long> {

    /**
     * Busca un usuario por nombre de usuario.
     *
     * @param username nombre de usuario
     * @return usuario encontrado, si existe
     */
    Optional<UserEntity> findByUsername(String username);
}
