package br.com.esaplicacoes.backend.service;

import br.com.esaplicacoes.backend.component.Mensagem;
import br.com.esaplicacoes.backend.entity.TipoDespesa;
import br.com.esaplicacoes.backend.entity.TipoInvestimento;
import br.com.esaplicacoes.backend.entity.TipoRendimento;
import br.com.esaplicacoes.backend.repository.TipoDespesaRepository;
import br.com.esaplicacoes.backend.repository.TipoInvestimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author Eduardo Sganderla
 *
 * @version 1.0.0, 29/07/2023
 * @since 1.0.0
 */
@Service
public class TipoDespesaService {

    @Autowired
    private Mensagem mensagem;
    @Autowired
    private TipoDespesaRepository tipoDespesaRepository;

    /**
     *
     * @param id
     * @return
     */
    public TipoDespesa findById(final Long id){

        return this.tipoDespesaRepository.findById(id)
                .map(tipoDespesa -> {
                    return tipoDespesa;
                })
                .orElseThrow(() -> {
                    throw new IllegalStateException(
                            this.mensagem.texto("error.id.nao.encontrado", id)
                    );
                });
    }

    /**
     *
     * @param tipoDespesa
     * @return
     */
    public String cadastrar(final TipoDespesa tipoDespesa){

        this.tipoDespesaRepository.save(tipoDespesa);

        return this.mensagem.texto("sucesso.cadastro","Tipo de Despesa");
    }

    /**
     *
     * @param id
     * @param tipoDespesa
     * @return
     */
    public String editar(final Long id, final TipoDespesa tipoDespesa){

        final TipoDespesa tipoDespesaBanco = this.findById(id);

        Assert.isTrue(
                tipoDespesa.getId().equals(tipoDespesaBanco.getId()),
                this.mensagem.texto("error.id.diferentes"));

        this.tipoDespesaRepository.save(tipoDespesa);

        return this.mensagem.texto("sucesso.editar", "Tipo de Despesa");
    }

    /**
     *
     * @param id
     * @return
     */
    public String excluir(final Long id){

        final TipoDespesa tipoInvestimento = this.findById(id);

        this.tipoDespesaRepository.delete(tipoInvestimento);

        return this.mensagem.texto("sucesso.excluir", "Tipo de Despesa");
    }
}
