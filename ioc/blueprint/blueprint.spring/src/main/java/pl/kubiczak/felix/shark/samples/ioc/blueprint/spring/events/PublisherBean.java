package pl.kubiczak.felix.shark.samples.ioc.blueprint.spring.events;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

public class PublisherBean implements ApplicationEventPublisherAware {

	private ApplicationEventPublisher applicationEventPublisher;

	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

	public void publishEvent() {
		this.applicationEventPublisher.publishEvent(new Event(this));
	}

	private static final class Event extends ApplicationEvent {
		/**
		 * Create a new ApplicationEvent.
		 *
		 * @param source the object on which the event initially occurred (never {@code null})
		 */
		public Event(Object source) {
			super(source);
		}
	}
}
