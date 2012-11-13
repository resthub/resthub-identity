package org.resthub.identity.elasticsearch;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Requester {

	private static final Logger LOGGER = LoggerFactory.getLogger(Indexer.class);

	/**
	 * This method is used to request an index and retrieve anything that
	 * contains 1 or more words contained in params
	 * 
	 * @param client
	 *            The client to request
	 * @param indexName
	 *            The name of the index to request
	 * @param indexType
	 *            The type of the index to request
	 * @param params
	 *            All the keywords to search, separated by a space char, for
	 *            example : "keyword1 keyword2" In that case, the request will
	 *            research everything that contain "keyword1" <b>OR</b>
	 *            "keyword2"
	 * @param classExpected
	 *            The type of object that are expected to be returned by the
	 *            research
	 * @return A list of objects found by the request
	 */
	public static <T> List<T> requestSimpleOr(Client client, String indexName,
			String indexType, String params, Class<T> classExpected) {

		if (params == null) {
			throw new IllegalArgumentException("query must not be null");
		}
		List<T> resultList = new ArrayList<>();

		// Preparing the query
		LOGGER.debug("Rechercher tout ce qui contient : \""
				+ params.replaceAll("\\s", "\" OU \"") + "\"");
		QueryBuilder queryBuilder = QueryBuilders.queryString(params);
		// Executing the request
		SearchResponse searchResponse = client.prepareSearch(indexName)
				.setTypes(indexType).setQuery(queryBuilder).execute()
				.actionGet();

		// Results
		long hits = searchResponse.getHits().getTotalHits();
		LOGGER.debug("Nombre de resultats de la recherche : " + hits + "\n");
		SearchHit hit = null;
		for (int i = 0; i < hits; i++) {
			hit = searchResponse.getHits().getAt(i);
			LOGGER.debug("* Resultat " + (i + 1) + " : " + hit.sourceAsString()
					+ "\n");
			resultList.add(JsonObjectConverter.getObjectFromJson(
					hit.sourceAsString(), classExpected));
		}

		return resultList;
	}

	/**
	 * This method is used to request an index and retrieve anything that
	 * contains all words contained in params
	 * 
	 * @param client
	 *            The client to request
	 * @param indexName
	 *            The name of the index to request
	 * @param indexType
	 *            The type of the index to request
	 * @param params
	 *            All the keywords to search, separated by a space char, for
	 *            example : "keyword1 keyword2" In that case, the request will
	 *            research everything that contain "keyword1" <b>AND</b>
	 *            "keyword2"
	 * @param classExpected
	 *            The type of object that are expected to be returned by the
	 *            research
	 * @return A list of objects found by the request
	 */
	public static <T> List<T> requestSimpleAnd(Client client, String indexName,
			String indexType, String params, Class<T> classExpected) {
		if (params == null) {
			throw new IllegalArgumentException("query must not be null");
		}
		List<T> resultList = new ArrayList<>();

		// Preparing the query
		LOGGER.debug("Rechercher tout ce qui contient : \""
				+ params.replaceAll("\\s", "\" ET \"") + "\"");
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		String[] keywords = params.split("\\s");
		for (String keyword : keywords) {
			queryBuilder.must(QueryBuilders.queryString(keyword));
		}

		// Executing the request
		SearchResponse searchResponse = client.prepareSearch(indexName)
				.setTypes(indexType).execute().actionGet();

		// Results
		long hits = searchResponse.getHits().getTotalHits();
		LOGGER.debug("Nombre de resultats de la recherche : " + hits + "\n");
		SearchHit hit = null;
		for (int i = 0; i < hits; i++) {
			hit = searchResponse.getHits().getAt(i);
			LOGGER.debug("* Resultat " + (i + 1) + " : "
					+ hit.getSourceAsString() + "\n");
			resultList.add(JsonObjectConverter.getObjectFromJson(
					hit.getSourceAsString(), classExpected));
		}

		return resultList;
	}
}
