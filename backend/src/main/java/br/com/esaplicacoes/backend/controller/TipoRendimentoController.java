package br.com.esaplicacoes.backend.controller;

import br.com.esaplicacoes.backend.component.Mensagem;
import br.com.esaplicacoes.backend.entity.TipoDespesa;
import br.com.esaplicacoes.backend.entity.TipoRendimento;
import br.com.esaplicacoes.backend.repository.TipoRendimentoRepository;
import br.com.esaplicacoes.backend.service.TipoRendimentoService;
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
@RequestMapping(value = "/api/tipo-rendimento")
public class TipoRendimentoController {

    @Autowired
    private Mensagem mensagem;
    @Autowired
    private TipoRendimentoService tipoRendimentoService;
    @Autowired
    private TipoRendimentoRepository tipoRendimentoRepository;

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<TipoRendimento> findById(
            final @PathVariable("id") Long id
    ){
        return ResponseEntity.ok().body(this.tipoRendimentoService.findById(id));
    }

    /**
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<List<TipoRendimento>> findAll(){
        return ResponseEntity.ok().body(this.tipoRendimentoRepository.findAll());
    }

    /**
     *
     * @return
     */
    @GetMapping("/ativo")
    public ResponseEntity<Set<TipoRendimento>> findAllAtivo(){
        return ResponseEntity.ok().body(this.tipoRendimentoRepository.findByAtivoTrue());
    }

    /**
     *
     * @param tipoRendimento
     * @return
     */
    @PostMapping
    public ResponseEntity<String> cadastrar(
            final @RequestBody TipoRendimento tipoRendimento
    ){
        return ResponseEntity.ok().body(this.tipoRendimentoService.cadastrar(tipoRendimento));
    }

    /**
     *
     * @param id
     * @param tipoRendimento
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> editar(
            final @PathVariable("id") Long id,
            final @RequestBody TipoRendimento tipoRendimento
    ){
        return ResponseEntity.ok().body(this.tipoRendimentoService.editar(id, tipoRendimento));
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
        return ResponseEntity.ok().body(this.tipoRendimentoService.excluir(id));
    }
}
