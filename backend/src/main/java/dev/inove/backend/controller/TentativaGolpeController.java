package dev.inove.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dev.inove.backend.model.TentativaGolpe;
import dev.inove.backend.repository.TentativaGolpeRepository;

@RestController
@RequestMapping("/tentativas-golpe")
public class TentativaGolpeController {

    @Autowired
    private TentativaGolpeRepository tentativaGolpeRepository;

    @GetMapping
    public ResponseEntity<?> listarTentativas() {
        try {
            List<TentativaGolpe> tentativas = tentativaGolpeRepository.findAll();
            return ResponseEntity.ok(tentativas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Erro ao listar tentativas de golpe.");
        }
    }
}