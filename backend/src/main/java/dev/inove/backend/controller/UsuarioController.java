package dev.inove.backend.controller;

import dev.inove.backend.model.Usuario;
import dev.inove.backend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
    @RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping
    public List<Usuario> listar() {
        return service.listar();
    }

    @PostMapping("/login")
    public Usuario login(@RequestBody Usuario usuario) {
        return service.login(usuario.getEmail(), usuario.getSenha());
    }

    @PostMapping
    public Usuario criar(@RequestBody Usuario usuario) {
        return service.criar(usuario);
    }

    @PutMapping("/{id}")
    public Usuario atualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        return service.atualizar(id, usuario);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}

