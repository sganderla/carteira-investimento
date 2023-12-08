package br.com.esaplicacoes.backend.controller;

import br.com.esaplicacoes.backend.entity.Investimento;
import br.com.esaplicacoes.backend.repository.InvestimentoRepository;
import br.com.esaplicacoes.backend.service.InvestimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * @author Eduardo Sganderla
 *
 * @version 1.0.0, 08/08/2023
 * @since 1.0.0
 */
@RestController
@RequestMapping(value = "/api/investimento")
public class InvestimentoController {

    @Autowired
    private InvestimentoService investimentoService;
    @Autowired
    private InvestimentoRepository investimentoRepository;

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Investimento> findById(
            final @PathVariable("id") Long id
    ){
        return ResponseEntity.ok().body(this.investimentoService.findById(id));
    }

    /**
     *
     * @return
     */
    @GetMapping("/lista")
    public ResponseEntity<Set<Investimento>> findAll(
            @RequestParam(value = "tipoInvestimentoId", required = false) final Long tipoInvestimentoId
    ){
        return ResponseEntity.ok().body(
                this.investimentoRepository.findByTipoInvestimento(tipoInvestimentoId));
    }

    /**
     *
     * @param investimento
     * @return
     */
    @PostMapping
    public ResponseEntity<String> cadastrar(
            final @RequestBody Investimento investimento
    ){
        return ResponseEntity.ok().body(this.investimentoService.cadastrar(investimento));
    }

    /**
     *
     * @param id
     * @param investimento
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> editar(
            final @PathVariable("id") Long id,
            final @RequestBody Investimento investimento
    ){
        return ResponseEntity.ok().body(this.investimentoService.editar(id, investimento));
    }

    /**
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluir(
            final @PathVariable("id") Long id
    ) {
        return ResponseEntity.ok().body(this.investimentoService.excluir(id));
    }
}
