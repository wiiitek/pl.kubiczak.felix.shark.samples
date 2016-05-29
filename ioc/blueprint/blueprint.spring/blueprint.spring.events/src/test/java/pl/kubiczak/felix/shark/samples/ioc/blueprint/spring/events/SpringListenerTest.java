package pl.kubiczak.felix.shark.samples.ioc.blueprint.spring.events;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:OSGI-INF/blueprint/spring-context.xml")
public class SpringListenerTest {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	public void springApplicationEventsShouldBeProcessed() {
		Publisher publisher = applicationContext.getBean("publisherId", Publisher.class);
		Listener listener = applicationContext.getBean("listenerId", Listener.class);

		long before = listener.processedEvents();
		publisher.publishEvent();
		// TODO: spring events are asynchronous, test should be refactored for that
		long after = listener.processedEvents();

		assertThat(after, is(before + 1));
	}

}