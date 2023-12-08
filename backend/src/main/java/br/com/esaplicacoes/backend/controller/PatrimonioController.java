package br.com.esaplicacoes.backend.controller;

import br.com.esaplicacoes.backend.component.Mensagem;
import br.com.esaplicacoes.backend.entity.Patrimonio;
import br.com.esaplicacoes.backend.repository.PatrimonioRepository;
import br.com.esaplicacoes.backend.service.PatrimonioService;
import br.com.esaplicacoes.backend.service.TipoInvestimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * @author Eduardo Sganderla
 *
 * @version 1.0.0, 08/08/2023
 * @since 1.0.0
 */
@RestController
@RequestMapping(value = "/api/patrimonio")
public class PatrimonioController {

    @Autowired
    private Mensagem mensagem;
    @Autowired
    private PatrimonioService patrimonioService;
    @Autowired
    private PatrimonioRepository patrimonioRepository;
    @Autowired
    private TipoInvestimentoService tipoInvestimentoService;


    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Patrimonio> findById(
            final @PathVariable("id") Long id
    ){
        return ResponseEntity.ok().body(this.patrimonioService.findById(id));
    }

    /**
     *
     * @return
     */
    @GetMapping("/lista")
    public ResponseEntity<Set<Patrimonio>> findAll(
            @RequestParam(value = "tipoInvestimentoId", required = false) final Long tipoInvestimentoId,
            @RequestParam(value = "filtro", required = false) final String filtro
    ){
        return ResponseEntity.ok().body(this.patrimonioRepository.findByTipoInvestimento(tipoInvestimentoId, filtro));
    }

    /**
     *
     * @return
     */
    @GetMapping("/ativo")
    public ResponseEntity<Set<Patrimonio>> findAllAtivo(){
        return ResponseEntity.ok().body(this.patrimonioRepository.findByAtivoTrueAndFinalizadoFalseOrderByTipoInvestimentoIdDesc());
    }

    /**
     *
     * @param patrimonio
     * @return
     */
    @PostMapping
    public ResponseEntity<String> cadastrar(
            final @RequestBody Patrimonio patrimonio
    ){
        return ResponseEntity.ok().body(this.patrimonioService.cadastrar(patrimonio));
    }

    /**
     *
     * @param id
     * @param patrimonio
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> editar(
            final @PathVariable("id") Long id,
            final @RequestBody Patrimonio patrimonio
    ){
        return ResponseEntity.ok().body(this.patrimonioService.editar(id, patrimonio));
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
        return ResponseEntity.ok().body(this.patrimonioService.excluir(id));
    }
}
