package br.com.esaplicacoes.backend.entity.view;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author Eduardo Sganderla
 *
 * @version 1.0.0, 05/12/2023
 * @since 1.0.0
 */
@Entity
@Table(name = "compilacao_dados_posicao", schema = "public")
public class CompilacaoDadosPosicao {

    @Id
    @Getter @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter @Setter
    @Column(name = "ano")
    private Integer ano;
    @Getter @Setter
    @Column(name = "mes")
    private Integer mes;
    @Getter @Setter
    @Column(name = "posicao")
    private BigDecimal posicao;
    @Getter @Setter
    @Column(name = "investimento")
    private BigDecimal investimento;
    @Getter @Setter
    @Column(name = "resgate")
    private BigDecimal resgate;
    @Getter @Setter
    @Column(name = "diferenca")
    private BigDecimal diferenca;
    @Getter @Setter
    @Column(name = "posicao_anterior")
    private BigDecimal posicaoAnterior;
    @Getter @Setter
    @Column(name = "tipo_investimento_id")
    private Long tipoInvestimentoId;
    @Getter @Setter
    @Column(name = "tipo_investimento")
    private String tipoInvestimento;
}
