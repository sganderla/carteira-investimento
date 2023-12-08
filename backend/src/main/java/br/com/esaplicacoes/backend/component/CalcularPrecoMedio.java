package br.com.esaplicacoes.backend.component;

import br.com.esaplicacoes.backend.entity.Investimento;
import br.com.esaplicacoes.backend.entity.Patrimonio;
import br.com.esaplicacoes.backend.repository.InvestimentoRepository;
import br.com.esaplicacoes.backend.repository.PatrimonioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.Set;

/**
 * @author Eduardo Sganderla
 *
 * @version 1.0.0, 08/08/2023
 * @since 1.0.0
 */
@Component
public class CalcularPrecoMedio {

    @Autowired
    private InvestimentoRepository investimentoRepository;
    @Autowired
    private PatrimonioRepository patrimonioRepository;

    /**
     *
     * @param patrimonio
     */
    public void calcular(final Patrimonio patrimonio){

        final Set<Investimento> investimentos = this.investimentoRepository
                .findByPatrimonioAndAtivoTrue(patrimonio);

        /**
         * Quantidade de ativos manipulados
         */
        final Integer quantidade = investimentos
                .stream()
                .map(Investimento::getQuantidade)
                .filter(Objects::nonNull)
                .reduce((a,b) -> a + b)
                .orElse(0);

        /**
         * Valor total dos ativos manipulados (valor * quantidade)
          */
        BigDecimal valor = BigDecimal.ZERO;
        for (Investimento investimento : investimentos){
            valor = valor.add(
                investimento.getValor()
                    .multiply(BigDecimal.valueOf(investimento.getQuantidade())));
        }

        /**
         * Preço medio das compras
         */
        BigDecimal valorMedio = BigDecimal.ZERO;
        if(valor.compareTo(BigDecimal.ZERO) > 0) {
            valorMedio = valor.divide(
                    BigDecimal.valueOf(quantidade), 3, RoundingMode.HALF_UP);
        }

        patrimonio.setVlMedio(valorMedio);
        patrimonio.setQtdePatrimonio(quantidade);

        this.patrimonioRepository.save(patrimonio);
    }
}
