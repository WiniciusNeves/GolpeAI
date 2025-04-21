package dev.inove.backend.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.inove.backend.model.Entregador;
import dev.inove.backend.model.Usuario;
import dev.inove.backend.model.ValidacaoEntrega;
import dev.inove.backend.repository.ValidacaoEntregaRepository;

@Service
public class ValidacaoEntregaService {

    @Autowired
    private ValidacaoEntregaRepository validacaoEntregaRepository;

    @Autowired
    private TentativaGolpeService tentativaGolpeService;

    public ValidacaoEntrega gerarValidacao(Usuario usuario, Entregador entregador) {
        ValidacaoEntrega validacao = new ValidacaoEntrega();
        validacao.setDataHora(LocalDateTime.now().toString());
        validacao.setStatus("PENDENTE");
        validacao.setCodigoVerificacao(gerarCodigoAleatorio());
        validacao.setUsuario(usuario);
        validacao.setEntregador(entregador);

        return validacaoEntregaRepository.save(validacao);
    }

    public boolean validarCodigo(String codigo, Long usuarioId, Long entregadorId) {
        Optional<ValidacaoEntrega> validacao = validacaoEntregaRepository
            .findByCodigoVerificacaoAndUsuarioIdAndEntregadorId(codigo, usuarioId, entregadorId);
        return validacao.isPresent();
    }

    private String gerarCodigoAleatorio() {
        return UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }

    public String concluirValidacao(String codigo, Long usuarioId, Long entregadorId) {
        Optional<ValidacaoEntrega> optionalValidacao = validacaoEntregaRepository
            .findByCodigoVerificacaoAndUsuarioIdAndEntregadorId(codigo, usuarioId, entregadorId);

        if (optionalValidacao.isPresent()) {
            ValidacaoEntrega validacao = optionalValidacao.get();
            validacao.setStatus("CONCLUIDO");
            validacaoEntregaRepository.save(validacao);
            return "Validação concluída com sucesso!";
        } else {
            tentativaGolpeService.registrarTentativa(
                codigo,
                usuarioId,
                entregadorId,
                "Código inválido informado na validação de entrega."
            );
            return "Código inválido! Suspeita de golpe registrada.";
        }
    }
}
