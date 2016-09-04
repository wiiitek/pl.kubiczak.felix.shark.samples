package pl.kubiczak.felix.shark.samples.ioc.blueprint.it;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;
import static org.ops4j.pax.exam.CoreOptions.junitBundles;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;
import org.ops4j.pax.exam.util.Filter;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.kubiczak.felix.shark.samples.ioc.blueprint.simple.EventHandlerImpl;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;


@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class BlueprintSimpleTest {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Inject
  private EventAdmin eventAdmin;

  /**
   * EventHandler implementation from blueprint.simple bundle
   */
  @Inject
  @Filter("(" + EventConstants.EVENT_TOPIC + "=" + EventHandlerImpl.TOPIC + ")")
  private EventHandler eventHandler;

  @Test
  public void eventAdminServiceShouldBeAvailable() {
    assertThat(eventAdmin, is(not(nullValue())));
  }

  @Test
  public void eventHandlerServiceShouldBeAvailable() {
    assertThat(eventHandler, is(not(nullValue())));
  }

  @Test
  public void filterShouldWorkCorrectly() {
    assertThat(eventHandler, instanceOf(EventHandlerImpl.class));
  }

  @Test
  public void eventHandlerShouldProcessOsgiEvent() {
    long before = ((EventHandlerImpl) eventHandler).processedEvents();
    log.debug("sending synchronous event..");
    Map<String, Object> eventProperties = new HashMap<String, Object>();
    eventAdmin.sendEvent(new Event(EventHandlerImpl.TOPIC, eventProperties));
    long after = ((EventHandlerImpl) eventHandler).processedEvents();
    assertThat(after, is(before + 1));
  }

  /**
   * Provides configuration for Pax Exam container.
   * Contains bundles for logging, blueprint, spring and tested bundles
   *
   * @return configuration options for Pax Exam
   */
  @Configuration
  public Option[] provideRequiredBundles() {
    return new Option[]{
            junitBundles(),

            Options.logbackBundlesAndConfiguration(),
            Options.springAndGeminiBlueprint(),

            // bundles for tests
            mavenBundle("org.apache.felix", "org.apache.felix.eventadmin").versionAsInProject(),
            mavenBundle("pl.kubiczak.felix.shark", "samples.ioc.blueprint.simple")
                    .versionAsInProject()
    };
  }
}
