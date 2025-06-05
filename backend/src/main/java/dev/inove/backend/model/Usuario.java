package dev.inove.backend.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Representa um usuário do sistema, que pode solicitar validações de entrega.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nome completo do usuário */
    private String nome;

    /** E-mail do usuário (utilizado para login) */
    private String email;

   
    /** Endereço do usuário para entrega */
    private String endereco;

    /** Número de telefone para contato */
    private String telefone;
}
