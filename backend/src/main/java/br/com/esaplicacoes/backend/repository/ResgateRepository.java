package br.com.esaplicacoes.backend.repository;

import br.com.esaplicacoes.backend.entity.Patrimonio;
import br.com.esaplicacoes.backend.entity.Resgate;
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
public interface ResgateRepository extends JpaRepository<Resgate, Long> {

    /**
     * @return
     */
    public Set<Resgate> findByAtivoTrue();

    /**
     * @param patrimonio
     * @return
     */
    public Set<Resgate> findByPatrimonioAndAtivoTrue(Patrimonio patrimonio);

    /**
     *
     * @param tipoInvestimentoId
     * @param mes
     * @param ano
     * @return
     */
    @Query(" select sum(resgate.valor * resgate.quantidade) " +
            " from Resgate resgate " +
            " where 1 = 1 " +
            "   and resgate.patrimonio.tipoInvestimento.id = :tipoInvestimentoId " +
            "   and year(resgate.data) = :ano " +
            "   and month(resgate.data) = :mes ")
    BigDecimal findSomaTotalMes(
            @Param("tipoInvestimentoId") final Long tipoInvestimentoId,
            final @Param("mes") Integer mes,
            final @Param("ano") Integer ano);

}
