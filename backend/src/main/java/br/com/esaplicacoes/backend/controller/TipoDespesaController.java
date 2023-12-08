package br.com.esaplicacoes.backend.controller;

import br.com.esaplicacoes.backend.component.Mensagem;
import br.com.esaplicacoes.backend.entity.TipoDespesa;
import br.com.esaplicacoes.backend.entity.TipoInvestimento;
import br.com.esaplicacoes.backend.repository.TipoDespesaRepository;
import br.com.esaplicacoes.backend.service.TipoDespesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * @author Eduardo Sganderla
 *
 * @version 1.0.0, 29/07/2023
 * @since 1.0.0
 */
@RestController
@RequestMapping(value = "/api/tipo-despesa")
public class TipoDespesaController {

    @Autowired
    private Mensagem mensagem;
    @Autowired
    private TipoDespesaService tipoDespesaService;
    @Autowired
    private TipoDespesaRepository tipoDespesaRepository;

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<TipoDespesa> findById(
            final @PathVariable("id") Long id
    ){
        return ResponseEntity.ok().body(this.tipoDespesaService.findById(id));
    }

    /**
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<List<TipoDespesa>> findAll(){
        return ResponseEntity.ok().body(this.tipoDespesaRepository.findAll());
    }

    /**
     *
     * @return
     */
    @GetMapping("/ativo")
    public ResponseEntity<Set<TipoDespesa>> findAllAtivo(){
        return ResponseEntity.ok().body(this.tipoDespesaRepository.findByAtivoTrue());
    }

    /**
     *
     * @param tipoDespesa
     * @return
     */
    @PostMapping
    public ResponseEntity<String> cadastrar(
            final @RequestBody TipoDespesa tipoDespesa
    ){
        return ResponseEntity.ok().body(this.tipoDespesaService.cadastrar(tipoDespesa));
    }

    /**
     *
     * @param id
     * @param tipoDespesa
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> editar(
            final @PathVariable("id") Long id,
            final @RequestBody TipoDespesa tipoDespesa
    ){
        return ResponseEntity.ok().body(this.tipoDespesaService.editar(id, tipoDespesa));
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
        return ResponseEntity.ok().body(this.tipoDespesaService.excluir(id));
    }
}
