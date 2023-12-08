package br.com.esaplicacoes.backend.controller;

import br.com.esaplicacoes.backend.dto.PosicaoDTO;
import br.com.esaplicacoes.backend.entity.Posicao;
import br.com.esaplicacoes.backend.repository.PosicaoRepository;
import br.com.esaplicacoes.backend.service.PosicaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * @author Eduardo Sganderla
 *
 * @version 1.0.0, 29/07/2023
 * @since 1.0.0
 */
@RestController
@RequestMapping(value = "/api/posicao")
public class PosicaoController {

    @Autowired
    private PosicaoService posicaoService;
    @Autowired
    private PosicaoRepository posicaoRepository;

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Posicao> findById(
            final @PathVariable("id") Long id
    ){
        return ResponseEntity.ok().body(this.posicaoService.findById(id));
    }

    /**
     *
     * @return
     */
    @GetMapping("/lista")
    public ResponseEntity<Set<Posicao>> findAll(
            @RequestParam(value = "dataFiltro", required = false) final LocalDate dataFiltro
    ){
        return ResponseEntity.ok().body(this.posicaoRepository.findPosicaoByData(dataFiltro));
    }

    /**
     *
     * @return
     */
    @GetMapping("/detalhada")
    public ResponseEntity<List<PosicaoDTO>> findAllDetalhada(
            @RequestParam(value = "dataFiltro", required = false) final LocalDate dataFiltro
    ){
        return ResponseEntity.ok().body(this.posicaoService.findAllDetalhada(dataFiltro));
    }

    /**
     *
     * @param posicao
     * @return
     */
    @PostMapping
    public ResponseEntity<String> cadastrar(
            final @RequestBody Posicao posicao
    ){
        return ResponseEntity.ok().body(this.posicaoService.cadastrar(posicao));
    }

    /**
     *
     * @param id
     * @param posicao
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> editar(
            final @PathVariable("id") Long id,
            final @RequestBody Posicao posicao
    ){
        return ResponseEntity.ok().body(this.posicaoService.editar(id, posicao));
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
        return ResponseEntity.ok().body(this.posicaoService.excluir(id));
    }
}
