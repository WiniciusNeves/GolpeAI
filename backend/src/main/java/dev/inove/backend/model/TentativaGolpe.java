package dev.inove.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * Representa uma tentativa inválida de validação de entrega,
 * possivelmente indicando um golpe.
 */
@Entity
@Table(name = "tentativa_golpe")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TentativaGolpe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Código de verificação tentado, mas que falhou */
    private String codigoTentado;

    /** Data e hora da tentativa */
    private LocalDateTime dataHora;

    /** ID do usuário que sofreu a tentativa */
    private Long usuarioId;

    /** ID do entregador envolvido na tentativa */
    private Long entregadorId;

    /** Observação adicional sobre a tentativa */
    private String observacao;
}
