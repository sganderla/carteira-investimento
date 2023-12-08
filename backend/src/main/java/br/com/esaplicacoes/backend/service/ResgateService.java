package br.com.esaplicacoes.backend.service;

import br.com.esaplicacoes.backend.component.CalcularPrecoMedioVenda;
import br.com.esaplicacoes.backend.component.Mensagem;
import br.com.esaplicacoes.backend.entity.Patrimonio;
import br.com.esaplicacoes.backend.entity.Resgate;
import br.com.esaplicacoes.backend.repository.ResgateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author Eduardo Sganderla
 *
 * @version 1.0.0, 08/08/2023
 * @since 1.0.0
 */
@Service
public class ResgateService {

    @Autowired
    private Mensagem mensagem;
    @Autowired
    private ResgateRepository resgateRepository;
    @Autowired
    private CalcularPrecoMedioVenda calcularPrecoMedioVenda;
    @Autowired
    private PatrimonioService patrimonioService;

    /**
     *
     * @param id
     * @return
     */
    public Resgate findById(final Long id){

        return this.resgateRepository.findById(id)
                .map(resgate -> {
                    return resgate;
                })
                .orElseThrow(() -> {
                    throw new IllegalStateException(
                            this.mensagem.texto("error.id.nao.encontrado", id)
                    );
                });
    }

    /**
     *
     * @param resgate
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public String cadastrar(final Resgate resgate){

        final Patrimonio patrimonio = this.patrimonioService
                .findById(resgate.getPatrimonio().getId());

        this.resgateRepository.save(resgate);

        /**
         * Calcular o Preço medio da Venda
         */
        this.calcularPrecoMedioVenda.calcular(resgate.getPatrimonio());

        return this.mensagem.texto("sucesso.cadastro","Resgate");
    }

    /**
     *
     * @param id
     * @param resgate
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public String editar(final Long id, final Resgate resgate){

        final Patrimonio patrimonio = this.patrimonioService
                .findById(resgate.getPatrimonio().getId());

        final Resgate resgateBanco = this.findById(id);

        Assert.isTrue(
                resgate.getId().equals(resgateBanco.getId()),
                this.mensagem.texto("error.id.diferentes"));

        this.resgateRepository.save(resgate);

        /**
         * Calcular o Preço medio da Venda
         */
        this.calcularPrecoMedioVenda.calcular(resgate.getPatrimonio());

        return this.mensagem.texto("sucesso.editar", "Resgate");
    }

    /**
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public String excluir(final Long id){

        final Resgate resgate = this.findById(id);

        this.resgateRepository.delete(resgate);

        /**
         * Calcular o Preço medio da Venda
         */
        this.calcularPrecoMedioVenda.calcular(resgate.getPatrimonio());

        return this.mensagem.texto("sucesso.excluir", "Resgate");
    }
}
