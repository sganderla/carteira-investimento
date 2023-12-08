package br.com.esaplicacoes.backend.repository;

import br.com.esaplicacoes.backend.entity.TipoInvestimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @author Eduardo Sganderla
 *
 * @version 1.0.0, 29/07/2023
 * @since 1.0.0
 */
@Repository
public interface TipoInvestimentoRepository extends JpaRepository<TipoInvestimento, Long> {

    /**
     *
     * @return
     */
    public Set<TipoInvestimento> findByAtivoTrue();

    /**
     *
     * @return
     */
    public Set<TipoInvestimento> findByRendaVariadaTrue();

}
