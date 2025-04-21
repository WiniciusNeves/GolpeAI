package dev.inove.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

import dev.inove.backend.model.TentativaGolpe;
import dev.inove.backend.repository.TentativaGolpeRepository;

@Service
public class TentativaGolpeService {

    @Autowired
    private TentativaGolpeRepository tentativaGolpeRepository;

    public void registrarTentativa(String codigo, Long usuarioId, Long entregadorId, String observacao) {
        TentativaGolpe tentativa = new TentativaGolpe();
        tentativa.setCodigoTentado(codigo);
        tentativa.setDataHora(LocalDateTime.now());
        tentativa.setUsuarioId(usuarioId);
        tentativa.setEntregadorId(entregadorId);
        tentativa.setObservacao(observacao);

        tentativaGolpeRepository.save(tentativa);
    }
}

