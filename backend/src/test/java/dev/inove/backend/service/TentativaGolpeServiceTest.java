package dev.inove.backend.service;

import dev.inove.backend.model.TentativaGolpe;
import dev.inove.backend.repository.TentativaGolpeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TentativaGolpeServiceTest {

    private TentativaGolpeRepository repository;
    private TentativaGolpeService service;

    @BeforeEach
    void setup() {
        repository = mock(TentativaGolpeRepository.class);
        service = new TentativaGolpeService();
        service.setTentativaGolpeRepository(repository);
    }
    @Test
    void deveRegistrarTentativaDeGolpe() {
        String codigo = "ABC123";
        Long usuarioId = 1L;
        Long entregadorId = 2L;
        String observacao = "Código não confere com o informado.";

        service.registrarTentativa(codigo, usuarioId, entregadorId, observacao);

        verify(repository, times(1)).save(any(TentativaGolpe.class));
    }
}
