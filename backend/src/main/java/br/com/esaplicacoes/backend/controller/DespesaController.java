package br.com.esaplicacoes.backend.controller;

import br.com.esaplicacoes.backend.entity.Despesa;
import br.com.esaplicacoes.backend.repository.DespesaRepository;
import br.com.esaplicacoes.backend.service.DespesaService;
import br.com.esaplicacoes.backend.service.PatrimonioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
@RequestMapping(value = "/api/despesa")
public class DespesaController {

    @Autowired
    private DespesaService despesaService;
    @Autowired
    private DespesaRepository despesaRepository;
    @Autowired
    private PatrimonioService patrimonioService;

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Despesa> findById(
            final @PathVariable("id") Long id
    ){
        return ResponseEntity.ok().body(this.despesaService.findById(id));
    }

    /**
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<List<Despesa>> findAll(){
        return ResponseEntity.ok().body(
                this.despesaRepository.findAll(
                        Sort.by(Sort.Direction.DESC, "data")));
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/patrimonio")
    public ResponseEntity<Set<Despesa>> findAll(
            @RequestParam(value = "id", required = false) final Long id
    ){
        return ResponseEntity.ok().body(this.despesaRepository
                .findByPatrimonioAndAtivoTrueOrderByDataDesc(
                        this.patrimonioService.findById(id)));
    }

    /**
     *
     * @param despesa
     * @return
     */
    @PostMapping
    public ResponseEntity<String> cadastrar(
            final @RequestBody Despesa despesa
    ){
        return ResponseEntity.ok().body(this.despesaService.cadastrar(despesa));
    }

    /**
     *
     * @param id
     * @param despesa
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> editar(
            final @PathVariable("id") Long id,
            final @RequestBody Despesa despesa
    ){
        return ResponseEntity.ok().body(this.despesaService.editar(id, despesa));
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
        return ResponseEntity.ok().body(this.despesaService.excluir(id));
    }
}
