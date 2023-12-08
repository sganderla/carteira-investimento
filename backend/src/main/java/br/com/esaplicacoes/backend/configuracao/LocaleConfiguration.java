package br.com.esaplicacoes.backend.configuracao;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

/**
 * @author Eduardo Sganderla
 *
 * @version 1.0.0, 29/07/2023
 * @since 1.0.0
 */
@Configuration
public class LocaleConfiguration extends AcceptHeaderLocaleResolver {

    /**
     *
     * @return
     */
    @Bean
    public MessageSource messageSource() {

        Locale.setDefault(new Locale("pt", "BR"));

        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setAlwaysUseMessageFormat(true);
        messageSource.setUseCodeAsDefaultMessage(true);

        messageSource.setBasenames("classpath:i18n/messages");

        return messageSource;
    }
}