package pl.kubiczak.felix.shark.samples.ioc.annotations.scr;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.PropertyOption;
import org.apache.felix.scr.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Component
        (
                immediate = true,
                metatype = true
        )
@Service(Options.class)
public class Options {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Property
          (
                  label = "Name Label",
                  options = {
                          @PropertyOption(name = "james.bond", value = "James Bond"),
                          @PropertyOption(name = "john.doe", value = "John Doe")
                  }
          )
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
   */
  @Deactivate
  public void deactivate() {
    log.debug("deactivating");
    name = null;
  }
}
