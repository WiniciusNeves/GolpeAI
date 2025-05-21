package dev.inove.backend.service;

import dev.inove.backend.model.Entregador;
import dev.inove.backend.model.Usuario;
import dev.inove.backend.model.ValidacaoEntrega;
import dev.inove.backend.repository.ValidacaoEntregaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ValidacaoEntregaServiceTest {

    private ValidacaoEntregaService service;
    private ValidacaoEntregaRepository repository;

    @BeforeEach
    void setup() {
        repository = mock(ValidacaoEntregaRepository.class);
        service = new ValidacaoEntregaService();
        service.setValidacaoEntregaRepository(repository); // Use um método setter
    };

    @Test
    void deveGerarValidacaoComUsuarioEEntregador() {
        Usuario usuario = new Usuario(1L, "João", "joao@email.com", "senha", "Rua X", "1199999");
        Entregador entregador = new Entregador(1L, "Carlos", "carlos@email.com", "ABC1234", "urlFoto", "iFood");

        ValidacaoEntrega esperado = new ValidacaoEntrega();
        esperado.setId(1L);
        esperado.setCodigoVerificacao("ABC123");
        esperado.setUsuario(usuario);
        esperado.setEntregador(entregador);
        esperado.setStatus("PENDENTE");

        when(repository.save(any())).thenReturn(esperado);

        ValidacaoEntrega resultado = service.gerarValidacao(usuario, entregador);

        assertNotNull(resultado);
        assertEquals("ABC123", resultado.getCodigoVerificacao());
        assertEquals("PENDENTE", resultado.getStatus());
        assertEquals("João", resultado.getUsuario().getNome());
    }
}
