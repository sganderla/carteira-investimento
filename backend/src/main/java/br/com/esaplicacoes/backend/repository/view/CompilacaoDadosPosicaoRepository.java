package br.com.esaplicacoes.backend.repository.view;

import br.com.esaplicacoes.backend.entity.view.CompilacaoDadosPosicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @author Eduardo Sganderla
 *
 * @version 1.0.0, 05/12/2023
 * @since 1.0.0
 */
@Repository
public interface CompilacaoDadosPosicaoRepository extends JpaRepository<CompilacaoDadosPosicao, Long> {

    /**
     *
     * @param tipoInvestimentoId
     * @param ano
     * @param mes
     * @return
     */
    @Query("from CompilacaoDadosPosicao where 1 = 1 " +
            "and ano >= :ano " +
            "and mes >= :mes " +
            "and (tipoInvestimentoId = :tipoInvestimentoId or :tipoInvestimentoId is null)")
    public Set<CompilacaoDadosPosicao> findByAnoMes(
            final @Param("tipoInvestimentoId") Long tipoInvestimentoId,
            final @Param("ano") Integer ano,
            final @Param("mes") Integer mes);

}
