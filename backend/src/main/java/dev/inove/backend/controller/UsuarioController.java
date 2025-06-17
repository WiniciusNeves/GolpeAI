package dev.inove.backend.controller;

import dev.inove.backend.model.Usuario;
import dev.inove.backend.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador responsável por operações com usuários.
 */
@RestController
@RequestMapping("/api/usuarios")
@Slf4j
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    /**
     * Lista todos os usuários.
     */
    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        log.info("Listando todos os usuários.");
        try {
            List<Usuario> usuarios = service.listar();
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            log.error("Erro ao listar usuários.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Cria um novo usuário.
     */
    @PostMapping("/")
    public ResponseEntity<?> criar(@RequestBody Usuario usuario) {
        log.info("Criando novo usuário.");
        try {
            if (usuario.getId() != null && service.buscar(usuario.getId()) != null) {
                log.warn("Usuário já existe com ID: {}", usuario.getId());
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Usuário já existe com o ID: " + usuario.getId());
            }

            Usuario novoUsuario = service.criar(usuario);
            log.info("Usuário criado com sucesso: {}", novoUsuario.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);

        } catch (Exception e) {
            log.error("Erro ao criar usuário.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao criar usuário.");
        }
    }

    /**
     * Atualiza os dados de um usuário pelo ID.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        log.info("Atualizando usuário com ID: {}", id);
        try {
            if (service.buscar(id) != null) {
                Usuario atualizado = service.atualizar(id, usuario);
                return ResponseEntity.ok(atualizado);
            } else {
                log.warn("Usuário não encontrado com ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Usuário não encontrado com o ID: " + id);
            }
        } catch (Exception e) {
            log.error("Erro ao atualizar usuário.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar usuário.");
        }
    }

    /**
     * Remove um usuário pelo ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        log.info("Deletando usuário com ID: {}", id);
        try {
            if (service.buscar(id) != null) {
                service.deletar(id);
                log.info("Usuário deletado com sucesso.");
                return ResponseEntity.ok("Usuário deletado com sucesso.");
            } else {
                log.warn("Usuário não encontrado com ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Usuário não encontrado com o ID: " + id);
            }
        } catch (Exception e) {
            log.error("Erro ao deletar usuário.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar usuário.");
        }
    }
}
