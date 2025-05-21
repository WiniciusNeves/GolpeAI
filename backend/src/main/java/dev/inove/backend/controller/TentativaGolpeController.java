package dev.inove.backend.controller;

import dev.inove.backend.model.TentativaGolpe;
import dev.inove.backend.repository.TentativaGolpeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador responsável por listar tentativas de golpe registradas.
 */
@RestController
@RequestMapping("/api/tentativas-golpe")
@Slf4j
public class TentativaGolpeController {

    @Autowired
    private TentativaGolpeRepository tentativaGolpeRepository;

    /**
     * Retorna todas as tentativas de golpe registradas.
     */
    @GetMapping
    public ResponseEntity<?> listarTentativas() {
        log.info("Requisição para listar tentativas de golpe.");
        try {
            List<TentativaGolpe> tentativas = tentativaGolpeRepository.findAll();
            log.info("Foram encontradas {} tentativas de golpe.", tentativas.size());
            return ResponseEntity.ok(tentativas);
        } catch (Exception e) {
            log.error("Erro ao listar tentativas de golpe.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Erro ao listar tentativas de golpe.");
        }
    }
}
