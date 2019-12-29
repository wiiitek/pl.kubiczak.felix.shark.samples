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

  private HttpService httpService;

  /**
   * Bind http service into this component.
   * 
   * @param httpService dynamically provided HTTP service
   */
  @Reference(policy = ReferencePolicy.DYNAMIC)
  public void bindHttpService(HttpService httpService) {
    log.debug("Binding HTTP service: '{}'", httpService);
    registerResources(httpService);
    this.httpService = httpService;
  }

  /**
   * Unbind http service into this component.
   *
   * @param httpService dynamically provided HTTP service
   */
  public void unbindHttpService(HttpService httpService) {
    log.debug("Unbinding HTTP service: '{}'", httpService);
    unregisterResources(this.httpService);
    this.httpService = httpService;
  }

  /**
   * Activation method for a component.
   */
  @Activate
  public void start() {
    if (httpService != null) {
      log.info("Component activation: HTTP service is present");
    } else {
      log.info("Component activation: HTTP service not available");
    }
  }

  /**
   * Deactivation method for a component.
   */
  @Deactivate
  public void stop() {
    log.info("Component deactivation. HTTP service: '{}'", httpService);
    unregisterResources(this.httpService);
  }

  private void registerResources(HttpService httpService) {
    if (httpService != null) {
      try {
        httpService.registerResources(WEB_PATH, CONTENT, null);
        log.debug("Resources registered");
      } catch (NamespaceException ne) {
        log.warn("Failed to register resources", ne);
      }
    }
  }

  private void unregisterResources(HttpService httpService) {
    if (httpService != null) {
      httpService.unregister(WEB_PATH);
      log.debug("Resources unregistered");
    }
  }
}
