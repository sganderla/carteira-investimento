package br.com.esaplicacoes.backend.dto;

import br.com.esaplicacoes.backend.entity.TipoInvestimento;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Eduardo Sganderla
 *
 * @version 1.0.0, 04/08/2023
 * @since 1.0.0
 */
public class PosicaoDTO {

    @Getter @Setter
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate data;
    @Getter @Setter
    private BigDecimal valor;
    @Getter @Setter
    private BigDecimal ganho;
    @Getter @Setter
    private BigDecimal rendimento;
    @Getter @Setter
    private BigDecimal investimento;
    @Getter @Setter
    private BigDecimal resgate;

    @Getter @Setter
    private TipoInvestimento tipoInvestimento;
}
