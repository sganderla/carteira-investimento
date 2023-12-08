package br.com.esaplicacoes.backend.repository;

import br.com.esaplicacoes.backend.entity.TipoDespesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @author Eduardo Sganderla
 *
 * @version 1.0.0, 04/08/2023
 * @since 1.0.0
 */
@Repository
public interface TipoDespesaRepository extends JpaRepository<TipoDespesa, Long> {

    /**
     *
     * @return
     */
    public Set<TipoDespesa> findByAtivoTrue();

}
