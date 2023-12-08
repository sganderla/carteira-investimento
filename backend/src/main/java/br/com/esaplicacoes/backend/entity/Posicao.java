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
 * @version 1.0.0, 29/07/2023
 * @since 1.0.0
 */
@Entity
@Table(name = "posicao", schema = "public")
public class Posicao extends AbstractEntity{

    @Getter @Setter
    @Column(name = "valor", nullable = false, precision = 9, scale = 3)
    private BigDecimal valor;
    @Getter @Setter
    @Column(name = "data", nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate data;

    @ManyToOne
    @Getter @Setter
    @JoinColumn(name = "tipo_investimento_id", nullable = false)
    private TipoInvestimento tipoInvestimento;

}
