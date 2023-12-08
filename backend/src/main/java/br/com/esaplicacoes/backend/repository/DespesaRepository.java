package br.com.esaplicacoes.backend.repository;

import br.com.esaplicacoes.backend.entity.Despesa;
import br.com.esaplicacoes.backend.entity.Patrimonio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @author Eduardo Sganderla
 *
 * @version 1.0.0, 08/08/2023
 * @since 1.0.0
 */
@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    /**
     *
     * @return
     */
    public Set<Despesa> findByAtivoTrue();

    /**
     *
     * @param patrimonio
     * @return
     */
    public Set<Despesa> findByPatrimonioAndAtivoTrueOrderByDataDesc(Patrimonio patrimonio);

}
