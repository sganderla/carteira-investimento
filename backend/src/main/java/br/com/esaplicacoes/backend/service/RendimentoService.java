package br.com.esaplicacoes.backend.service;

import br.com.esaplicacoes.backend.component.CalcularPrecoMedioVenda;
import br.com.esaplicacoes.backend.component.CalcularRendimento;
import br.com.esaplicacoes.backend.component.Mensagem;
import br.com.esaplicacoes.backend.entity.Rendimento;
import br.com.esaplicacoes.backend.repository.RendimentoRepository;
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
public class RendimentoService {

    @Autowired
    private Mensagem mensagem;
    @Autowired
    private PatrimonioService patrimonioService;
    @Autowired
    private CalcularRendimento calcularRendimento;
    @Autowired
    private RendimentoRepository rendimentoRepository;
    @Autowired
    private CalcularPrecoMedioVenda calcularPrecoMedioVenda;

    /**
     *
     * @param id
     * @return
     */
    public Rendimento findById(final Long id){

        return this.rendimentoRepository.findById(id)
                .map(rendimento -> {
                    return rendimento;
                })
                .orElseThrow(() -> {
                    throw new IllegalStateException(
                            this.mensagem.texto("error.id.nao.encontrado", id)
                    );
                });
    }

    /**
     *
     * @param rendimento
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public String cadastrar(final Rendimento rendimento){

        this.rendimentoRepository.save(rendimento);

        if(rendimento.isPago() && rendimento.isAtivo()) {
            this.calcularRendimento.calcularCadastrar(rendimento.getPatrimonio(), rendimento);
        }

        return this.mensagem.texto("sucesso.cadastro","Rendimento");
    }

    /**
     *
     * @param id
     * @param rendimento
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public String editar(final Long id, final Rendimento rendimento){

        final Rendimento rendimentoBanco = this.findById(id);

        Assert.isTrue(
                rendimento.getId().equals(rendimentoBanco.getId()),
                this.mensagem.texto("error.id.diferentes"));

        this.rendimentoRepository.save(rendimento);

//        if(rendimentoBanco.isAtivo() && rendimentoBanco.isPago() && rendimento.isAtivo() && rendimento.isPago()){
//            this.calcularRendimento.calcularEditar(rendimento.getPatrimonio(), rendimentoBanco, rendimento);
//        }
//        else if ((rendimentoBanco.isAtivo() && rendimentoBanco.isPago()) && (!rendimento.isAtivo() || !rendimento.isPago())){
//            this.calcularRendimento.calcularExcluir(rendimento.getPatrimonio(), rendimentoBanco);
//        }
//        else if((!rendimentoBanco.isAtivo() || !rendimentoBanco.isPago()) && (rendimento.isAtivo() && rendimento.isPago())){
//            this.calcularRendimento.calcularCadastrar(rendimento.getPatrimonio(), rendimento);
//        }

        this.calcularPrecoMedioVenda.calcular(this.patrimonioService.findById(rendimento.getPatrimonio().getId()));

        return this.mensagem.texto("sucesso.editar", "Rendimento");
    }

    /**
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public String excluir(final Long id){

        final Rendimento rendimento = this.findById(id);

        this.rendimentoRepository.delete(rendimento);

        this.calcularRendimento.calcularExcluir(rendimento.getPatrimonio(), rendimento);

        return this.mensagem.texto("sucesso.excluir", "Rendimento");
    }
}
