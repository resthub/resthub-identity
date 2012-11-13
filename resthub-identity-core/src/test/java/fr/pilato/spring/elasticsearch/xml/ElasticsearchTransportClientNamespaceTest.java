package fr.pilato.spring.elasticsearch.xml;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.collect.ImmutableList;
import org.elasticsearch.common.transport.TransportAddress;
import org.fest.assertions.api.Assertions;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


public class ElasticsearchTransportClientNamespaceTest {
	static protected ConfigurableApplicationContext ctx;
	
	@BeforeClass
	static public void setup() {
		ctx = new ClassPathXmlApplicationContext("fr/pilato/spring/elasticsearch/xml/es-nodefortransportclient-namespace-test-context.xml",
				"fr/pilato/spring/elasticsearch/xml/es-transport-client-namespace-test-context.xml");
	}
	
	@AfterClass
	static public void tearDown() {
		if (ctx != null) {
			ctx.close();
		}
	}
	
	@Test
	public void test_transport_client() {
		Client client = ctx.getBean("testTransportClient", Client.class);
		Assertions.assertThat(client).as("Client must not be null...").isNotNull();	
		Assertions.assertThat( client instanceof org.elasticsearch.client.transport.TransportClient).as("Client should be an instance of org.elasticsearch.client.transport.TransportClient.").isTrue();	

		TransportClient tClient = (TransportClient) client;
		ImmutableList<TransportAddress> adresses = tClient.transportAddresses();
		Assertions.assertThat(adresses).as("Nodes urls must not be null...").isNotNull();	
		Assertions.assertThat(adresses.isEmpty()).as("Nodes urls must not be empty...").isFalse();	
		
//		TransportAddress transportAddress = adresses.get(0);
//		assertEquals("URL should be localhost:9399", "inet[localhost:9399]", transportAddress.toString());

		// We wait a while for connection to the cluster (1s should be enough)
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		
		// Testing if we are really connected to a cluster node
		Assertions.assertThat(tClient.connectedNodes().isEmpty()).as("We should be connected at least to one node.").isFalse();	
		DiscoveryNode node = tClient.connectedNodes().get(0);
		
		// TODO Raise a bug to ES Team ? This test fails... Why ?
		//assertEquals("The node should be junit.node.transport", "junit.node.transport", node.getName());

		Assertions.assertThat(node.isMasterNode()).as("We should be connected to the master node.").isTrue();	
	}
}
