package br.com.esaplicacoes.backend.component;

import br.com.esaplicacoes.backend.entity.Despesa;
import br.com.esaplicacoes.backend.entity.Patrimonio;
import br.com.esaplicacoes.backend.entity.Rendimento;
import br.com.esaplicacoes.backend.repository.PatrimonioRepository;
import br.com.esaplicacoes.backend.service.PatrimonioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Eduardo Sganderla
 *
 * @version 1.0.0, 07/09/2023
 * @since 1.0.0
 */
@Component
public class CalcularRendimento {

    @Autowired
    private PatrimonioService patrimonioService;
    @Autowired
    private PatrimonioRepository patrimonioRepository;

    /**
     *
     * @param patrimonio
     * @param rendimento
     */
    public void calcularCadastrar(Patrimonio patrimonio, final Rendimento rendimento) {

        patrimonio = this.patrimonioService.findById(patrimonio.getId());

        patrimonio.setVlRendimento(patrimonio.getVlRendimento().add(rendimento.getValor()));

        final BigDecimal perRendimento = patrimonio.getVlRendimento()
                .multiply(BigDecimal.valueOf(100))
                .divide(patrimonio.getVlMedio().multiply(BigDecimal.valueOf(patrimonio.getQtdePatrimonio())),
                        2, RoundingMode.HALF_UP);

        patrimonio.setPerRendimento(perRendimento);

        this.patrimonioRepository.save(patrimonio);
    }

    /**
     *
     * @param patrimonio
     * @param rendimentoAntigo
     * @param rendimentoNovo
     */
    public void calcularEditar(Patrimonio patrimonio, final Rendimento rendimentoAntigo, final Rendimento rendimentoNovo){

        patrimonio = this.patrimonioService.findById(patrimonio.getId());

        patrimonio.setVlRendimento(patrimonio.getVlRendimento().subtract(rendimentoAntigo.getValor()));
        patrimonio.setVlRendimento(patrimonio.getVlRendimento().add(rendimentoNovo.getValor()));

        final BigDecimal perRendimento = patrimonio.getVlRendimento()
                .multiply(BigDecimal.valueOf(100))
                .divide(patrimonio.getVlMedio().multiply(BigDecimal.valueOf(patrimonio.getQtdePatrimonio())),
                        2, RoundingMode.HALF_UP);

        patrimonio.setPerRendimento(perRendimento);

        this.patrimonioRepository.save(patrimonio);
    }

    /**
     *
     * @param patrimonio
     * @param rendimento
     */
    public void calcularExcluir(Patrimonio patrimonio, final Rendimento rendimento){

        patrimonio = this.patrimonioService.findById(patrimonio.getId());

        patrimonio.setVlRendimento(patrimonio.getVlRendimento().subtract(rendimento.getValor()));

        final BigDecimal perRendimento = patrimonio.getVlRendimento()
                .multiply(BigDecimal.valueOf(100))
                .divide(patrimonio.getVlMedio().multiply(BigDecimal.valueOf(patrimonio.getQtdePatrimonio())),
                        2, RoundingMode.HALF_UP);

        patrimonio.setPerRendimento(perRendimento);

        this.patrimonioRepository.save(patrimonio);
    }
}
