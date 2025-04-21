package dev.inove.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import dev.inove.backend.model.Entregador;
import dev.inove.backend.model.TentativaGolpe;
import dev.inove.backend.model.Usuario;
import dev.inove.backend.model.ValidacaoEntrega;
import dev.inove.backend.repository.EntregadorRepository;
import dev.inove.backend.repository.UsuarioRepository;
import dev.inove.backend.service.ValidacaoEntregaService;

@RestController
@RequestMapping("/api/validacao")
public class ValidacaoEntregaController {

    @Autowired
    private ValidacaoEntregaService validacaoEntregaService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EntregadorRepository entregadorRepository;

    @PostMapping("/gerar/{usuarioId}/{entregadorId}")
    public ValidacaoEntrega gerarValidacao(@PathVariable Long usuarioId, @PathVariable Long entregadorId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Entregador entregador = entregadorRepository.findById(entregadorId)
                .orElseThrow(() -> new RuntimeException("Entregador não encontrado"));

        return validacaoEntregaService.gerarValidacao(usuario, entregador);
    }

    @GetMapping("/validar")
    public boolean validarCodigo(
            @RequestParam String codigo,
            @RequestParam Long usuarioId,
            @RequestParam Long entregadorId) {
        return validacaoEntregaService.validarCodigo(codigo, usuarioId, entregadorId);
    }

    @PutMapping("/concluir")
    public String concluirValidacao(
            @RequestParam String codigo,
            @RequestParam Long usuarioId,
            @RequestParam Long entregadorId) {
        return validacaoEntregaService.concluirValidacao(codigo, usuarioId, entregadorId);
    }
    
}
