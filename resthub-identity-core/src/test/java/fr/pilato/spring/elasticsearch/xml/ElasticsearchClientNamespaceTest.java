package fr.pilato.spring.elasticsearch.xml;

import org.elasticsearch.client.Client;
import org.fest.assertions.api.Assertions;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


public class ElasticsearchClientNamespaceTest {
	static protected ConfigurableApplicationContext ctx;
	
	@BeforeClass
	static public void setup() {
		ctx = new ClassPathXmlApplicationContext("fr/pilato/spring/elasticsearch/xml/es-client-namespace-test-context.xml");
	}
	
	@AfterClass
	static public void tearDown() {
		if (ctx != null) {
			ctx.close();
		}
	}
	
	@Test
	public void test_node_client() {
		Client client = ctx.getBean("testNodeClient", Client.class);
		Assertions.assertThat(client).as("Client must not be null...").isNotNull();	
		Assertions.assertThat(client instanceof org.elasticsearch.client.node.NodeClient).as("Client should be an instance of org.elasticsearch.client.node.NodeClient.").isTrue();	
	}
}
