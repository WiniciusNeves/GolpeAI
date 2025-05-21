package dev.inove.backend.controller;

import dev.inove.backend.model.Entregador;
import dev.inove.backend.service.EntregadorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador responsável pelos endpoints de Entregador.
 */
@RestController
@RequestMapping("/api/entregadores")
@Slf4j
public class EntregadorController {

    private final EntregadorService service;

    public EntregadorController(EntregadorService service) {
        this.service = service;
    }

    /**
     * Lista todos os entregadores cadastrados.
     */
    @GetMapping
    public ResponseEntity<List<Entregador>> listar() {
        log.info("Requisição para listar todos os entregadores.");
        try {
            List<Entregador> lista = service.listar();
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            log.error("Erro ao listar entregadores.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Cria um novo entregador.
     *
     * @param entregador dados do entregador
     */
    @PostMapping
    public ResponseEntity<Entregador> criar(@RequestBody Entregador entregador) {
        log.info("Requisição para criar entregador: {}", entregador);
        try {
            Entregador novo = service.criar(entregador);
            return ResponseEntity.status(HttpStatus.CREATED).body(novo);
        } catch (Exception e) {
            log.error("Erro ao criar entregador.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Busca um entregador pelo ID.
     *
     * @param id identificador do entregador
     */
    @GetMapping("/{id}")
    public ResponseEntity<Entregador> buscar(@PathVariable Long id) {
        log.info("Requisição para buscar entregador com ID: {}", id);
        try {
            Entregador entregador = service.buscar(id);
            if (entregador != null) {
                return ResponseEntity.ok(entregador);
            } else {
                log.warn("Entregador não encontrado com ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            log.error("Erro ao buscar entregador com ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Remove um entregador pelo ID.
     *
     * @param id identificador do entregador
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        log.info("Requisição para deletar entregador com ID: {}", id);
        try {
            if (service.buscar(id) != null) {
                service.deletar(id);
                return ResponseEntity.noContent().build();
            } else {
                log.warn("Entregador não encontrado para exclusão com ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            log.error("Erro ao deletar entregador com ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Atualiza os dados de um entregador.
     *
     * @param id identificador do entregador
     * @param entregador dados atualizados
     */
    @PutMapping("/{id}")
    public ResponseEntity<Entregador> atualizar(@PathVariable Long id, @RequestBody Entregador entregador) {
        log.info("Requisição para atualizar entregador com ID: {}", id);
        try {
            if (service.buscar(id) != null) {
                Entregador atualizado = service.atualizar(id, entregador);
                return ResponseEntity.ok(atualizado);
            } else {
                log.warn("Entregador não encontrado para atualização com ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            log.error("Erro ao atualizar entregador com ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
