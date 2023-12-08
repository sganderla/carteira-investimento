package br.com.esaplicacoes.backend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Eduardo Sganderla
 *
 * @version 1.0.0, 08/08/2023
 * @since 1.0.0
 */
@Entity
@Table(name = "despesa", schema = "public")
public class Despesa extends AbstractEntity{

    @Getter @Setter
    @Column(name = "data", nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate data;
    @Getter @Setter
    @Column(name = "valor", nullable = false, precision = 9, scale = 3)
    private BigDecimal valor;

    @ManyToOne
    @Getter @Setter
    @JoinColumn(name = "tipo_despesa_id", nullable = false)
    private TipoDespesa tipoDespesa;

    @ManyToOne
    @Getter @Setter
    @JoinColumn(name = "patrimonio_id", nullable = false)
    private Patrimonio patrimonio;

}
