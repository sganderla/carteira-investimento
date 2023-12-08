package br.com.esaplicacoes.backend.service;

import br.com.esaplicacoes.backend.component.Mensagem;
import br.com.esaplicacoes.backend.entity.TipoDespesa;
import br.com.esaplicacoes.backend.entity.TipoRendimento;
import br.com.esaplicacoes.backend.repository.TipoRendimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author Eduardo Sganderla
 *
 * @version 1.0.0, 05/08/2023
 * @since 1.0.0
 */
@Service
public class TipoRendimentoService {

    @Autowired
    private Mensagem mensagem;
    @Autowired
    private TipoRendimentoRepository tipoRendimentoRepository;

    /**
     *
     * @param id
     * @return
     */
    public TipoRendimento findById(final Long id){

        return this.tipoRendimentoRepository.findById(id)
                .map(tipoRendimento -> {
                    return tipoRendimento;
                })
                .orElseThrow(() -> {
                    throw new IllegalStateException(
                            this.mensagem.texto("error.id.nao.encontrado", id)
                    );
                });
    }

    /**
     *
     * @param tipoRendimento
     * @return
     */
    public String cadastrar(final TipoRendimento tipoRendimento){

        this.tipoRendimentoRepository.save(tipoRendimento);

        return this.mensagem.texto("sucesso.cadastro","Tipo de Rendimento");
    }

    /**
     *
     * @param id
     * @param tipoRendimento
     * @return
     */
    public String editar(final Long id, final TipoRendimento tipoRendimento){

        final TipoRendimento tipoRendimentoBanco = this.findById(id);

        Assert.isTrue(
                tipoRendimento.getId().equals(tipoRendimentoBanco.getId()),
                this.mensagem.texto("error.id.diferentes"));

        this.tipoRendimentoRepository.save(tipoRendimento);

        return this.mensagem.texto("sucesso.editar", "Tipo de Rendimento");
    }

    /**
     *
     * @param id
     * @return
     */
    public String excluir(final Long id){

        final TipoRendimento tipoRendimento = this.findById(id);

        this.tipoRendimentoRepository.delete(tipoRendimento);

        return this.mensagem.texto("sucesso.excluir", "Tipo de Rendimento");
    }
}
