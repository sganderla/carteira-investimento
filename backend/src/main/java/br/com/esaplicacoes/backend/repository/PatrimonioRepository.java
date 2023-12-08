package br.com.esaplicacoes.backend.repository;

import br.com.esaplicacoes.backend.entity.Investimento;
import br.com.esaplicacoes.backend.entity.Patrimonio;
import br.com.esaplicacoes.backend.entity.TipoInvestimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @author Eduardo Sganderla
 *
 * @version 1.0.0, 08/08/2023
 * @since 1.0.0
 */
@Repository
public interface PatrimonioRepository extends JpaRepository<Patrimonio, Long> {

    /**
     *
     * @return
     */
    public Set<Patrimonio> findByAtivoTrueAndFinalizadoFalseOrderByTipoInvestimentoIdDesc();

    /**
     *
     * @param tipoInvestimentoId
     * @return
     */
    @Query(" from Patrimonio patrimonio " +
            " where 1 = 1 " +
            "       and patrimonio.ativo = true " +
            "       and (upper(patrimonio.nome) like concat('%',upper(:filtro),'%')) " +
            "       and (patrimonio.tipoInvestimento.id = :tipoInvestimentoId or :tipoInvestimentoId is null) " +
            " order by patrimonio.finalizado, patrimonio.tipoInvestimento.nome, patrimonio.nome")
    public Set<Patrimonio> findByTipoInvestimento(@Param("tipoInvestimentoId") final Long tipoInvestimentoId, final String filtro);

    /**
     *
     * @param tipoInvestimentoId
     * @return
     */
    @Query(" from Patrimonio patrimonio " +
            " where 1 = 1 " +
            "       and patrimonio.ativo = true " +
            "       and patrimonio.finalizado = false " +
            "       and (patrimonio.tipoInvestimento.id = :tipoInvestimentoId or :tipoInvestimentoId is null) " +
            " order by patrimonio.finalizado, patrimonio.tipoInvestimento.nome, patrimonio.nome")
    public Set<Patrimonio> findByTipoInvestimentoAndFinalizado(
            @Param("tipoInvestimentoId") final Long tipoInvestimentoId
    );
}
