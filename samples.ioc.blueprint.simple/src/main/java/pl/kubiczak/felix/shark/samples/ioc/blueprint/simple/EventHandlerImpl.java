package pl.kubiczak.felix.shark.samples.ioc.blueprint.simple;


import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Sample OSGi events handler.
 */
public class EventHandlerImpl implements EventHandler {

  public static final String TOPIC =
          "pl/kubiczak/felix/shark/samples/ioc/blueprint/simple/EventHandlerImpl/Topic";

  private final Logger log = LoggerFactory.getLogger(getClass());

  private final AtomicLong processedEvents = new AtomicLong();

  private final DateFormatter dateFormatter;

  public EventHandlerImpl(DateFormatter dateFormatter) {
    this.dateFormatter = dateFormatter;
  }

  /**
   * Process passed OSGI events. The events may be filtered during event handler registration.
   * See <code>context-osgi.xml</code> file for this instance registration.
   *
   * @param event passed OSGI event
   */
  public void handleEvent(Event event) {
    long counter = processedEvents.incrementAndGet();
    log.debug("[{}]: current time: '{}', handling event: '{}'",
            counter, dateFormatter.getFormatted(new Date()), event.getTopic());
  }

  public long processedEvents() {
    return processedEvents.get();
  }
}
