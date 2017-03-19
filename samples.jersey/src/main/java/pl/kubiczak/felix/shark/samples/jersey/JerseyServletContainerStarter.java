package pl.kubiczak.felix.shark.samples.jersey;

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

import java.util.Dictionary;
import java.util.Hashtable;

import javax.servlet.ServletException;

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
  private transient HttpService httpService;

  /**
   * See also org.glassfish.jersey.examples.osgihttpservice.Activator.
   */
  @Activate
  public void registerJerseyServletContainer() {
    ClassLoader myClassLoader = getClass().getClassLoader();
    ClassLoader originalContextClassLoader = Thread.currentThread().getContextClassLoader();
    try {
      Thread.currentThread().setContextClassLoader(myClassLoader);
      try {

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

  @Deactivate
  public void unregisterJerseyServletContainer() {
    if (httpService != null) {
      httpService.unregister(JERSEY_APPLICATION_PATH);
      httpService = null;
    }
  }

  private Dictionary populateServletContainerParams() {
    Dictionary<String, String> params = new Hashtable<>();
    params.put(ServletProperties.JAXRS_APPLICATION_CLASS, JerseyApplication.class.getName());
    return params;
  }
}
