package br.com.esaplicacoes.backend.repository;

import br.com.esaplicacoes.backend.entity.Posicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Set;

/**
 * @author Eduardo Sganderla
 *
 * @version 1.0.0, 29/07/2023
 * @since 1.0.0
 */
@Repository
public interface PosicaoRepository extends JpaRepository<Posicao, Long> {

    /**
     *
     * @return
     */
    public Set<Posicao> findByAtivoTrue();

    /**
     *
     * @param dataFiltro
     * @return
     */
    @Query("from Posicao posicao " +
            "where posicao.data = :dataFiltro or (cast(:dataFiltro as date) is null) " +
            "order by posicao.data desc")
    public Set<Posicao> findPosicaoByData(
            @Param("dataFiltro") final LocalDate dataFiltro);

    /**
     *
     * @param tipoInvestimentoId
     * @return
     */
    @Query(" from Posicao posicao " +
            " where 1 = 1" +
            "   and posicao.tipoInvestimento.id = :tipoInvestimentoId " +
            "   and year(posicao.data) = :ano " +
            "   and month(posicao.data) = :mes " +
            " order by posicao.data desc limit 1 ")
    public Posicao findUltimaPosicao(
            @Param("tipoInvestimentoId") final Long tipoInvestimentoId,
            @Param("mes") final Integer mes,
            @Param("ano") final Integer ano);
}
