package dev.inove.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dev.inove.backend.model.Entregador;
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
    public ResponseEntity<?> gerarValidacao(@PathVariable Long usuarioId, @PathVariable Long entregadorId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElse(null);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }

        Entregador entregador = entregadorRepository.findById(entregadorId)
                .orElse(null);
        if (entregador == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entregador não encontrado");
        }

        ValidacaoEntrega validacao = validacaoEntregaService.gerarValidacao(usuario, entregador);
        return ResponseEntity.status(HttpStatus.CREATED).body(validacao);
    }

    @GetMapping("/validar")
    public ResponseEntity<?> validarCodigo(
            @RequestParam String codigo,
            @RequestParam Long usuarioId,
            @RequestParam Long entregadorId) {

        boolean valido = validacaoEntregaService.validarCodigo(codigo, usuarioId, entregadorId);
        if (valido) {
            return ResponseEntity.ok("Código válido!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Código inválido ou não localizado");
        }
    }

    @PutMapping("/concluir")
    public ResponseEntity<String> concluirValidacao(
            @RequestParam String codigo,
            @RequestParam Long usuarioId,
            @RequestParam Long entregadorId) {

        String resultado = validacaoEntregaService.concluirValidacao(codigo, usuarioId, entregadorId);
        if (resultado.contains("sucesso")) {
            return ResponseEntity.ok(resultado);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultado);
        }
    }
}