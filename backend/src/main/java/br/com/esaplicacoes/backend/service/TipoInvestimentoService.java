package br.com.esaplicacoes.backend.service;

import br.com.esaplicacoes.backend.component.Mensagem;
import br.com.esaplicacoes.backend.entity.Posicao;
import br.com.esaplicacoes.backend.entity.TipoInvestimento;
import br.com.esaplicacoes.backend.repository.PosicaoRepository;
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
public class TipoInvestimentoService {

    @Autowired
    private Mensagem mensagem;
    @Autowired
    private TipoInvestimentoRepository tipoInvestimentoRepository;

    /**
     *
     * @param id
     * @return
     */
    public TipoInvestimento findById(final Long id){

        return this.tipoInvestimentoRepository.findById(id)
                .map(tipoInvestimento -> {
                    return tipoInvestimento;
                })
                .orElseThrow(() -> {
                    throw new IllegalStateException(
                            this.mensagem.texto("error.id.nao.encontrado", id)
                    );
                });
    }

    /**
     *
     * @param tipoInvestimento
     * @return
     */
    public String cadastrar(final TipoInvestimento tipoInvestimento){

        this.tipoInvestimentoRepository.save(tipoInvestimento);

        return this.mensagem.texto("sucesso.cadastro","Tipo de Investimento");
    }

    /**
     *
     * @param id
     * @param tipoInvestimento
     * @return
     */
    public String editar(final Long id, final TipoInvestimento tipoInvestimento){

        final TipoInvestimento tipoInvestimentoBanco = this.findById(id);

        Assert.isTrue(
                tipoInvestimento.getId().equals(tipoInvestimentoBanco.getId()),
                this.mensagem.texto("error.id.diferentes"));

        this.tipoInvestimentoRepository.save(tipoInvestimento);

        return this.mensagem.texto("sucesso.editar", "Tipo de Investimento");
    }

    /**
     *
     * @param id
     * @return
     */
    public String excluir(final Long id){

        final TipoInvestimento tipoInvestimento = this.findById(id);

        this.tipoInvestimentoRepository.delete(tipoInvestimento);

        return this.mensagem.texto("sucesso.excluir", "Tipo de Investimento");
    }
}
