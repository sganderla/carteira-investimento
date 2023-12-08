package br.com.esaplicacoes.backend.service;

import br.com.esaplicacoes.backend.component.CalcularPrecoMedio;
import br.com.esaplicacoes.backend.component.CalcularPrecoMedioVenda;
import br.com.esaplicacoes.backend.component.Mensagem;
import br.com.esaplicacoes.backend.entity.Investimento;
import br.com.esaplicacoes.backend.repository.InvestimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author Eduardo Sganderla
 *
 * @version 1.0.0, 08/08/2023
 * @since 1.0.0
 */
@Service
public class InvestimentoService {

    @Autowired
    private Mensagem mensagem;
    @Autowired
    private CalcularPrecoMedio calcularPrecoMedio;
    @Autowired
    private InvestimentoRepository investimentoRepository;
    @Autowired
    private CalcularPrecoMedioVenda calcularPrecoMedioVenda;
    @Autowired
    private PatrimonioService patrimonioService;

    /**
     *
     * @param id
     * @return
     */
    public Investimento findById(final Long id){

        return this.investimentoRepository.findById(id)
                .map(investimento -> {
                    return investimento;
                })
                .orElseThrow(() -> {
                    throw new IllegalStateException(
                            this.mensagem.texto("error.id.nao.encontrado", id)
                    );
                });
    }

    /**
     *
     * @param investimento
     * @return
     */
    public String cadastrar(final Investimento investimento){

        this.investimentoRepository.save(investimento);

        /**
         * Calcular preço médio
         */
        this.calcularPrecoMedio.calcular(investimento.getPatrimonio());

        /**
         * Recalcula os valores finais do investimento
         */
        this.calcularPrecoMedioVenda.calcular(
                this.patrimonioService.findById(
                        investimento.getPatrimonio().getId()));

        return this.mensagem.texto("sucesso.cadastro","Investimento");
    }

    /**
     *
     * @param id
     * @param investimento
     * @return
     */
    public String editar(final Long id, final Investimento investimento){

        final Investimento investimentoBanco = this.findById(id);

        Assert.isTrue(
                investimento.getId().equals(investimentoBanco.getId()),
                this.mensagem.texto("error.id.diferentes"));

        this.investimentoRepository.save(investimento);

        /**
         * Calcular preço médio
         */
        this.calcularPrecoMedio.calcular(investimento.getPatrimonio());

        return this.mensagem.texto("sucesso.editar", "Investimento");
    }

    /**
     *
     * @param id
     * @return
     */
    public String excluir(final Long id){

        final Investimento investimento = this.findById(id);

        this.investimentoRepository.delete(investimento);

        /**
         * Calcular preço médio
         */
        this.calcularPrecoMedio.calcular(investimento.getPatrimonio());

        return this.mensagem.texto("sucesso.excluir", "Investimento");
    }
}
