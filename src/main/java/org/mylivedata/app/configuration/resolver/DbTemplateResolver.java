package org.mylivedata.app.configuration.resolver;


import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.mylivedata.app.dashboard.repository.service.LayoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.TemplateProcessingParameters;
import org.thymeleaf.resourceresolver.IResourceResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import com.google.common.collect.Sets;

/**
 * Created by lubo08 on 3.3.2015.
 */

public class DbTemplateResolver extends TemplateResolver {

    private final static String PREFIX = "db:";
    private final static String PREFIX_TYPE_HTML = "html:";
    private final static String PREFIX_TYPE_CSS = "css:";
    
    @Autowired
    private LayoutService layoutService;

    public DbTemplateResolver() {
        setResourceResolver(new DbResourceResolver());
        setResolvablePatterns(Sets.newHashSet(PREFIX + "*"));
    }
    
    @Override
    protected String computeResourceName(TemplateProcessingParameters params) {
        String templateName = params.getTemplateName();
        return templateName.substring(PREFIX.length());
    }

    private class DbResourceResolver implements IResourceResolver {

        @Override
        public InputStream getResourceAsStream(TemplateProcessingParameters params, String resourceName) {
            String template = null;//templateRepo.findOne(Long.valueOf(resourceName));
            try {
                if(resourceName.startsWith(PREFIX_TYPE_HTML)) {
                    template = layoutService.getLayoutHtml(resourceName.substring(PREFIX_TYPE_HTML.length()), "bubble");
                } else if(resourceName.startsWith(PREFIX_TYPE_CSS)) {
                    template = layoutService.getLayoutCss(resourceName.substring(PREFIX_TYPE_CSS.length()), "bubble");
                } else {
                    template = layoutService.getLayoutHtml("bubble_style", "bubble");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (template != null) {
                return new ByteArrayInputStream(template.getBytes());
            }
            return null;
        }

        @Override
        public String getName() {
            return "dbResourceResolver";
        }
    }
}
