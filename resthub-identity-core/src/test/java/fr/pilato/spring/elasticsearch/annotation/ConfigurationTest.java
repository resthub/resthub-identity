package fr.pilato.spring.elasticsearch.annotation;

import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;
import org.fest.assertions.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.junit.Test;

@ContextConfiguration(locations = {
		"classpath:annotation-context.xml"
		})
public class ConfigurationTest {
	
	@Autowired Node node;

	@Autowired Client client;

	@Test
	public void testNode() {
		Assertions.assertThat(node).as("Node must not be null...").isNotNull();	
		Assertions.assertThat(client).as("Client must not be null...").isNotNull();	
	}
	
}
