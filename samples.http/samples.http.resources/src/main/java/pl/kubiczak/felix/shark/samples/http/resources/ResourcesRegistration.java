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
    (
        immediate = true
    )
public class ResourcesRegistration {

  private static final String WEB_PATH = "/samples.http.resources";

  private static final String CONTENT = "/content/html";

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Reference(policy = ReferencePolicy.DYNAMIC)
  private HttpService httpService;

  /**
   * Registers resources within OSGI HTTP service.
   */
  @Activate
  public void start() {
    try {
      httpService.registerResources(WEB_PATH, CONTENT, null);
      log.debug("Resources registered");
    } catch (NamespaceException ne) {
      log.warn("Failed to register resources", ne);
    }
  }

  @Deactivate
  public void stop() {
    httpService.unregister(WEB_PATH);
  }
}
