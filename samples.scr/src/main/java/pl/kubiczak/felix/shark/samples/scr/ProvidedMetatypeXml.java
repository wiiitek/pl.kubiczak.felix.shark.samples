package pl.kubiczak.felix.shark.samples.scr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * XML file with metatype information is provided for this service.
 */
public class ProvidedMetatypeXml {

  private final Logger log = LoggerFactory.getLogger(getClass());

  private String name;

  private Integer number;

  private String[] multiple;

  /**
   * Called upon service activation / update.
   *
   * @param properties properties of the service
   */
  public void activate(Map<String, Object> properties) {
    this.name = (String) properties.get("name");
    this.number = (Integer) properties.get("number");
    this.multiple = (String[]) properties.get("multiple");
    log.info("activating for name: '{}', number: '{}', multiple: '{}'", name, number, multiple);
  }

  /**
   * Called upon service deactivation / before update.
   */
  public void deactivate() {
    log.info("deactivating");
    this.name = null;
    this.number = null;
    this.multiple = new String[0];
  }
}
