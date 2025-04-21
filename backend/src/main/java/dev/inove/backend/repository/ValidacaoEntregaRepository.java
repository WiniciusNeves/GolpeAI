package dev.inove.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import dev.inove.backend.model.ValidacaoEntrega;

public interface ValidacaoEntregaRepository extends JpaRepository<ValidacaoEntrega, Long> {
    Optional<ValidacaoEntrega> findByCodigoVerificacaoAndUsuarioIdAndEntregadorId(String codigo, Long usuarioId, Long entregadorId);
}
