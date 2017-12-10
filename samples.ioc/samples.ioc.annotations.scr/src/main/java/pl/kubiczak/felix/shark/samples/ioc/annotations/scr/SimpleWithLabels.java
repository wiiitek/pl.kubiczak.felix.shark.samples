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
        metatype = true,
        name = "Component SimpleWithLabels Name",
        label = "Component SimpleWithLabels Label",
        description = "Component pl.kubiczak [...] SimpleWithLabels Description"
    )
@Service(SimpleWithLabels.class)
public class SimpleWithLabels {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Property(label = "Name Label")
  private String name;

  /**
   * Called upon service activation / update.
   *
   * @param properties properties of the service
   */
  @Activate
  public void activate(Map<String, Object> properties) {
    name = (String) properties.get("name");
    log.debug("activating for name: '{}'", name);
  }

  /**
   * Called upon service deactivation / before update.
   *
   * @param properties properties of the service
   */
  @Deactivate
  public void deactivate(Map<String, Object> properties) {
    log.debug("deactivating for name: '{}', property value: '{}'", name, properties.get("name"));
    name = null;
  }
}
