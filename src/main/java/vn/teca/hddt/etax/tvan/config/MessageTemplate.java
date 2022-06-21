package vn.teca.hddt.etax.tvan.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class MessageTemplate {
    @Autowired
    private MessageSource messageSource;

    public String message(String key, String... value) {
        return messageSource.getMessage(key, value, LocaleContextHolder.getLocale());
    }
}
