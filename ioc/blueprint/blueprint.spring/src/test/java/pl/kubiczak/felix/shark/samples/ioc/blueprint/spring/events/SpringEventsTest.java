package pl.kubiczak.felix.shark.samples.ioc.blueprint.spring.events;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/test-spring-context.xml")
public class SpringEventsTest {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	public void springApplicationEventsShouldBeProcessed() {
		PublisherBean publisherBean = applicationContext.getBean("publisherBean", PublisherBean.class);
		ListenerBean listenerBean = applicationContext.getBean("listenerBean", ListenerBean.class);

		long before = listenerBean.processedEvents();
		publisherBean.publishEvent();
		long after = listenerBean.processedEvents();

		assertThat(after, is(before + 1));
	}

}