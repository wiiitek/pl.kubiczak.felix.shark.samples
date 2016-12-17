package pl.kubiczak.felix.shark.samples.http.resources.amdatu;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.http.HttpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This configures Amdatu resource register.
 */
@Component
public class AmdatuResourceRegisterConfiguration {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Reference(policy = ReferencePolicy.DYNAMIC)
  private volatile HttpService httpService;

  /**
   * Registers resources within OSGI HTTP service.
   */
  @Activate
  public void start() {
    log.debug("Starting configuration component...");
  }

  @Deactivate
  public void stop() {
    log.debug("Stopping configuration component...");
  }
}
