package org.resthub.identity.elasticsearch;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author JRO <jeremie.romuald@atos.net>
 * Date : 21/08/2012 
 */
public class Indexer {

	private static final Logger LOGGER = LoggerFactory.getLogger(Indexer.class);
		
	/**
	 * This method is used to add an object to the specified index
	 * @param sourceObject The object to index
	 * @param indexName The name of the index to request
	 * @param indexType The type of the index to request
	 * @param indexId The id of the object in the index
	 * @return The id of the object in the index
	 */
	public static <T> String add(Client client, T sourceObject, String indexName, String indexType, String indexId){
	    		
		LOGGER.debug("Indexing the object : " + sourceObject.getClass().getName() + " with id " + indexId);
		
		String jsonSource = JsonObjectConverter.getJsonFromObject(sourceObject);

		IndexResponse indexResponse = client.prepareIndex(indexName, indexType, indexId)
				.setRefresh(true)
				.setSource(jsonSource)
				.execute()
				.actionGet();

		LOGGER.debug("Object indexed under " + indexName + "." + indexType + " as id " + indexResponse.getId());
		
		
		return indexResponse.getId();
	}
	
	/**
	 * This method is used to remove an object from the specified index
	 * @param indexName The name of the index to request
	 * @param indexType The type of the index to request
	 * @param indexId The id of the object to remove from the index
	 */
	public static void delete(Client client, String indexName, String indexType, String indexId){
	    
		LOGGER.debug("Removing the object with id " + indexId + " from the index");
		
		DeleteResponse deleteResponse = client.prepareDelete(indexName, indexType, indexId)
				.setRefresh(true)
				.execute()
				.actionGet();
		
		LOGGER.debug("Object removed from " + indexName + "." + indexType + " with id " + deleteResponse.getId());

	}
	
	/**
	 * This method is used to edit an object in the specified index
	 * @param sourceObject The object to index
	 * @param indexName The name of the index to request
	 * @param indexType The type of the index to request
	 * @param indexId The id of the object to re-index
	 * @return The id of the object in the index
	 */
	public static <T> String edit(Client client, T sourceObject, String indexName, String indexType, String indexId){

	    
		LOGGER.debug("Editing the indexed object : " + sourceObject.getClass().getName() + " with id " + indexId);
		
		String jsonSource = JsonObjectConverter.getJsonFromObject(sourceObject);

		IndexResponse indexResponse = client.prepareIndex(indexName, indexType, indexId)
				.setRefresh(true)
				.setSource(jsonSource)
				.execute()
				.actionGet();

		LOGGER.debug("Object re-indexed under " + indexName + "." + indexType + " as id " + indexResponse.getId());
		
		return indexResponse.getId();
	}
}
