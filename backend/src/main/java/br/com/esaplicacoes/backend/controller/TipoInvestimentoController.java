package br.com.esaplicacoes.backend.controller;

import br.com.esaplicacoes.backend.component.Mensagem;
import br.com.esaplicacoes.backend.entity.TipoInvestimento;
import br.com.esaplicacoes.backend.repository.TipoInvestimentoRepository;
import br.com.esaplicacoes.backend.service.TipoInvestimentoService;
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
@RequestMapping(value = "/api/tipo-investimento")
public class TipoInvestimentoController {

    @Autowired
    private Mensagem mensagem;
    @Autowired
    private TipoInvestimentoService tipoInvestimentoService;
    @Autowired
    private TipoInvestimentoRepository tipoInvestimentoRepository;

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<TipoInvestimento> findById(
            final @PathVariable("id") Long id
    ){
        return ResponseEntity.ok().body(this.tipoInvestimentoService.findById(id));
    }

    /**
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<List<TipoInvestimento>> findAll(){
        return ResponseEntity.ok().body(this.tipoInvestimentoRepository.findAll());
    }

    /**
     *
     * @return
     */
    @GetMapping("/ativo")
    public ResponseEntity<Set<TipoInvestimento>> findAllAtivo(){
        return ResponseEntity.ok().body(this.tipoInvestimentoRepository.findByAtivoTrue());
    }

    /**
     *
     * @param tipoInvestimento
     * @return
     */
    @PostMapping
    public ResponseEntity<String> cadastrar(
            final @RequestBody TipoInvestimento tipoInvestimento
    ){
        return ResponseEntity.ok().body(this.tipoInvestimentoService.cadastrar(tipoInvestimento));
    }

    /**
     *
     * @param id
     * @param tipoInvestimento
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> editar(
            final @PathVariable("id") Long id,
            final @RequestBody TipoInvestimento tipoInvestimento
    ){
        return ResponseEntity.ok().body(this.tipoInvestimentoService.editar(id, tipoInvestimento));
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
        return ResponseEntity.ok().body(this.tipoInvestimentoService.excluir(id));
    }
}
