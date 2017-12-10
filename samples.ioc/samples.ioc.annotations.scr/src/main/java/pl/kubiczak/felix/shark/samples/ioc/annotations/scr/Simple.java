package pl.kubiczak.felix.shark.samples.ioc.annotations.scr;

import java.util.Map;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
    (
        immediate = true,
        metatype = true
    )
@Service(Simple.class)
public class Simple {

  // see also: scr-annotations: naming-the-property
  @Property(label = "Name Label")
  static final String PROP_NAME = "prop.name";

  private final Logger log = LoggerFactory.getLogger(getClass());

  private String name;

  /**
   * Called upon service activation / update.
   *
   * @param properties properties of the service
   */
  @Activate
  public void activate(Map<String, Object> properties) {
    name = (String) properties.get(PROP_NAME);
    log.debug("activating for name: '{}'", name);
  }

  /**
   * Called upon service deactivation / before update.
   */
  @Deactivate
  public void deactivate() {
    log.debug("deactivating");
    name = null;
  }

  public String getName() {
    return name;
  }
}
