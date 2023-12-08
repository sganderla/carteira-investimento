package br.com.esaplicacoes.backend.service;

import br.com.esaplicacoes.backend.component.CalcularDespesa;
import br.com.esaplicacoes.backend.component.CalcularPrecoMedioVenda;
import br.com.esaplicacoes.backend.component.Mensagem;
import br.com.esaplicacoes.backend.entity.Despesa;
import br.com.esaplicacoes.backend.repository.DespesaRepository;
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
public class DespesaService {

    @Autowired
    private Mensagem mensagem;
    @Autowired
    private DespesaRepository despesaRepository;
    @Autowired
    private CalcularDespesa calcularDespesa;
    @Autowired
    private CalcularPrecoMedioVenda calcularPrecoMedioVenda;
    @Autowired
    private PatrimonioService patrimonioService;

    /**
     *
     * @param id
     * @return
     */
    public Despesa findById(final Long id){

        return this.despesaRepository.findById(id)
                .map(despesa -> {
                    return despesa;
                })
                .orElseThrow(() -> {
                    throw new IllegalStateException(
                            this.mensagem.texto("error.id.nao.encontrado", id)
                    );
                });
    }

    /**
     *
     * @param despesa
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public String cadastrar(final Despesa despesa){

        this.despesaRepository.save(despesa);

        if(despesa.isAtivo()) {
            this.calcularDespesa.calcularCadastrar(despesa.getPatrimonio(), despesa);
        }

        /**
         * Recalcula os valores finais do investimento
         */
        this.calcularPrecoMedioVenda.calcular(
                this.patrimonioService.findById(
                        despesa.getPatrimonio().getId()));

        return this.mensagem.texto("sucesso.cadastro","Despesa");
    }

    /**
     *
     * @param id
     * @param despesa
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public String editar(final Long id, final Despesa despesa){

        final Despesa despesaBanco = this.findById(id);

        Assert.isTrue(
                despesa.getId().equals(despesaBanco.getId()),
                this.mensagem.texto("error.id.diferentes"));

        this.despesaRepository.save(despesa);

        if(despesaBanco.isAtivo() && despesa.isAtivo()){
            this.calcularDespesa.calcularEditar(despesa.getPatrimonio(), despesaBanco, despesa);
        }
        else if(despesaBanco.isAtivo() && !despesa.isAtivo()){
            this.calcularDespesa.calcularExcluir(despesa.getPatrimonio(), despesaBanco);
        }
        else if(!despesaBanco.isAtivo() && despesa.isAtivo()){
            this.calcularDespesa.calcularCadastrar(despesa.getPatrimonio(), despesa);
        }

        return this.mensagem.texto("sucesso.editar", "Despesa");
    }

    /**
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public String excluir(final Long id){

        final Despesa despesa = this.findById(id);

        this.despesaRepository.delete(despesa);

        if(despesa.isAtivo()) {
            this.calcularDespesa.calcularExcluir(despesa.getPatrimonio(), despesa);
        }

        return this.mensagem.texto("sucesso.excluir", "Despesa");
    }
}
