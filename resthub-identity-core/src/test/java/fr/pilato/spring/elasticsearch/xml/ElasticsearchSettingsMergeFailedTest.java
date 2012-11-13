package fr.pilato.spring.elasticsearch.xml;

import org.elasticsearch.ElasticSearchIllegalArgumentException;
import org.fest.assertions.api.Assertions;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;


/**
 * We try to merge non merging settings.
 * An exception should be raised.
 * @author David Pilato aka dadoonet
 *
 */
public class ElasticsearchSettingsMergeFailedTest {
	static protected ConfigurableApplicationContext ctx;
	
	@Test(expected=BeanCreationException.class)
	public void test_merge_settings_failure() {
		try {
			new ClassPathXmlApplicationContext("fr/pilato/spring/elasticsearch/xml/es-settings-failed-test-context.xml");
		} catch (BeanCreationException e) {
			Assertions.assertThat(e.getCause().getClass()).as("BeanCreationException causes is not an instance of ElasticSearchIllegalArgumentException...").isExactlyInstanceOf(ElasticSearchIllegalArgumentException.class);
			throw e;
		}
	}
}
