package fr.pilato.spring.elasticsearch.xml;

import org.elasticsearch.index.mapper.MergeMappingException;
import org.fest.assertions.api.Assertions;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;

/**
 * We try to merge non merging mapping.
 * An exception should be raised.
 * @author David Pilato aka dadoonet
 *
 */
public class ElasticsearchMappingMergeFailedTest {
	
	@Test(expected=BeanCreationException.class)
	public void test_transport_client() {
		try {
			new ClassPathXmlApplicationContext("fr/pilato/spring/elasticsearch/xml/es-mapping-failed-test-context.xml");
		} catch (BeanCreationException e) {
			Assertions.assertThat(e.getCause().getClass()).as("BeanCreationException causes must be an instance of MergeMappingException...").isExactlyInstanceOf(MergeMappingException.class);	
			throw e;
		}
	}
}
