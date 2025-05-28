package dev.inove.backend.controller;

import dev.inove.backend.model.AuthUser;
import dev.inove.backend.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/**
 * Controlador responsável por operações de autenticação de usuários.
 */
@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * Realiza o login de um usuário.
     *
     * @param auth dados de autenticação do usuário
     * @return um token JWT caso o login seja bem-sucedido, ou um erro caso contrário
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthUser auth) {
        log.info("Requisição de login para o usuário: {}", auth.getEmail());
        String token = authService.login(auth.getEmail(), auth.getSenha());
        if (token != null) {
            log.info("Login bem-sucedido para o usuário: {}", auth.getEmail());
            return ResponseEntity.ok(Collections.singletonMap("token", token));
        }
        log.warn("Credenciais inválidas para o usuário: {}", auth.getEmail());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
    }

    /**
     * Registra um novo usuário.
     *
     * @param user dados do usuário a ser registrado
     * @return o usuário registrado
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthUser user) {
        log.info("Requisição de registro para o usuário: {}", user.getEmail());
        AuthUser created = authService.register(user);
        log.info("Usuário registrado com sucesso: {}", user.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}

