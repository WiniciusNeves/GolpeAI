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
    @Column(nullable = false)
    private String nome;

    /** E-mail do entregador */
    @Column(nullable = false, unique = true)
    private String email;

    /** Placa do veículo usado na entrega */
    @Column(nullable = false)
    private String placa;

    /** URL da foto do entregador (pode ser um base64 ou URL do Firebase/Storage) */
    @Column(length = 999999)
    private String fotoUrl;

    /** Empresa responsável pelo entregador */
    @Column(nullable = false)
    private String empresa;

    // Futuro: poderá ter relação com uma entidade Usuario ou Empresa
}

