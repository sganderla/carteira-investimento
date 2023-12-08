package br.com.esaplicacoes.backend.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author Eduardo Sganderla
 *
 * @version 1.0.0, 23/11/2023
 * @since 1.0.0
 */
public class CotacaoDTO {

    @Getter @Setter
    private String tipoInvestimento;

    @Getter @Setter
    private String codigo;
    @Getter @Setter
    private String nome;
    @Getter @Setter
    private String data;
    @Getter @Setter
    private BigDecimal percentual;
    @Getter @Setter
    private BigDecimal preco;
    @Getter @Setter
    private BigDecimal precoFechamento;
    @Getter @Setter
    private String logoURL;

    @Getter @Setter
    private Integer qtdePatrimonio;
    @Getter @Setter
    private BigDecimal vlMedio;
    @Getter @Setter
    private BigDecimal percentualVariacao;

}
