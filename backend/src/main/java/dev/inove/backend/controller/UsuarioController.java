package dev.inove.backend.controller;

import dev.inove.backend.model.Usuario;
import dev.inove.backend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        try {
            List<Usuario> usuarios = service.listar();
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        try {
            Usuario user = service.login(usuario.getEmail(), usuario.getSenha());
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou senha inválidos");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao tentar fazer login.");
        }
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Usuario usuario) {
        try {
            if (service.buscar(usuario.getId()) != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Usuário já existe com o ID: " + usuario.getId());
            }
            Usuario novoUsuario = service.criar(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao criar usuário.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            if (service.buscar(id) != null) {
                Usuario atualizado = service.atualizar(id, usuario);
                return ResponseEntity.ok(atualizado);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Usuário não encontrado com o ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar usuário.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            if (service.buscar(id) != null) {
                service.deletar(id);
                return ResponseEntity.ok("Usuário deletado com sucesso.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Usuário não encontrado com o ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar usuário.");
        }
    }
}