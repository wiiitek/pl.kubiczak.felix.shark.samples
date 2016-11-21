package pl.kubiczak.felix.shark.samples.ioc.blueprint.spring.messages;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;

public class Bean implements MessageSourceAware {

  private MessageSource messageSource;

  @Override
  public void setMessageSource(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  public MessageSource getMessageSource() {
    return messageSource;
  }
}
