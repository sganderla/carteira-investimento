package br.com.esaplicacoes.backend.component;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author Eduardo Sganderla
 *
 * @version 1.0.0, 29/07/2023
 * @since 1.0.0
 */
@Component
public class Mensagem {

    @Setter
    @Autowired
    private MessageSource messageSource;

    /**
     *
     * @param mensagem
     * @param parametros
     * @return
     */
    public String texto(String mensagem, Object... parametros){
        return this.messageSource.getMessage(
                mensagem,
                parametros,
                LocaleContextHolder.getLocale());
    }
}
