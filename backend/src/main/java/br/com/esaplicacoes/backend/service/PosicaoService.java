package br.com.esaplicacoes.backend.service;

import br.com.esaplicacoes.backend.component.Mensagem;
import br.com.esaplicacoes.backend.dto.PosicaoDTO;
import br.com.esaplicacoes.backend.entity.Posicao;
import br.com.esaplicacoes.backend.entity.TipoInvestimento;
import br.com.esaplicacoes.backend.repository.*;
import org.hibernate.usertype.BaseUserTypeSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Eduardo Sganderla
 *
 * @version 1.0.0, 29/07/2023
 * @since 1.0.0
 */
@Service
public class PosicaoService {

    @Autowired
    private Mensagem mensagem;
    @Autowired
    private PosicaoRepository posicaoRepository;
    @Autowired
    private TipoInvestimentoRepository tipoInvestimentoRepository;
    @Autowired
    private ResgateRepository resgateRepository;
    @Autowired
    private InvestimentoRepository investimentoRepository;


    /**
     *
     * @param id
     * @return
     */
    public Posicao findById(final Long id){

        return this.posicaoRepository.findById(id)
                .map(posicao -> {
                    return posicao;
                })
                .orElseThrow(() -> {
                    throw new IllegalStateException(
                            this.mensagem.texto("error.id.nao.encontrado", id)
                    );
                });
    }

    /**
     *
     * @param dataFiltro
     * @return
     */
    public List<PosicaoDTO> findAllDetalhada(final LocalDate dataFiltro){

        final Set<TipoInvestimento> tipoInvestimentos = this.tipoInvestimentoRepository.findByAtivoTrue();
        final List<PosicaoDTO> posicaoDTOS = new ArrayList<PosicaoDTO>();

        tipoInvestimentos.forEach(tipoInvestimento -> {

            final Posicao posicao = this.posicaoRepository
                    .findUltimaPosicao(
                            tipoInvestimento.getId(),
                            dataFiltro.getMonthValue(),
                            dataFiltro.getYear());

            final Posicao posicaoAnterior = this.posicaoRepository
                    .findUltimaPosicao(
                            tipoInvestimento.getId(),
                            dataFiltro.plusMonths(-1).getMonthValue(),
                            dataFiltro.plusMonths(-1).getYear());

            if (posicao != null) {

                BigDecimal ganho = posicao.getValor().subtract(
                        posicaoAnterior == null
                                ? posicao.getValor()
                                : posicaoAnterior.getValor());

                final BigDecimal investimento = this.investimentoRepository
                        .findSomaTotalMes(
                                tipoInvestimento.getId(),
                                dataFiltro.getMonthValue(),
                                dataFiltro.getYear()
                        );

                final BigDecimal resgate = this.resgateRepository
                        .findSomaTotalMes(
                                tipoInvestimento.getId(),
                                dataFiltro.getMonthValue(),
                                dataFiltro.getYear()
                        );

                ganho = investimento == null ? ganho : ganho.subtract(investimento);
                ganho = resgate == null ? ganho : ganho.add(resgate);

                BigDecimal rendimento = ganho.multiply(new BigDecimal(100));
                rendimento = rendimento.divide(posicao.getValor(), MathContext.DECIMAL128);

                final PosicaoDTO posicaoDTO = new PosicaoDTO();
                posicaoDTO.setTipoInvestimento(tipoInvestimento);
                posicaoDTO.setValor(posicao.getValor());
                posicaoDTO.setData(posicao.getData());
                posicaoDTO.setGanho(ganho == null ? BigDecimal.ZERO : ganho);
                posicaoDTO.setRendimento(rendimento);
                posicaoDTO.setInvestimento(investimento == null ? BigDecimal.ZERO : investimento );
                posicaoDTO.setResgate(resgate == null ? BigDecimal.ZERO : resgate   );

                posicaoDTOS.add(posicaoDTO);
            }
        });

        return posicaoDTOS;
    }

    /**
     *
     * @param posicao
     * @return
     */
    public String cadastrar(final Posicao posicao){

        this.posicaoRepository.save(posicao);

        return this.mensagem.texto("sucesso.cadastro","Posição");
    }

    /**
     *
     * @param id
     * @param posicao
     * @return
     */
    public String editar(final Long id, final Posicao posicao){

        final Posicao posicaoBanco = this.findById(id);

        Assert.isTrue(
                posicao.getId().equals(posicaoBanco.getId()),
                this.mensagem.texto("error.id.diferentes"));

        this.posicaoRepository.save(posicao);

        return this.mensagem.texto("sucesso.editar", "Posição");
    }

    /**
     *
     * @param id
     * @return
     */
    public String excluir(final Long id){

        final Posicao posicao = this.findById(id);

        this.posicaoRepository.delete(posicao);

        return this.mensagem.texto("sucesso.excluir", "Posição");
    }
}
