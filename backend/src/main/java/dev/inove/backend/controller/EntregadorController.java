package dev.inove.backend.controller;

import dev.inove.backend.model.Entregador;
import dev.inove.backend.service.EntregadorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entregadores")
public class EntregadorController {

    private final EntregadorService service;

    public EntregadorController(EntregadorService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        try {
            List<Entregador> lista = service.listar();
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao listar entregadores.");
        }
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Entregador entregador) {
        try {
            Entregador novo = service.criar(entregador);
            return ResponseEntity.status(HttpStatus.CREATED).body(novo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao criar entregador.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        try {
            Entregador entregador = service.buscar(id);
            if (entregador != null) {
                return ResponseEntity.ok(entregador);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Entregador não encontrado com ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar entregador.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            if (service.buscar(id) != null) {
                service.deletar(id);
                return ResponseEntity.ok("Entregador deletado com sucesso.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Entregador não encontrado com ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar entregador.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Entregador entregador) {
        try {
            if (service.buscar(id) != null) {
                Entregador atualizado = service.atualizar(id, entregador);
                return ResponseEntity.ok(atualizado);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Entregador não encontrado com ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar entregador.");
        }
    }
}