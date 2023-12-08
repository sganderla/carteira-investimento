package br.com.esaplicacoes.backend.controller;

import br.com.esaplicacoes.backend.entity.Rendimento;
import br.com.esaplicacoes.backend.repository.RendimentoRepository;
import br.com.esaplicacoes.backend.service.PatrimonioService;
import br.com.esaplicacoes.backend.service.RendimentoService;
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
@RequestMapping(value = "/api/rendimento")
public class RendimentoController {

    @Autowired
    private RendimentoService rendimentoService;
    @Autowired
    private RendimentoRepository rendimentoRepository;
    @Autowired
    private PatrimonioService patrimonioService;

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Rendimento> findById(
            final @PathVariable("id") Long id
    ){
        return ResponseEntity.ok().body(this.rendimentoService.findById(id));
    }

    /**
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<List<Rendimento>> findAll(){
        return ResponseEntity.ok().body(
                this.rendimentoRepository.findAll(
                        Sort.by(Sort.Direction.DESC, "data")));
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/patrimonio")
    public ResponseEntity<Set<Rendimento>> findAll(
            @RequestParam(value = "id", required = false) final Long id
    ){
        return ResponseEntity.ok().body(this.rendimentoRepository
                .findByPatrimonioAndAtivoTrueOrderByDataDesc(
                        this.patrimonioService.findById(id)));
    }

    /**
     *
     * @param rendimento
     * @return
     */
    @PostMapping
    public ResponseEntity<String> cadastrar(
            final @RequestBody Rendimento rendimento
    ){
        return ResponseEntity.ok().body(this.rendimentoService.cadastrar(rendimento));
    }

    /**
     *
     * @param id
     * @param rendimento
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> editar(
            final @PathVariable("id") Long id,
            final @RequestBody Rendimento rendimento
    ){
        return ResponseEntity.ok().body(this.rendimentoService.editar(id, rendimento));
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
        return ResponseEntity.ok().body(this.rendimentoService.excluir(id));
    }
}
