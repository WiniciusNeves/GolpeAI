package dev.inove.backend.controller;

import dev.inove.backend.DTO.TentativaGolpeRequest;
import dev.inove.backend.model.Entregador;
import dev.inove.backend.model.Usuario;
import dev.inove.backend.model.ValidacaoEntrega;
import dev.inove.backend.repository.EntregadorRepository;
import dev.inove.backend.repository.UsuarioRepository;
import dev.inove.backend.repository.ValidacaoEntregaRepository;
import dev.inove.backend.service.TentativaGolpeService;
import dev.inove.backend.service.ValidacaoEntregaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/validacao")
@Slf4j
public class ValidacaoEntregaController {

    @Autowired
    private ValidacaoEntregaService validacaoEntregaService;

    @Autowired
    private TentativaGolpeService tentativaGolpeService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EntregadorRepository entregadorRepository;

    @Autowired
    private ValidacaoEntregaRepository validacaoEntregaRepository;

    @GetMapping("/")
    public ResponseEntity<?> listarTodosCodigos() {
        log.info("Listando todos os códigos de validação.");
        try {
            return ResponseEntity.ok(validacaoEntregaRepository.findAll());
        } catch (Exception e) {
            log.error("Erro ao listar códigos de validação", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao listar códigos.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarCodigo(@PathVariable Long id) {
        log.info("Buscando código de validação com ID: {}", id);
        try {
            ValidacaoEntrega codigo = validacaoEntregaRepository.findById(id).orElse(null);
            if (codigo == null) {
                log.warn("Código não encontrado com ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Código de validação não encontrado.");
            }
            return ResponseEntity.ok(codigo);
        } catch (Exception e) {
            log.error("Erro ao buscar código de validação", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao buscar código.");
        }
    }

    @PostMapping("/gerar/{usuarioId}/{entregadorId}")
    public ResponseEntity<?> gerarValidacao(@PathVariable Long usuarioId, @PathVariable Long entregadorId) {
        log.info("Gerando código para usuário ID: {} e entregador ID: {}", usuarioId, entregadorId);
        try {
            Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
            if (usuario == null) {
                log.warn("Usuário não encontrado com ID: {}", usuarioId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
            }

            Entregador entregador = entregadorRepository.findById(entregadorId).orElse(null);
            if (entregador == null) {
                log.warn("Entregador não encontrado com ID: {}", entregadorId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entregador não encontrado.");
            }

            ValidacaoEntrega validacao = validacaoEntregaService.gerarValidacao(usuario, entregador);
            log.info("Código gerado: {}", validacao.getCodigoVerificacao());
            return ResponseEntity.status(HttpStatus.CREATED).body(validacao);
        } catch (Exception e) {
            log.error("Erro ao gerar código de validação", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao gerar código.");
        }
    }

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
                return ResponseEntity.ok(Map.of(
                        "status", "ok",
                        "message", "Código válido!"));
            } else {
                log.warn("Código inválido ou não localizado.");

                // Registra tentativa de golpe para código inválido
                tentativaGolpeService.registrarTentativa(codigo, usuarioId, entregadorId,
                        "Código inválido no momento da validação");

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of(
                                "status", "golpe",
                                "message", "Código inválido. Possível tentativa de golpe registrada."));
            }
        } catch (Exception e) {
            log.error("Erro ao validar código.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "error",
                    "message", "Erro ao validar código."));
        }
    }

    @PutMapping("/concluir")
    public ResponseEntity<String> concluirValidacao(
            @RequestParam String codigo,
            @RequestParam Long usuarioId,
            @RequestParam Long entregadorId) {
        log.info("Concluindo validação do código: {}", codigo);
        try {
            String resultado = validacaoEntregaService.concluirValidacao(codigo, usuarioId, entregadorId);
            if (resultado.toLowerCase().contains("sucesso")) {
                log.info("Validação concluída com sucesso.");
                return ResponseEntity.ok(resultado);
            } else {
                log.warn("Erro ao concluir validação: {}", resultado);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultado);
            }
        } catch (Exception e) {
            log.error("Erro ao concluir validação", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao concluir validação.");
        }
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<String> excluirValidacao(@PathVariable Long id) {
        log.info("Excluindo validação com ID: {}", id);
        try {
            ValidacaoEntrega v = validacaoEntregaRepository.findById(id).orElse(null);
            if (v == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Validação não encontrada.");
            }

            // Dissocia antes de excluir para evitar erro de FK
            v.setUsuario(null);
            v.setEntregador(null);
            validacaoEntregaRepository.save(v);

            validacaoEntregaRepository.deleteById(id);
            log.info("Validação excluída com sucesso.");
            return ResponseEntity.ok("Validação excluída com sucesso!");
        } catch (Exception e) {
            log.error("Erro ao excluir validação", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao excluir validação.");
        }
    }

    @PostMapping("/denunciar-golpe")
    public ResponseEntity<?> registrarTentativaGolpe(@RequestBody TentativaGolpeRequest request) {
        log.info("Recebida denúncia de tentativa de golpe com código: {}", request.getCodigo());
        try {
            // Buscar a validação pelo código, usuarioId e entregadorId
            ValidacaoEntrega validacao = validacaoEntregaRepository
                    .findByCodigoVerificacaoAndUsuarioIdAndEntregadorId(
                            request.getCodigo(),
                            request.getUsuarioId(),
                            request.getEntregadorId())
                    .orElse(null);

            if (validacao == null) {
                log.warn("Validação não encontrada para denúncia");
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("status", "ERROR", "message", "Validação não encontrada para denúncia."));
            }

            // Atualizar status para DENUNCIADO
            validacao.setStatus("DENUNCIADO");
            validacaoEntregaRepository.save(validacao);

            // Registrar a tentativa de golpe
            tentativaGolpeService.registrarTentativa(
                    request.getCodigo(),
                    request.getUsuarioId(),
                    request.getEntregadorId(),
                    "Denúncia de golpe manual pelo usuário");

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of(
                            "status", "OK",
                            "message", "Tentativa de golpe registrada e status atualizado para DENUNCIADO."));
        } catch (Exception e) {
            log.error("Erro ao registrar tentativa de golpe", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status", "ERROR",
                            "message", "Erro ao registrar tentativa de golpe."));
        }
    }
}
