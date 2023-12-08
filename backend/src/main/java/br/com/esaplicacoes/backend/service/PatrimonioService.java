package br.com.esaplicacoes.backend.service;

import br.com.esaplicacoes.backend.component.Mensagem;
import br.com.esaplicacoes.backend.entity.Patrimonio;
import br.com.esaplicacoes.backend.repository.PatrimonioRepository;
import br.com.esaplicacoes.backend.repository.TipoInvestimentoRepository;
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
public class PatrimonioService {

    @Autowired
    private Mensagem mensagem;
    @Autowired
    private PatrimonioRepository patrimonioRepository;
    @Autowired
    private TipoInvestimentoRepository tipoInvestimentoRepository;

    /**
     *
     * @param id
     * @return
     */
    public Patrimonio findById(final Long id){

        return this.patrimonioRepository.findById(id)
                .map(patrimonio -> {
                    return patrimonio;
                })
                .orElseThrow(() -> {
                    throw new IllegalStateException(
                            this.mensagem.texto("error.id.nao.encontrado", id)
                    );
                });
    }

    /**
     *
     * @param patrimonio
     * @return
     */
    public String cadastrar(final Patrimonio patrimonio){

        this.patrimonioRepository.save(patrimonio);

        Assert.isTrue(patrimonio.getDtFim().isAfter(patrimonio.getDtInicio())
                        || patrimonio.getDtInicio().isEqual(patrimonio.getDtFim()),
                this.mensagem.texto("error.data.fim.menor.data.inicio"));

        return this.mensagem.texto("sucesso.cadastro","Patrimônio");
    }

    /**
     *
     * @param id
     * @param patrimonio
     * @return
     */
    public String editar(final Long id, final Patrimonio patrimonio){

        final Patrimonio patrimonioBanco = this.findById(id);

        Assert.isTrue(
                patrimonio.getId().equals(patrimonioBanco.getId()),
                this.mensagem.texto("error.id.diferentes"));

        this.patrimonioRepository.save(patrimonio);

        return this.mensagem.texto("sucesso.editar", "Patrimônio");
    }

    /**
     *
     * @param id
     * @return
     */
    public String excluir(final Long id){

        final Patrimonio patrimonio = this.findById(id);

        this.patrimonioRepository.delete(patrimonio);

        return this.mensagem.texto("sucesso.excluir", "Patrimonio");
    }
}
