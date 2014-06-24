package org.resthub.identity.webapp.elasticsearch;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import java.io.IOException;

@Named("jsonConverter")
public class JsonObjectConverter {

    private Logger logger = LoggerFactory.getLogger(JsonObjectConverter.class);

    private ObjectMapper mapper = new ObjectMapper();

    public <T> T getObjectFromJson(String sourceJson, Class<T> classExpected) {

        logger.debug("Getting Object from Json.");

        T objectResult = null;

        try {
            objectResult = mapper.readValue(sourceJson, classExpected);
        } catch (JsonParseException e) {
            logger.error("Exception : Json Exception", e);
        } catch (JsonMappingException e) {
            logger.error("Exception : Json Exception", e);
        } catch (IOException e) {
            logger.error("Exception : IOException", e);
        }

        return objectResult;
    }

    public <T> String getJsonFromObject(T sourceObject) {

        logger.debug("Getting Json from Object");

        String jsonResult = "";

        try {
            jsonResult = mapper.writeValueAsString(sourceObject);
        } catch (JsonParseException e) {
            logger.error("Exception : Json Exception", e);
        } catch (JsonMappingException e) {
            logger.error("Exception : Json Exception", e);
        } catch (IOException e) {
            logger.error("Exception : IOException", e);
        }

        logger.debug("JSON : " + jsonResult);

        return jsonResult;
    }
}
