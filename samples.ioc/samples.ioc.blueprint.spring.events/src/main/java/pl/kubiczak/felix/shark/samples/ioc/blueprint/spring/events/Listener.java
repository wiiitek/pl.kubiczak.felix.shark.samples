package pl.kubiczak.felix.shark.samples.ioc.blueprint.spring.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import java.util.concurrent.atomic.AtomicLong;

public class Listener implements ApplicationListener {

  private static final AtomicLong processedEvents = new AtomicLong();

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Override
  public void onApplicationEvent(ApplicationEvent event) {
    log.debug("processing application event: '{}'", event.getSource());
    processedEvents.getAndIncrement();
  }

  public long processedEvents() {
    return processedEvents.get();
  }
}
