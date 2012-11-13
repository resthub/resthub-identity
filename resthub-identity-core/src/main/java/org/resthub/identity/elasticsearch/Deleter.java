package org.resthub.identity.elasticsearch;

import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Deleter {

private static final Logger LOGGER = LoggerFactory.getLogger(Indexer.class);
	
public  static void deleteIndex(Client client, String indexName, String indexType){
	
	LOGGER.debug("Delete index : " + indexName + "with type "+ indexType);
	
	client.prepareDeleteByQuery(indexName).
    setQuery(QueryBuilders.matchAllQuery()).
    setTypes(indexType).
    execute().actionGet();

	LOGGER.debug("Deleted index : " + indexName + "with type "+ indexType);
	
}
}