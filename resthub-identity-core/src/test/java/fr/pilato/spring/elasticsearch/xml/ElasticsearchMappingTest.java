package fr.pilato.spring.elasticsearch.xml;

import org.elasticsearch.client.Client;
import org.fest.assertions.api.Assertions;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ElasticsearchMappingTest {
	static protected ConfigurableApplicationContext ctx;
	
	@BeforeClass
	static public void setup() {
		ctx = new ClassPathXmlApplicationContext("fr/pilato/spring/elasticsearch/xml/es-mapping-test-context.xml");
	}
	
	@AfterClass
	static public void tearDown() {
		if (ctx != null) {
			ctx.close();
		}
	}
	
	@Test
	public void test_transport_client() {
		Client client = ctx.getBean("esClient", Client.class);
		Assertions.assertThat(client).as("Client must not be null...").isNotNull();	

		Client client2 = ctx.getBean("esClient2", Client.class);
		Assertions.assertThat(client2).as("Client2 must not be null...").isNotNull();	
		// We wait a while for connection to the cluster (1s should be enough)
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		
	}
}
