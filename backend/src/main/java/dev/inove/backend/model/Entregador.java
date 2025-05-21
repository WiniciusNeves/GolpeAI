package dev.inove.backend.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Representa o entregador que realiza a entrega ao cliente.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Entregador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nome completo do entregador */
    private String nome;

    /** E-mail do entregador */
    private String email;

    /** Placa do veículo usado na entrega */
    private String placa;

    /** URL da foto do entregador */
    private String fotoUrl;

    /** Empresa responsável pelo entregador */
    private String empresa;
}
//      * Valida o código de entrega gerado entre usuário e entregador.