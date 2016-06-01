package pl.kubiczak.felix.shark.samples.ioc.blueprint.spring.events;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

public class Publisher implements ApplicationEventPublisherAware {

	private ApplicationEventPublisher applicationEventPublisher;

	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

	public void publishEvent() {
		this.applicationEventPublisher.publishEvent(new Event(this));
	}
}
