package pl.kubiczak.felix.shark.samples.jersey;

import java.util.Dictionary;
import java.util.Hashtable;
import javax.servlet.ServletException;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Registers Jersey Application with this bundle classloader.
 */
@Component
    (
        service = JerseyServletContainerStarter.class,
        immediate = true
    )
public class JerseyServletContainerStarter {

  private static final String JERSEY_APPLICATION_PATH = "/jersey";

  private final Logger log = LoggerFactory.getLogger(this.getClass());

  @Reference
  HttpService httpService;

  /**
   * See also org.glassfish.jersey.examples.osgihttpservice.Activator.
   */
  @Activate
  public void registerJerseyServletContainer() {
    ClassLoader thisBundleClassLoader = getClass().getClassLoader();
    ClassLoader originalContextClassLoader = Thread.currentThread().getContextClassLoader();
    try {
      Thread.currentThread().setContextClassLoader(thisBundleClassLoader);
      try {
        log.debug("current thread class loader: '{}'", thisBundleClassLoader);
        httpService.registerServlet(JERSEY_APPLICATION_PATH, new ServletContainer(),
            populateServletContainerParams(), null);
      } catch (ServletException se) {
        log.error("Error while registering servlet container at '{}'. Already registered?",
            JERSEY_APPLICATION_PATH, se);
      } catch (NamespaceException ne) {
        log.error("Error while registering servlet at '{}'", JERSEY_APPLICATION_PATH, ne);
      }
    } finally {
      Thread.currentThread().setContextClassLoader(originalContextClassLoader);
    }
  }

  /**
   * Deactivates Jersey servlet container when HTTP service is missing.
   */
  @Deactivate
  public void unregisterJerseyServletContainer() {
    httpService.unregister(JERSEY_APPLICATION_PATH);
    httpService = null;
  }

  private Dictionary populateServletContainerParams() {
    Dictionary dict = new Hashtable<>();
    dict.put(ServletProperties.JAXRS_APPLICATION_CLASS, JerseyApplication.class.getName());
    return dict;
  }
}
