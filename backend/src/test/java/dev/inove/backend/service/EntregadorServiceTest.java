package dev.inove.backend.service;

import dev.inove.backend.model.Entregador;
import dev.inove.backend.repository.EntregadorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EntregadorServiceTest {

    private EntregadorRepository repository;
    private EntregadorService service;

    @BeforeEach
    void setup() {
        repository = mock(EntregadorRepository.class);
        service = new EntregadorService(repository);
    }

    @Test
    void deveListarTodosEntregadores() {
        List<Entregador> listaMock = List.of(new Entregador(), new Entregador());
        when(repository.findAll()).thenReturn(listaMock);

        List<Entregador> resultado = service.listar();

        assertEquals(2, resultado.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void deveBuscarEntregadorPorId() {
        Entregador entregador = new Entregador();
        entregador.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(entregador));

        Entregador resultado = service.buscar(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(repository).findById(1L);
    }

    @Test
    void deveLancarExcecaoQuandoEntregadorNaoEncontrado() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> service.buscar(99L));
    }

    @Test
    void deveCriarEntregador() {
        Entregador novo = new Entregador();
        when(repository.save(novo)).thenReturn(novo);

        Entregador resultado = service.criar(novo);

        assertEquals(novo, resultado);
        verify(repository).save(novo);
    }

    @Test
    void deveAtualizarEntregador() {
        Entregador atualizado = new Entregador();
        when(repository.save(any(Entregador.class))).thenReturn(atualizado);

        Entregador resultado = service.atualizar(1L, atualizado);

        assertEquals(atualizado, resultado);
        assertEquals(1L, atualizado.getId()); // deve setar o ID antes de salvar
        verify(repository).save(atualizado);
    }

    @Test
    void deveDeletarEntregadorPorId() {
        service.deletar(10L);

        verify(repository).deleteById(10L);
    }

    @Test
    void deveSalvarEntregador() {
        Entregador entregador = new Entregador();
        when(repository.save(entregador)).thenReturn(entregador);

        Entregador resultado = service.salvar(entregador);

        assertEquals(entregador, resultado);
        verify(repository).save(entregador);
    }
}
