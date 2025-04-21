package dev.inove.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import dev.inove.backend.model.TentativaGolpe;
import dev.inove.backend.repository.TentativaGolpeRepository;

@RestController
@RequestMapping("/tentativas-golpe")
public class TentativaGolpeController {

    @Autowired
    private TentativaGolpeRepository tentativaGolpeRepository;

    @GetMapping
    public List<TentativaGolpe> listarTentativas() {
        return tentativaGolpeRepository.findAll();
    }
}
