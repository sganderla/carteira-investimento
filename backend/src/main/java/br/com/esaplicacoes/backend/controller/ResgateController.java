package br.com.esaplicacoes.backend.controller;

import br.com.esaplicacoes.backend.entity.Resgate;
import br.com.esaplicacoes.backend.repository.ResgateRepository;
import br.com.esaplicacoes.backend.service.ResgateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Eduardo Sganderla
 *
 * @version 1.0.0, 08/08/2023
 * @since 1.0.0
 */
@RestController
@RequestMapping(value = "/api/resgate")
public class ResgateController {

    @Autowired
    private ResgateService resgateService;
    @Autowired
    private ResgateRepository resgateRepository;

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Resgate> findById(
            final @PathVariable("id") Long id
    ){
        return ResponseEntity.ok().body(this.resgateService.findById(id));
    }

    /**
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<List<Resgate>> findAll(){
        return ResponseEntity.ok().body(
                this.resgateRepository.findAll(
                        Sort.by(Sort.Direction.DESC, "data")));
    }

    /**
     *
     * @param resgate
     * @return
     */
    @PostMapping
    public ResponseEntity<String> cadastrar(
            final @RequestBody Resgate resgate
    ){
        return ResponseEntity.ok().body(this.resgateService.cadastrar(resgate));
    }

    /**
     *
     * @param id
     * @param resgate
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> editar(
            final @PathVariable("id") Long id,
            final @RequestBody Resgate resgate
    ){
        return ResponseEntity.ok().body(this.resgateService.editar(id, resgate));
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
        return ResponseEntity.ok().body(this.resgateService.excluir(id));
    }
}
