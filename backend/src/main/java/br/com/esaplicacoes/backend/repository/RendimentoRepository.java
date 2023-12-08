package br.com.esaplicacoes.backend.repository;

import br.com.esaplicacoes.backend.dto.DashboardRendimentoDTO;
import br.com.esaplicacoes.backend.entity.Patrimonio;
import br.com.esaplicacoes.backend.entity.Rendimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author Eduardo Sganderla
 *
 * @version 1.0.0, 08/08/2023
 * @since 1.0.0
 */
@Repository
public interface RendimentoRepository extends JpaRepository<Rendimento, Long> {

    /**
     *
     * @return
     */
    public Set<Rendimento> findByAtivoTrue();

    /**
     *
     * @param patrimonio
     * @return
     */
    public Set<Rendimento> findByPatrimonioAndAtivoTrueAndPagoTrue(final Patrimonio patrimonio);

    /**
     *
     * @param patrimonio
     * @return
     */
    public Set<Rendimento> findByPatrimonioAndAtivoTrueOrderByDataDesc(final Patrimonio patrimonio);

    /**
     *
     * @param pago
     * @param ano
     */
    @Query("select " +
            "   new br.com.esaplicacoes.backend.dto.DashboardRendimentoDTO(" +
            "       (extract(month from data)), " +
            "       (extract(year from data)), " +
            "       sum(valor)" +
            "   ) " +
            " from Rendimento " +
            " where 1 = 1 " +
            "   and (pago = :pago or :pago is null) " +
            "   and (extract(year from data)) = :ano " +
            " group by (extract(month from data)), (extract(year from data)) " +
            " order by 1 ")
    public List<DashboardRendimentoDTO> findRendimentosByAno(
            final @Param("pago") Boolean pago,
            final @Param("ano") Integer ano);
}
