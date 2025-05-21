package dev.inove.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

import dev.inove.backend.model.TentativaGolpe;
import dev.inove.backend.repository.TentativaGolpeRepository;

/**
 * Serviço responsável por registrar tentativas suspeitas de golpe.
 */
@Service
public class TentativaGolpeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TentativaGolpeService.class);

    @Autowired
    private TentativaGolpeRepository tentativaGolpeRepository;

    /**
     * Registra uma tentativa suspeita de golpe.
     *
     * @param codigo       código informado na tentativa
     * @param usuarioId    identificador do usuário
     * @param entregadorId identificador do entregador
     * @param observacao   observação sobre a tentativa
     */
    
    public void setTentativaGolpeRepository(TentativaGolpeRepository tentativaGolpeRepository) {
        this.tentativaGolpeRepository = tentativaGolpeRepository;
    }
    public void registrarTentativa(String codigo, Long usuarioId, Long entregadorId, String observacao) {
        LOGGER.info("Registrando tentativa de golpe: usuarioId={}, entregadorId={}, codigo={}, observacao={}",
                usuarioId, entregadorId, codigo, observacao);

        TentativaGolpe tentativa = new TentativaGolpe();
        tentativa.setCodigoTentado(codigo);
        tentativa.setDataHora(LocalDateTime.now());
        tentativa.setUsuarioId(usuarioId);
        tentativa.setEntregadorId(entregadorId);
        tentativa.setObservacao(observacao);

        tentativaGolpeRepository.save(tentativa);
    }
}

