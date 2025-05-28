package dev.inove.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/**
 * Representa um usuário autenticado no sistema.
 */
@Entity
@Table(name = "auth_users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthUser {

    private static final Logger log = LoggerFactory.getLogger(AuthUser.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome do usuário autenticado.
     */
    private String nome;

    /**
     * Email do usuário autenticado.
     */
    private String email;

    /**
     * Senha do usuário autenticado.
     * <p>
     * <b>Nota:</b> Esta senha é armazenada em forma de hash, para fins de segurança.
     */
    private String senha;

    public AuthUser(String nome, String email, String senha) {
        log.info("[AuthUser] Criando AuthUser com email: {}", email);
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }
}

