package fr.pilato.spring.elasticsearch;

import org.springframework.test.context.ContextConfiguration;


@ContextConfiguration(locations = {
		"classpath:es-context.xml"
		})
public abstract class AbstractESTest {

}
