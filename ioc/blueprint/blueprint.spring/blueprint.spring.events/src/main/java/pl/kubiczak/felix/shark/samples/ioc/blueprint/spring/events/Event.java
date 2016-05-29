package pl.kubiczak.felix.shark.samples.ioc.blueprint.spring.events;

import org.springframework.context.ApplicationEvent;

class Event extends ApplicationEvent {

	/**
	 * Create a new ApplicationEvent.
	 *
	 * @param source the object on which the event initially occurred (never {@code null})
	 */
	public Event(Object source) {
		super(source);
	}
}
