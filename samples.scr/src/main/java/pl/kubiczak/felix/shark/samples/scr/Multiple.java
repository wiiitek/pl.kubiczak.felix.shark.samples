package pl.kubiczak.felix.shark.samples.scr;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.PropertyUnbounded;
import org.apache.felix.scr.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Component
        (
                immediate = true,
                metatype = true
        )
@Service(Multiple.class)
public class Multiple {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Property(label = "Colors Label", cardinality = Integer.MAX_VALUE)
  private String[] colors;

  @Property(label = "Names Label", unbounded = PropertyUnbounded.ARRAY)
  static final String NAMES_PROPERTY = "key.for.names.property";

  private String[] names;

  /**
   * Called upon service activation / update.
   *
   * @param properties properties of the service
   */
  @Activate
  public void activate(Map<String, Object> properties) {
    colors = (String[]) properties.get("colors");
    names = (String[]) properties.get(NAMES_PROPERTY);
    log.debug("activating for colors: '{}' and names: '{}'", colors, names);
  }

  /**
   * Called upon service deactivation / before update.
   */
  @Deactivate
  public void deactivate(Map<String, Object> properties) {
    log.debug("deactivating");
    colors = null;
    names = null;
  }
}
