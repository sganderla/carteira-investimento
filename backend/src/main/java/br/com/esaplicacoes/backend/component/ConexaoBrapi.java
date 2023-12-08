package br.com.esaplicacoes.backend.component;

import br.com.esaplicacoes.backend.entity.brapi.Results;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author Eduardo Sganderla
 *
 * @version 1.0.0, 07/09/2023
 * @since 1.0.0
 */

@Component
public class ConexaoBrapi {

    public Results buscarCotacaoAtivo(final String ativo)  {

        final RestTemplate restTemplate = new RestTemplate();

        final ResponseEntity<Results> response = restTemplate.getForEntity(
                "https://brapi.dev/api/quote/"+ativo+"?token=5PCkhdyVPXayF9WzJmP6Qh", Results.class);

        return response.getBody();
    }
}
