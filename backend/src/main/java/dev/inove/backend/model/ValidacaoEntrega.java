package dev.inove.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "validacao_entrega")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidacaoEntrega {

    // ID único gerado automaticamente para cada registro de validação
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Código de verificação da entrega, pode ser utilizado para autenticar a
    // validação
    private String codigoVerificacao;

    // Data e hora da validação. Usar LocalDateTime permite manipular a data e hora
    // corretamente.
    private LocalDateTime dataHora;

    // Relacionamento muitos-para-um com a tabela de usuários (quem fez a validação)
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = true) // <- deixar nullable
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "entregador_id", nullable = true) // <- deixar nullable
    private Entregador entregador;

    // Status da validação da entrega, que pode ser algo como "PENDENTE",
    // "CONCLUÍDO" ou "CANCELADO"
    private String status;
}
