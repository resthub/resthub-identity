package fr.pilato.spring.elasticsearch.xml;

import org.elasticsearch.client.Client;
import org.fest.assertions.api.Assertions;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ElasticsearchTemplateTest {

	static protected ConfigurableApplicationContext ctx;

	@BeforeClass
	static public void setup() {
		ctx = new ClassPathXmlApplicationContext(
				"fr/pilato/spring/elasticsearch/xml/es-template-test-context.xml");
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
	}

}
