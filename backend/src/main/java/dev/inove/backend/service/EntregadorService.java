package dev.inove.backend.service;

import dev.inove.backend.model.Entregador;
import dev.inove.backend.repository.EntregadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntregadorService {

    @Autowired
    private EntregadorRepository repository;

    public List<Entregador> listar() {
        return repository.findAll();
    }

    public Entregador buscar(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public Entregador criar(Entregador entregador) {
        return repository.save(entregador);
    }

    public Entregador atualizar(Long id, Entregador entregador) {
        entregador.setId(id);
        return repository.save(entregador);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public Entregador salvar(Entregador entregador) {
        return null;
    }
}
