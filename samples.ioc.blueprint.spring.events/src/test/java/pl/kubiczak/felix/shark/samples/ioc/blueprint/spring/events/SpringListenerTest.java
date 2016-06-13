package pl.kubiczak.felix.shark.samples.ioc.blueprint.spring.events;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static com.jayway.awaitility.Awaitility.await;

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
        await().atMost(5, TimeUnit.SECONDS).until(listenerHasReceivedEvent(before, listener));
    }

    private Callable<Boolean> listenerHasReceivedEvent(final long before, final Listener listener) {
        return new Callable<Boolean>() {
            public Boolean call() throws Exception {
                return listener.processedEvents() > before;
            }
        };
    }

}
