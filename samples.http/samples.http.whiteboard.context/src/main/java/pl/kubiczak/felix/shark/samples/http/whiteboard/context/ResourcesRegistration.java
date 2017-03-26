package pl.kubiczak.felix.shark.samples.http.whiteboard.context;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
        (
                service = ResourcesRegistration.class,
                immediate = true
        )
public class ResourcesRegistration {

  private static final String PATH = "/samples.http.whiteboard.context/resources";

  private static final String CONTENT = "/content/html";

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Reference
  private SampleContext context;

  @Reference
  private HttpService httpService;

  /**
   * Registers resources within OSGI HTTP service.
   */
  @Activate
  public void start() {
    try {
      httpService.registerResources(PATH, CONTENT, context);
      log.debug("Resources registered");
    } catch (NamespaceException ne) {
      log.warn("Failed to register resources", ne);
    }
  }

  @Deactivate
  public void stop() {
    httpService.unregister(PATH);
  }
}
