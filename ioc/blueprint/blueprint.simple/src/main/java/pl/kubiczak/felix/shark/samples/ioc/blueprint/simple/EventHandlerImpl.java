package pl.kubiczak.felix.shark.samples.ioc.blueprint.simple;


import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Sample OSGi events handler.
 */
public class EventHandlerImpl implements EventHandler {

	public static final String TOPIC = "pl/kubiczak/felix/shark/samples/ioc/blueprint/simple/EventHandlerImpl/Topic";

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final AtomicLong processedEvents = new AtomicLong();

	private final DateFormatter dateFormatter;

	public EventHandlerImpl(DateFormatter dateFormatter) {
		this.dateFormatter = dateFormatter;
	}

	public void handleEvent(Event event) {
		long counter = processedEvents.incrementAndGet();
		log.debug("[{}]: current time: '{}', handling event: '{}'",
				counter, dateFormatter.getFormatted(new Date()), event.getTopic());
	}

	public long processedEvents() {
		return processedEvents.get();
	}
}
