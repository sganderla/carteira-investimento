package br.com.esaplicacoes.backend.entity.brapi;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author Eduardo Sganderla
 *
 * @version 1.0.0, 23/11/2023
 * @since 1.0.0
 */
public class Result {

    @Getter @Setter
    private String symbol;
    @Getter @Setter
    private String longName;
    @Getter @Setter
    private String regularMarketTime;
    @Getter @Setter
    private BigDecimal regularMarketChangePercent;
    @Getter @Setter
    private BigDecimal regularMarketPrice;;
    @Getter @Setter
    private BigDecimal regularMarketPreviousClose;
    @Getter @Setter
    private String logourl;

}
