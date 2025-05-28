package dev.inove.backend.repository;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.inove.backend.model.AuthUser;

/**
 * Interface responsável por realizar operações de CRUD com usuários autenticados.
 * 
 * @author Marcelo Farias
 *
 */
@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
    static final Logger LOGGER = LoggerFactory.getLogger(AuthUserRepository.class);

    /**
     * Busca um usuário autenticado pelo email.
     * 
     * @param email email do usuário a ser buscado
     * @return um Optional com o usuário encontrado, ou vazio caso não encontre
     */
    Optional<AuthUser> findByEmail(String email);
}

