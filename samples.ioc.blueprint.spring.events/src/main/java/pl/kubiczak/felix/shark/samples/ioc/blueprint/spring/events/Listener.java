package pl.kubiczak.felix.shark.samples.ioc.blueprint.spring.events;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class Listener implements ApplicationListener {

    private static final AtomicLong processedEvents = new AtomicLong();

    private final Logger log = LoggerFactory.getLogger(getClass());

    public void onApplicationEvent(ApplicationEvent event) {
        log.debug("processing application event: '{}'", event.getSource());
        processedEvents.getAndIncrement();
    }

    public long processedEvents() {
        return processedEvents.get();
    }
}
