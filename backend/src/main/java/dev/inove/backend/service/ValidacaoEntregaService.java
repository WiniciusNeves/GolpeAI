package dev.inove.backend.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.inove.backend.model.Entregador;
import dev.inove.backend.model.Usuario;
import dev.inove.backend.model.ValidacaoEntrega;
import dev.inove.backend.repository.ValidacaoEntregaRepository;

/**
 * Serviço responsável pela geração, validação e conclusão de códigos de entrega.
 */
@Service
public class ValidacaoEntregaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidacaoEntregaService.class);
    @Autowired
    private ValidacaoEntregaRepository validacaoEntregaRepository;
    
    @Autowired
    private TentativaGolpeService tentativaGolpeService;
    
    /**
     * Gera um novo código de validação entre usuário e entregador.
     * 
     * @param usuario   o usuário que fará a validação
     * @param entregador o entregador que fará a entrega
     * @return o código de validação gerado
     */
    public void setValidacaoEntregaRepository(ValidacaoEntregaRepository repository) {
        this.validacaoEntregaRepository = repository;
    }

    public ValidacaoEntrega gerarValidacao(Usuario usuario, Entregador entregador) {
        ValidacaoEntrega validacao = new ValidacaoEntrega();
        validacao.setDataHora(LocalDateTime.now());
        validacao.setStatus("PENDENTE");
        validacao.setCodigoVerificacao(gerarCodigoAleatorio(usuario.getId(), entregador.getId()));
        validacao.setUsuario(usuario);
        validacao.setEntregador(entregador);
    
        return validacaoEntregaRepository.save(validacao);
    }

    /**
     * Valida um código de entrega com base em usuário e entregador.
     * 
     * @param codigo       o código informado na validação
     * @param usuarioId    o identificador do usuário
     * @param entregadorId o identificador do entregador
     * @return true se o código for válido, false caso contrário
     */
    public boolean validarCodigo(String codigo, Long usuarioId, Long entregadorId) {
        Optional<ValidacaoEntrega> validacao = validacaoEntregaRepository
                .findByCodigoVerificacaoAndUsuarioIdAndEntregadorId(codigo, usuarioId, entregadorId);
        return validacao.isPresent();
    }

    /**
     * Gera um código aleatório único para a validação de entrega.
     * 
     * @param usuarioId    o identificador do usuário
     * @param entregadorId o identificador do entregador
     * @return o código gerado
     */
    private String gerarCodigoAleatorio(Long usuarioId, Long entregadorId) {
        String codigo;
        do {
            codigo = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        } while (validacaoEntregaRepository
                .findByCodigoVerificacaoAndUsuarioIdAndEntregadorId(codigo, usuarioId, entregadorId).isPresent());
        return codigo;
    }

    /**
     * Conclui uma validação de entrega após a confirmação do código.
     * 
     * @param codigo       o código informado na validação
     * @param usuarioId    o identificador do usuário
     * @param entregadorId o identificador do entregador
     * @return uma mensagem de sucesso ou erro
     */
    public String concluirValidacao(String codigo, Long usuarioId, Long entregadorId) {
        Optional<ValidacaoEntrega> optionalValidacao = validacaoEntregaRepository
                .findByCodigoVerificacaoAndUsuarioIdAndEntregadorId(codigo, usuarioId, entregadorId);

        if (optionalValidacao.isPresent()) {
            ValidacaoEntrega validacao = optionalValidacao.get();
            validacao.setStatus("CONCLUIDO");
            validacaoEntregaRepository.save(validacao);
            LOGGER.info("Validação concluída com sucesso!");
            return "Validação concluída com sucesso!";
        } else {
            tentativaGolpeService.registrarTentativa(
                    codigo,
                    usuarioId,
                    entregadorId,
                    "Código inválido informado na validação de entrega.");
            LOGGER.warn("Código inválido! Suspeita de golpe registrada.");
            return "Código inválido! Suspeita de golpe registrada.";
        }
    }
}
