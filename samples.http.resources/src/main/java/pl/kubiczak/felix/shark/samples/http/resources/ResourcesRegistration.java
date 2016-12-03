package pl.kubiczak.felix.shark.samples.http.resources;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ResourcesRegistration {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Reference(policy = ReferencePolicy.DYNAMIC)
  private volatile HttpService httpService;

  /**
   * Registers resources within OSGI HTTP service.
   */
  @Activate
  public void start() {
    try {
      httpService.registerResources("/samples.http.resources", "/content", null);
      httpService.registerResources("/samples.http.resources/img", "/content/img", null);
      log.debug("Resources registered");
    } catch (NamespaceException ne) {
      log.warn("Failed to register resources", ne);
    }
  }

  @Deactivate
  public void stop() {
    httpService.unregister("/samples.http.resources");
    httpService.unregister("/samples.http.resources/img");
  }
}
