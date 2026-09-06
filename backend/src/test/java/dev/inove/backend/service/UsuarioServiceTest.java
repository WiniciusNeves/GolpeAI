package dev.inove.backend.service;

import dev.inove.backend.model.Usuario;
import dev.inove.backend.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioServiceTest {

    private UsuarioService service;
    private UsuarioRepository repository;

    @BeforeEach
    void setup() {
        repository = mock(UsuarioRepository.class);
        service = new UsuarioService(repository); // agora com injeção via construtor
    }

    @Test
    void deveListarUsuarios() {
        List<Usuario> usuarios = List.of(
                new Usuario(1L, "João", "joao@email.com", "Rua X", "1199999"),
                new Usuario(2L, "Maria", "maria@email.com", "Rua Y", "1188888")
        );
        when(repository.findAll()).thenReturn(usuarios);

        List<Usuario> resultado = service.listar();

        assertEquals(2, resultado.size());
        assertEquals("João", resultado.get(0).getNome());
    }

    @Test
    void deveBuscarUsuarioPorId() {
        Usuario u = new Usuario(1L, "João", "joao@email.com", "Rua X", "1199999");
        when(repository.findById(1L)).thenReturn(Optional.of(u));

        Usuario resultado = service.buscar(1L);

        assertNotNull(resultado);
        assertEquals("João", resultado.getNome());
    }

    @Test
    void deveCriarUsuario() {
        Usuario u = new Usuario(null, "João", "joao@email.com", "Rua X", "1199999");
        Usuario salvo = new Usuario(1L, "João", "joao@email.com", "Rua X", "1199999");

        when(repository.save(u)).thenReturn(salvo);

        Usuario resultado = service.criar(u);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void deveAtualizarUsuario() {
        Usuario u = new Usuario(null, "João Atualizado", "joao@email.com", "Rua Nova", "1199999");
        Usuario atualizado = new Usuario(1L, "João Atualizado", "joao@email.com", "Rua Nova", "1199999");

        when(repository.save(any())).thenReturn(atualizado);

        Usuario resultado = service.atualizar(1L, u);

        assertEquals("João Atualizado", resultado.getNome());
        assertEquals(1L, resultado.getId());
    }

    @Test
    void deveDeletarUsuario() {
        Long id = 1L;

        doNothing().when(repository).deleteById(id);

        service.deletar(id);

        verify(repository, times(1)).deleteById(id);
    }

}
