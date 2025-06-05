package dev.inove.backend.controller;

import dev.inove.backend.model.Entregador;
import dev.inove.backend.model.Usuario;
import dev.inove.backend.model.ValidacaoEntrega;
import dev.inove.backend.repository.EntregadorRepository;
import dev.inove.backend.repository.UsuarioRepository;
import dev.inove.backend.repository.ValidacaoEntregaRepository;
import dev.inove.backend.service.ValidacaoEntregaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador responsável pela geração, validação e conclusão de códigos de
 * entrega.
 */
@RestController
@RequestMapping("/api/validacao")
@Slf4j
public class ValidacaoEntregaController {

    @Autowired
    private ValidacaoEntregaService validacaoEntregaService;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private EntregadorRepository entregadorRepository;

    @Autowired
    private ValidacaoEntregaRepository validacaoEntregaRepository;


    /**
     * Retorna todos os códigos de validação gerados.
     */
    @GetMapping("/todos")
    public ResponseEntity<?> listarTodosCodigos() {
        log.info("Listando todos os códigos de validação.");
        try {
            return ResponseEntity.ok(validacaoEntregaRepository.findAll());
        } catch (Exception e) {
            log.error("Erro ao listar códigos de validação.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao listar códigos.");
        }
    }

    /**
     * Gera um novo código de validação entre usuário e entregador.
     */
    @PostMapping("/gerar/{usuarioId}/{entregadorId}")
    public ResponseEntity<?> gerarValidacao(@PathVariable Long usuarioId, @PathVariable Long entregadorId) {
        log.info("Gerando código de validação para usuário ID: {} e entregador ID: {}", usuarioId, entregadorId);
        try {
            Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
            if (usuario == null) {
                log.warn("Usuário não encontrado com ID: {}", usuarioId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
            }

            Entregador entregador = entregadorRepository.findById(entregadorId).orElse(null);
            if (entregador == null) {
                log.warn("Entregador não encontrado com ID: {}", entregadorId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entregador não encontrado");
            }

            ValidacaoEntrega validacao = validacaoEntregaService.gerarValidacao(usuario, entregador);
            log.info("Código gerado com sucesso: {}", validacao.getCodigoVerificacao());
            return ResponseEntity.status(HttpStatus.CREATED).body(validacao);
        } catch (Exception e) {
            log.error("Erro ao gerar código de validação.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao gerar código.");
        }
    }

    /**
     * Valida um código de entrega com base em usuário e entregador.
     */
    @GetMapping("/validar")
    public ResponseEntity<?> validarCodigo(
            @RequestParam String codigo,
            @RequestParam Long usuarioId,
            @RequestParam Long entregadorId) {
        log.info("Validando código: {}", codigo);
        try {
            boolean valido = validacaoEntregaService.validarCodigo(codigo, usuarioId, entregadorId);
            if (valido) {
                log.info("Código válido.");
                return ResponseEntity.ok("Código válido!");
            } else {
                log.warn("Código inválido ou não localizado.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Código inválido ou não localizado");
            }
        } catch (Exception e) {
            log.error("Erro ao validar código.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao validar código.");
        }
    }

    /**
     * Conclui uma validação de entrega após a confirmação do código.
     */
    @PutMapping("/concluir")
    public ResponseEntity<String> concluirValidacao(
            @RequestParam String codigo,
            @RequestParam Long usuarioId,
            @RequestParam Long entregadorId) {
        log.info("Concluindo validação para código: {}", codigo);
        try {
            String resultado = validacaoEntregaService.concluirValidacao(codigo, usuarioId, entregadorId);
            if (resultado.toLowerCase().contains("sucesso")) {
                log.info("Validação concluída com sucesso.");
                return ResponseEntity.ok(resultado);
            } else {
                log.warn("Falha ao concluir validação: {}", resultado);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultado);
            }
        } catch (Exception e) {
            log.error("Erro ao concluir validação.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao concluir validação.");
        }
    }

    /**
     * Exclui um código de validação.
     */
    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<String> excluirValidacao(@PathVariable Long id) {
        log.info("Excluindo validação com ID: {}", id);
        try {
            validacaoEntregaRepository.deleteById(id);
            log.info("Validação excluída com sucesso.");
            return ResponseEntity.ok("Validação excluída com sucesso!");
        } catch (Exception e) {
            log.error("Erro ao excluir validação.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao excluir validação.");
        }
    }
}

