package com.kenshoo.freemarker.services;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import freemarker.core.TemplateClassResolver;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Created with IntelliJ IDEA.
 * User: nir
 * Date: 4/12/14
 * Time: 10:15 AM
 */
@Service
public class FreeMarkerService {

    private static final String ERROR_IN_TEMPLATE_PARSING = "Error in template parsing";
    private static final String ERROR_IN_TEMPLATE_EVALUATION = "Error in template evaluation";
    private final Logger logger = LoggerFactory.getLogger(FreeMarkerService.class);

    private final Configuration freeMarkerConfig;
    
    public FreeMarkerService() {
        freeMarkerConfig = new Configuration(Configuration.getVersion());
        freeMarkerConfig.setNewBuiltinClassResolver(TemplateClassResolver.ALLOWS_NOTHING_RESOLVER);
    }
    
    public FreeMarkerServiceResponse calculateFreeMarkerTemplate(String templateText, Map<String, String> params) {
        StringWriter writer = new StringWriter();
        Template template;

        try {
            template = new Template(null, templateText, freeMarkerConfig);
        } catch (IOException e) {
            return createExceptionalResponse(e, ERROR_IN_TEMPLATE_PARSING);
        }
        try {
            template.process(params, writer);
        } catch (TemplateException e) {
            return createExceptionalResponse(e, ERROR_IN_TEMPLATE_EVALUATION);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String result = writer.toString();
        return new FreeMarkerServiceResponse.Builder().successfulResponse(result);
    }

    private FreeMarkerServiceResponse createExceptionalResponse(Exception e, String msg) {
        logger.info(msg);
        logger.debug(msg, e);
        return new FreeMarkerServiceResponse.Builder().errorResponse(e.getMessage());
    }

}
