package br.com.esaplicacoes.backend.repository;

import br.com.esaplicacoes.backend.entity.Investimento;
import br.com.esaplicacoes.backend.entity.Patrimonio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Set;

/**
 * @author Eduardo Sganderla
 *
 * @version 1.0.0, 08/08/2023
 * @since 1.0.0
 */
@Repository
public interface InvestimentoRepository extends JpaRepository<Investimento, Long> {

    /**
     *
     * @return
     */
    public Set<Investimento> findByAtivoTrue();

    /**
     *
     * @param patrimonio
     * @return
     */
    public Set<Investimento> findByPatrimonioAndAtivoTrue(Patrimonio patrimonio);

    /**
     *
     * @param tipoInvestimentoId
     * @return
     */
    @Query(" from Investimento investimento " +
            " where investimento.patrimonio.tipoInvestimento.id = :tipoInvestimentoId or :tipoInvestimentoId is null " +
            " order by investimento.data desc")
    public Set<Investimento> findByTipoInvestimento(@Param("tipoInvestimentoId") final Long tipoInvestimentoId);

    /**
     *
     * @param tipoInvestimentoId
     * @param mes
     * @param ano
     * @return
     */
    @Query(" select sum(investimento.valor * investimento.quantidade) " +
            " from Investimento investimento " +
            " where 1 = 1 " +
            "   and investimento.patrimonio.tipoInvestimento.id = :tipoInvestimentoId " +
            "   and year(investimento.data) = :ano " +
            "   and month(investimento.data) = :mes ")
    BigDecimal findSomaTotalMes(
            @Param("tipoInvestimentoId") final Long tipoInvestimentoId,
            @Param("mes") final Integer mes,
            @Param("ano") final Integer ano);
}
