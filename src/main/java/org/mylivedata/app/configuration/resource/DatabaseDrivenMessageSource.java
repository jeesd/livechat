package org.mylivedata.app.configuration.resource;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.mylivedata.app.dashboard.domain.MessageResourceEntity;
import org.mylivedata.app.dashboard.repository.service.LayoutService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

/**
 * Created by lubo08 on 4.3.2015.
 */
public class DatabaseDrivenMessageSource extends AbstractMessageSource implements ResourceLoaderAware {

    private final Logger LOGGER = LoggerFactory.getLogger(DatabaseDrivenMessageSource.class);
    public static final String DEFAULT_LANGUAGE = "en_GB";

    private ResourceLoader resourceLoader;

    private final Map<String, Map<String, String>> properties = new HashMap<>();

    @Autowired
    private LayoutService layoutService;

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        String msg = getText(code, locale);
        MessageFormat result = createMessageFormat(msg, locale);
        return result;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = (resourceLoader != null ? resourceLoader : new DefaultResourceLoader());
    }

    private String getText(String code, Locale locale) {

        if(properties.isEmpty()){
            this.reload();
        }

        Map<String, String> localized = properties.get(locale.getLanguage()+"_"+locale.getCountry());
        String textForCurrentLanguage = null;


        if (localized != null) {
            textForCurrentLanguage = localized.get(code);
            if (textForCurrentLanguage == null) {
                textForCurrentLanguage = properties.get(DEFAULT_LANGUAGE).get(code);
            }
        } else {
            textForCurrentLanguage = properties.get(DEFAULT_LANGUAGE).get(code);
        }
        if (textForCurrentLanguage==null) {
            //Check parent message
            LOGGER.debug("Fallback to properties message");
            try {
                textForCurrentLanguage = getParentMessageSource().getMessage(code, null, locale);
            } catch (Exception e) {
                LOGGER.error("Cannot find message with code: " + code);
            }
        }
        return textForCurrentLanguage != null ? textForCurrentLanguage : code;
    }


    public void reload() {
        properties.clear();
        properties.putAll(loadTexts());
    }

    protected Map<String, Map<String, String>> loadTexts() {
        LOGGER.debug("loadTexts");
        Map<String, Map<String, String>> messageByLang = new HashMap<>();
        List<MessageResourceEntity> texts = null;
        try {
            texts = layoutService.getMessageResource();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        assert texts!=null;
        for (MessageResourceEntity text : texts) {
            if(messageByLang.containsKey(text.getLangIsoCode()+"_"+text.getCoutryIsoCode())){
                ((Map)messageByLang.get(text.getLangIsoCode()+"_"+text.getCoutryIsoCode())).put(text.getTextCode(),text.getTextValue());
            }else{
                Map<String, String> messageByKey = new HashMap<>();
                messageByKey.put(text.getTextCode(),text.getTextValue());
                messageByLang.put(text.getLangIsoCode()+"_"+text.getCoutryIsoCode(),messageByKey);
            }
        }
        return messageByLang;
    }

}
