package br.com.esaplicacoes.backend.controller;

import br.com.esaplicacoes.backend.service.CotacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Eduardo Sganderla
 *
 * @version 1.0.0, 23/11/2023
 * @since 1.0.0
 */
@RestController
@RequestMapping(value = "/api/cotacao")
public class CotacaoController {

    @Autowired
    private CotacaoService cotacaoService;

    /**
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<?> buscarCotacaoAtivos()  {
        return ResponseEntity.ok().body(this.cotacaoService.buscarCotacaoAtivos());
    }
}
