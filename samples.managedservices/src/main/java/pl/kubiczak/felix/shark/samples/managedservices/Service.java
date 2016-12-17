package pl.kubiczak.felix.shark.samples.managedservices;

import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Dictionary;
import java.util.Enumeration;

@Component
        (
                service = {
                        Service.class,
                        ManagedService.class
                }
        )
public class Service implements ManagedService {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Override
  public void updated(Dictionary<String, ?> dictionary) throws ConfigurationException {
    if (log.isDebugEnabled()) {
      log.debug("managed service updated");
      Enumeration<String> keys = dictionary.keys();
      while (keys.hasMoreElements()) {
        String key = keys.nextElement();
        log.debug("configuration entry: [{}]:[{}]", key, dictionary.get(key));
      }
    }
  }
}
