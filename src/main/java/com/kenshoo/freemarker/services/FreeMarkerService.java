package com.kenshoo.freemarker.services;

import java.io.IOException;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kenshoo.freemarker.util.LengthLimitedWriter;
import com.kenshoo.freemarker.util.LengthLimitExceededException;

import freemarker.core.TemplateClassResolver;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

/**
 * Created with IntelliJ IDEA.
 * User: nir
 * Date: 4/12/14
 * Time: 10:15 AM
 */
@Service
public class FreeMarkerService {

    private static final int DEFAULT_OUTPUT_LENGTH_LIMIT = 100000;
    
    private static final String OUTPUT_LENGTH_LIMIT_EXCEEDED_TERMINATION = "\n----------\n"
            + "Aborted template processing, as the output length has exceeded the {0} character limit set for "
            + "this service.";
    
    private static final String ERROR_IN_TEMPLATE_PARSING = "Error in template parsing";
    private static final String ERROR_IN_TEMPLATE_EVALUATION = "Error in template evaluation";
    
    private static final Logger logger = LoggerFactory.getLogger(FreeMarkerService.class);

    private final Configuration freeMarkerConfig;
    
    private int outputLengthLimit = DEFAULT_OUTPUT_LENGTH_LIMIT;
    
    public FreeMarkerService() {
        freeMarkerConfig = new Configuration(Configuration.getVersion());
        freeMarkerConfig.setNewBuiltinClassResolver(TemplateClassResolver.ALLOWS_NOTHING_RESOLVER);
        freeMarkerConfig.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        freeMarkerConfig.setLocale(Locale.US);
        freeMarkerConfig.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
        freeMarkerConfig.setOutputEncoding("UTF-8");
    }
    
    public FreeMarkerServiceResponse calculateFreeMarkerTemplate(String templateText, Map<String, String> params) {
        Template template;
        try {
            template = new Template(null, templateText, freeMarkerConfig);
        } catch (IOException e) {
            return createExceptionalResponse(e, ERROR_IN_TEMPLATE_PARSING);
        }
        
        boolean resultTruncated = false;
        StringWriter writer = new StringWriter();
        try {
            template.process(params, new LengthLimitedWriter(writer, outputLengthLimit));
        } catch (LengthLimitExceededException e) {
            writer.write(new MessageFormat(OUTPUT_LENGTH_LIMIT_EXCEEDED_TERMINATION, Locale.US)
                    .format(new Object[] { outputLengthLimit }));
            resultTruncated = true;
            // Falls through
        } catch (TemplateException e) {
            return createExceptionalResponse(e, ERROR_IN_TEMPLATE_EVALUATION);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        return new FreeMarkerServiceResponse.Builder().successfulResponse(writer.toString(), resultTruncated);
    }
    
    public int getOutputLengthLimit() {
        return outputLengthLimit;
    }

    public void setOutputLengthLimit(int outputLengthLimit) {
        this.outputLengthLimit = outputLengthLimit;
    }
    
    private FreeMarkerServiceResponse createExceptionalResponse(Exception e, String msg) {
        logger.info(msg);
        logger.debug(msg, e);
        return new FreeMarkerServiceResponse.Builder().errorResponse(e.getMessage());
    }

}
