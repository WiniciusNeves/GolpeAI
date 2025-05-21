package dev.inove.backend.service;

import dev.inove.backend.model.Entregador;
import dev.inove.backend.repository.EntregadorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

@Service
public class EntregadorService {

    private static final Logger LOGGER = Logger.getLogger(EntregadorService.class.getName());

    private final EntregadorRepository repository;

    // Injeção via construtor 
    public EntregadorService(EntregadorRepository repository) {
        this.repository = repository;
    }

    /**
     * Lista todos os entregadores cadastrados.
     *
     * @return lista de entregadores
     */
    public List<Entregador> listar() {
        LOGGER.info("[EntregadorService] Listando todos os entregadores.");
        return repository.findAll();
    }

    /**
     * Busca um entregador pelo ID.
     *
     * @param id identificador do entregador
     * @return entregador encontrado
     * @throws NoSuchElementException se o entregador não for encontrado
     */
    public Entregador buscar(Long id) {
        LOGGER.info("[EntregadorService] Buscando entregador com ID: " + id);
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Entregador não encontrado com ID: " + id));
    }

    /**
     * Cria um novo entregador.
     *
     * @param entregador dados do entregador
     * @return entregador criado
     */
    public Entregador criar(Entregador entregador) {
        LOGGER.info("[EntregadorService] Criando novo entregador.");
        return repository.save(entregador);
    }

    /**
     * Atualiza os dados de um entregador.
     *
     * @param id identificador do entregador
     * @param entregador dados atualizados do entregador
     * @return entregador atualizado
     */
    public Entregador atualizar(Long id, Entregador entregador) {
        LOGGER.info("[EntregadorService] Atualizando entregador com ID: " + id);
        entregador.setId(id);
        return repository.save(entregador);
    }

    /**
     * Remove um entregador pelo ID.
     *
     * @param id identificador do entregador
     */
    public void deletar(Long id) {
        LOGGER.info("[EntregadorService] Removendo entregador com ID: " + id);
        repository.deleteById(id);
    }

    /**
     * Salva ou atualiza um entregador.
     *
     * @param entregador dados do entregador
     * @return entregador salvo
     */
    public Entregador salvar(Entregador entregador) {
        LOGGER.info("[EntregadorService] Salvando dados do entregador.");
        return repository.save(entregador);
    }
}
