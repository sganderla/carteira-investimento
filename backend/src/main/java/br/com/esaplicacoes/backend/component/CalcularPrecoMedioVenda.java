package br.com.esaplicacoes.backend.component;

import br.com.esaplicacoes.backend.entity.*;
import br.com.esaplicacoes.backend.repository.DespesaRepository;
import br.com.esaplicacoes.backend.repository.PatrimonioRepository;
import br.com.esaplicacoes.backend.repository.RendimentoRepository;
import br.com.esaplicacoes.backend.repository.ResgateRepository;
import br.com.esaplicacoes.backend.service.InvestimentoService;
import br.com.esaplicacoes.backend.service.PatrimonioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

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
public class CalcularPrecoMedioVenda {

    @Autowired
    private ResgateRepository resgateRepository;
    @Autowired
    private PatrimonioRepository patrimonioRepository;
    @Autowired
    private RendimentoRepository rendimentoRepository;
    @Autowired
    private DespesaRepository despesaRepository;
    @Autowired
    private PatrimonioService patrimonioService;
    @Autowired
    private Mensagem mensagem;

    /**
     *
     * @param patrimonio
     */
    public void calcular(Patrimonio patrimonio){

        final Set<Resgate> resgates = this.resgateRepository
                .findByPatrimonioAndAtivoTrue(patrimonio);

        patrimonio = this.patrimonioService.findById(patrimonio.getId());

        /**
         * Quantidade de ativos manipulados
         */
        final Integer quantidadeVenda = resgates
                .stream()
                .map(Resgate::getQuantidade)
                .filter(Objects::nonNull)
                .reduce((a,b) -> a + b)
                .orElse(0);

        /**
         * Não pode vender uma quantidade maior que a compra.
         */
        Assert.isTrue( quantidadeVenda <= patrimonio.getQtdePatrimonio(),
                this.mensagem.texto("error.resgate.maior.patrimonio"));

        /**
         * Valor total dos ativos manipulados (valor * quantidade)
         */
        BigDecimal valorVenda = BigDecimal.ZERO;
        for (Resgate resgate : resgates){
            valorVenda = valorVenda.add(
                resgate.getValor()
                    .multiply(BigDecimal.valueOf(resgate.getQuantidade())));
        }

        /**
         * Preço medio das vendas
         */
        BigDecimal valorMedioVenda = BigDecimal.ZERO;
        if(valorVenda.compareTo(BigDecimal.ZERO) > 0) {
            valorMedioVenda = valorVenda.divide(
                    BigDecimal.valueOf(quantidadeVenda), 3, RoundingMode.HALF_UP);
        }

        /**
         * Calcular os rendimentos
         */
        final Set<Rendimento> rendimentos = this.rendimentoRepository
                .findByPatrimonioAndAtivoTrueAndPagoTrue(patrimonio);

        final BigDecimal rendimentoTotal = rendimentos.stream()
                .map(Rendimento::getValor)
                .filter(Objects::nonNull)
                .reduce((a,b) -> a.add(b))
                .orElse(BigDecimal.ZERO);

        /**
         * Calcular as despesas
         */

        final Set<Despesa> despesas  = this.despesaRepository
                .findByPatrimonioAndAtivoTrueOrderByDataDesc(patrimonio);

        final BigDecimal despesaTotal = despesas.stream()
                .map(Despesa::getValor)
                .filter(Objects::nonNull)
                .reduce((a,b) -> a.add(b))
                .orElse(BigDecimal.ZERO);


        /**
         * Diferença entre os valores médios da compra e venda
         */
        final BigDecimal diferencaValores = valorMedioVenda.subtract(patrimonio.getVlMedio());

        final BigDecimal rendimento = diferencaValores
                .multiply(BigDecimal.valueOf(quantidadeVenda))
                .add(rendimentoTotal)
                .subtract(despesaTotal);

        final BigDecimal perRendimento = rendimento
                .multiply(BigDecimal.valueOf(100))
                .divide(patrimonio.getVlMedio()
                        .multiply(BigDecimal.valueOf(patrimonio.getQtdePatrimonio())),
                2, RoundingMode.HALF_UP);

        patrimonio.setQtdePatrimonioVenda(quantidadeVenda);
        patrimonio.setVlMedioVenda(valorMedioVenda);
        patrimonio.setVlRendimento(rendimento);
        patrimonio.setPerRendimento(perRendimento);
        patrimonio.setFinalizado(patrimonio.getQtdePatrimonio() == quantidadeVenda);

        this.patrimonioRepository.save(patrimonio);
    }
}
