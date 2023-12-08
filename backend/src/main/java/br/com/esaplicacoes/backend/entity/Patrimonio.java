package br.com.esaplicacoes.backend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Eduardo Sganderla
 *
 * @version 1.0.0, 29/07/2023
 * @since 1.0.0
 */
@Entity
@Table(name = "patrimonio", schema = "public")
public class Patrimonio extends AbstractEntity{

    @Getter @Setter
    @Column(name = "nome", nullable = false, length = 50)
    private String nome;
    @Getter @Setter
    @Column(name = "rentabilidade", nullable = false, length = 50)
    private String rentabilidade;

    @Getter @Setter
    @Column(name = "finalizado", nullable = false)
    private Boolean finalizado;

    @Getter @Setter
    @Column(name = "dt_inicio", nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dtInicio;
    @Getter @Setter
    @Column(name = "dt_fim", nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dtFim;

    @Getter @Setter
    @Column(name = "qtde_patrimonio", nullable = false)
    private Integer qtdePatrimonio;
    @Getter @Setter
    @Column(name = "vl_medio", nullable = false, precision = 9, scale = 3)
    private BigDecimal vlMedio;

    @Getter @Setter
    @Column(name = "qtde_patrimonio_venda", nullable = false)
    private Integer qtdePatrimonioVenda;
    @Getter @Setter
    @Column(name = "vl_medio_venda", nullable = false, precision = 9, scale = 3)
    private BigDecimal vlMedioVenda;

    @Getter @Setter
    @Column(name = "vl_rendimento", nullable = false, precision = 9, scale = 3)
    private BigDecimal vlRendimento;
    @Getter @Setter
    @Column(name = "per_rendimento", nullable = false, precision = 6, scale = 3)
    private BigDecimal perRendimento;

    @ManyToOne
    @Getter @Setter
    @JoinColumn(name = "tipo_investimento_id", nullable = false)
    private TipoInvestimento tipoInvestimento;

    /**
     *
     */
    @PrePersist
    private void preCadastro(){
        super.setDtCadastro(LocalDateTime.now());
        super.setAtivo(true);

        this.setQtdePatrimonio(0);
        this.setVlMedio(BigDecimal.ZERO);

        this.setQtdePatrimonioVenda(0);
        this.setVlMedioVenda(BigDecimal.ZERO);

        this.setVlRendimento(BigDecimal.ZERO);
        this.setPerRendimento(BigDecimal.ZERO);
    }
}
