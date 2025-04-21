package dev.inove.backend.controller;

import dev.inove.backend.model.Entregador;
import dev.inove.backend.service.EntregadorService;
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
    public List<Entregador> listar() {
        return service.listar();
    }

    @PostMapping
    public Entregador criar(@RequestBody Entregador entregador) {
        return service.criar(entregador);
    }

    @GetMapping("/{id}")
    public Entregador buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }

    @PutMapping("/{id}")
    public Entregador atualizar(@PathVariable Long id, @RequestBody Entregador entregador) {
        return service.atualizar(id, entregador);
    }
}

