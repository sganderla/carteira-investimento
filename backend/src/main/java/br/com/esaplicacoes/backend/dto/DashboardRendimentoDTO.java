package br.com.esaplicacoes.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author Eduardo Sganderla
 *
 * @version 1.0.0, 29/11/2023
 * @since 1.0.0
 */
@AllArgsConstructor
public class DashboardRendimentoDTO {

    @Getter @Setter
    private Integer mes;
    @Getter @Setter
    private Integer ano;
    @Getter @Setter
    private BigDecimal valor;

}
