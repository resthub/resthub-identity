package fr.pilato.spring.elasticsearch.xml;

import org.elasticsearch.node.Node;
import org.fest.assertions.api.Assertions;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


public class ElasticsearchNodeNamespaceTest {
	static protected ConfigurableApplicationContext ctx;
	
	@BeforeClass
	static public void setup() {
		ctx = new ClassPathXmlApplicationContext("fr/pilato/spring/elasticsearch/xml/es-namespace-test-context.xml");
	}
	
	@AfterClass
	static public void tearDown() {
		if (ctx != null) {
			ctx.close();
		}
	}
	
	@Test
	public void test_simple_node() {
		Node node = ctx.getBean("testNode", Node.class);
		Assertions.assertThat(node).as("Node must not be null...").isNotNull();
		Assertions.assertThat(node.settings().get("cluster.name")).as("Cluster name is incorrect. Settings are not loaded.").isEqualTo("junit.cluster");	
	}
	
	@Test
	public void test_node_settings() {
		Node node = ctx.getBean("testNodeSettings", Node.class);
		Assertions.assertThat(node).as("Node must not be null...").isNotNull();
		Assertions.assertThat(node.settings().get("cluster.name")).as("Cluster name is incorrect. Settings are not loaded.").isEqualTo("junit.cluster.xml");	
	}
}
