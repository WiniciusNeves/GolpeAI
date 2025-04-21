package dev.inove.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "tentativa_golpe")
@Data
@NoArgsConstructor
public class TentativaGolpe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigoTentado;

    private LocalDateTime dataHora;

    private Long usuarioId;

    private Long entregadorId;

    private String observacao; // opcional, pra descrever o motivo
}

