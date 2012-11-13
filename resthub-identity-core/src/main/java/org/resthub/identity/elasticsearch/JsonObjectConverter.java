package org.resthub.identity.elasticsearch;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonObjectConverter {

	private static final Logger LOGGER = LoggerFactory.getLogger(JsonObjectConverter.class);

	private static ObjectMapper mapper = new ObjectMapper();

	public static <T> T getObjectFromJson(String sourceJson, Class<T> classExpected){

		LOGGER.debug("Getting Object from Json.");

		T objectResult = null;

		try {
			objectResult = mapper.readValue(sourceJson, classExpected);
		} catch (JsonParseException | JsonMappingException e) {
			LOGGER.error("Exception : Json Exception", e);
		} catch (IOException e) {
			LOGGER.error("Exception : IOException", e);
		}

		return objectResult;
	}

	public static <T> String getJsonFromObject(T sourceObject){

		LOGGER.debug("Getting Json from Object");

		String jsonResult = "";

		try {
			jsonResult = mapper.writeValueAsString(sourceObject);
		} catch (JsonParseException | JsonMappingException e) {
			LOGGER.error("Exception : Json Exception", e);
		} catch (IOException e) {
			LOGGER.error("Exception : IOException", e);
		}

		LOGGER.debug("JSON : " + jsonResult);
		
		return jsonResult;
	}
}
