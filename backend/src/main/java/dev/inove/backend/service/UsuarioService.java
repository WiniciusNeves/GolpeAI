package dev.inove.backend.service;

import dev.inove.backend.model.Usuario;
import dev.inove.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Serviço responsável por operações com usuários.
 */
@Service
@Slf4j
@RequiredArgsConstructor // Injeta o repositório via construtor
public class UsuarioService {

    private final UsuarioRepository repository;

    /**
     * Lista todos os usuários.
     *
     * @return lista de usuários.
     */
    public List<Usuario> listar() {
        log.info("[UsuarioService] Listando todos os usuários.");
        return repository.findAll();
    }

    /**
     * Busca um usuário pelo ID.
     *
     * @param id identificador do usuário.
     * @return usuário encontrado.
     * @throws NoSuchElementException se não encontrado.
     */
    public Usuario buscar(Long id) {
        log.info("[UsuarioService] Buscando usuário com ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado com ID: " + id));
    }

    /**
     * Cria um novo usuário.
     *
     * @param usuario novo usuário.
     * @return usuário criado.
     */
    public Usuario criar(Usuario usuario) {
        log.info("[UsuarioService] Criando usuário com email: {}", usuario.getEmail());
        return repository.save(usuario);
    }

    /**
     * Atualiza os dados de um usuário pelo ID.
     *
     * @param id identificador do usuário.
     * @param usuario dados atualizados.
     * @return usuário atualizado.
     */
    public Usuario atualizar(Long id, Usuario usuario) {
        log.info("[UsuarioService] Atualizando usuário com ID: {}", id);
        usuario.setId(id);
        return repository.save(usuario);
    }

    /**
     * Remove um usuário pelo ID.
     *
     * @param id identificador do usuário.
     */
    public void deletar(Long id) {
    log.info("[UsuarioService] Tentando remover usuário com ID: {}", id);
    buscar(id); // Lança exceção se não existir

    boolean possuiDependencias = // lógica para checar se há registros em validacao_entrega com esse usuário
        !repository.existsById(id); // Exemplo, mas depende da estrutura do seu sistema

    if (possuiDependencias) {
        throw new IllegalStateException("Usuário não pode ser removido, pois está vinculado a validações de entrega.");
    }

    repository.deleteById(id);
}

}
