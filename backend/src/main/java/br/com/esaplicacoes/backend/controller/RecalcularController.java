package br.com.esaplicacoes.backend.controller;

import br.com.esaplicacoes.backend.component.CalcularPrecoMedioVenda;
import br.com.esaplicacoes.backend.component.Mensagem;
import br.com.esaplicacoes.backend.service.PatrimonioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Eduardo Sganderla
 *
 * @version 1.0.0, 15/09/2023
 * @since 1.0.0
 */
@RestController
@RequestMapping(value = "/api/recalcular")
public class RecalcularController {

    @Autowired
    private Mensagem mensagem;
        @Autowired
        private CalcularPrecoMedioVenda calcularPrecoMedioVenda;
        @Autowired
        private PatrimonioService patrimonioService;


    /**
     *
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> editar(
            final @PathVariable("id") Long id
    ){
        this.calcularPrecoMedioVenda.calcular(this.patrimonioService.findById(id));
        return ResponseEntity.ok().body( this.mensagem.texto("sucesso.recalcular") );
    }
}
