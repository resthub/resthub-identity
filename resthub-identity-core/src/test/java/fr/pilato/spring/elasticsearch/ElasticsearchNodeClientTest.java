package fr.pilato.spring.elasticsearch;

import org.elasticsearch.client.Client;
import org.fest.assertions.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.Test;

public class ElasticsearchNodeClientTest extends AbstractESTest {
	@Autowired Client client;
	
	@Test
	public void testStart() {
		Assertions.assertThat(client).as("Client must not be null...").isNotNull();	
	}
}
