package br.com.esaplicacoes.backend.controller;

import br.com.esaplicacoes.backend.dto.DashboardRendimentoDTO;
import br.com.esaplicacoes.backend.entity.Posicao;
import br.com.esaplicacoes.backend.entity.Rendimento;
import br.com.esaplicacoes.backend.repository.RendimentoRepository;
import br.com.esaplicacoes.backend.repository.view.CompilacaoDadosPosicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * @author Eduardo Sganderla
 *
 * @version 1.0.0, 23/11/2023
 * @since 1.0.0
 */
@RestController
@RequestMapping(value = "/api/dashboard")
public class DashboardController {

    @Autowired
    private RendimentoRepository rendimentoRepository;
    @Autowired
    private CompilacaoDadosPosicaoRepository compilacaoDadosPosicaoRepository;

    /**
     *
     * @param pago
     * @return
     */
    @GetMapping("/rendimentos")
    public ResponseEntity<?> findRendimentosByAno(
            final @RequestParam(value = "pago", required = false) Boolean pago
    ){
        return ResponseEntity.ok().body(this.rendimentoRepository
                .findRendimentosByAno(pago, LocalDate.now().getYear()));
    }

    /**
     *
     * @param tipoInvestimentoId
     * @return
     */
    @GetMapping("/posicao")
    public ResponseEntity<?> findPosisao(
            final @RequestParam(value = "tipoInvestimento", required = false) Long tipoInvestimentoId
    ){
        return ResponseEntity.ok().body(this.compilacaoDadosPosicaoRepository
                .findByAnoMes(tipoInvestimentoId, 2023,9));
    }
}
