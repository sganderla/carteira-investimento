package br.com.esaplicacoes.backend.service;

import br.com.esaplicacoes.backend.component.ConexaoBrapi;
import br.com.esaplicacoes.backend.dto.CotacaoDTO;
import br.com.esaplicacoes.backend.entity.Patrimonio;
import br.com.esaplicacoes.backend.entity.TipoInvestimento;
import br.com.esaplicacoes.backend.entity.brapi.Result;
import br.com.esaplicacoes.backend.entity.brapi.Results;
import br.com.esaplicacoes.backend.repository.PatrimonioRepository;
import br.com.esaplicacoes.backend.repository.TipoInvestimentoRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Eduardo Sganderla
 *
 * @version 1.0.0, 23/11/2023
 * @since 1.0.0
 */
@Service
public class CotacaoService {

    @Autowired
    private ConexaoBrapi conexaoBrapi;
    @Autowired
    private PatrimonioRepository patrimonioRepository;
    @Autowired
    private TipoInvestimentoRepository tipoInvestimentoRepository;

    /**
     *
     */
    public List<CotacaoDTO> buscarCotacaoAtivos(){

        final List<CotacaoDTO> cotacaoDTOS = new ArrayList<CotacaoDTO>();
        final Set<TipoInvestimento> tipoInvestimentos = this.tipoInvestimentoRepository.findByRendaVariadaTrue();

        tipoInvestimentos.forEach(tipoInvestimento -> {

            final Set<Patrimonio> patrimonios = this.patrimonioRepository.findByTipoInvestimentoAndFinalizado(tipoInvestimento.getId());

            patrimonios.forEach(patrimonio -> {

                final Results results = this.conexaoBrapi.buscarCotacaoAtivo(patrimonio.getNome());
                final Result result = results.getResults().get(0);

                final CotacaoDTO cotacaoDTO = new CotacaoDTO();

                cotacaoDTO.setTipoInvestimento(tipoInvestimento.getNome());
                cotacaoDTO.setCodigo(result.getSymbol());
                cotacaoDTO.setNome(result.getLongName());
                cotacaoDTO.setData(result.getRegularMarketTime().toString().split(" ")[0]);
                cotacaoDTO.setPercentual(result.getRegularMarketChangePercent());
                cotacaoDTO.setPreco(result.getRegularMarketPrice());
                cotacaoDTO.setPrecoFechamento(result.getRegularMarketPreviousClose());
                cotacaoDTO.setLogoURL(result.getLogourl());
                cotacaoDTO.setQtdePatrimonio(patrimonio.getQtdePatrimonio());
                cotacaoDTO.setVlMedio(patrimonio.getVlMedio());

                final BigDecimal multiplicacao = result.getRegularMarketPrice().multiply(BigDecimal.valueOf(100));
                final BigDecimal divisao = multiplicacao.divide(patrimonio.getVlMedio(), 2, RoundingMode.HALF_UP);

                cotacaoDTO.setPercentualVariacao(divisao.subtract(BigDecimal.valueOf(100)));

                cotacaoDTOS.add(cotacaoDTO);
            });
        });

        return cotacaoDTOS;
    }
}
