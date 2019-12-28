package pl.kubiczak.felix.shark.samples.tests.integration.ioc.blueprint;

import static com.jayway.awaitility.Awaitility.await;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;
import static org.ops4j.pax.exam.CoreOptions.junitBundles;
import static org.ops4j.pax.exam.CoreOptions.maven;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.wrappedBundle;

import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.options.MavenArtifactUrlReference;
import org.ops4j.pax.exam.options.WrappedUrlProvisionOption;
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
import pl.kubiczak.felix.shark.samples.tests.integration.ioc.LoggingOptions;


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
    final long before = ((EventHandlerImpl) eventHandler).processedEvents();
    log.debug("sending asynchronous event..");
    eventAdmin.postEvent(new Event(EventHandlerImpl.TOPIC, Collections.EMPTY_MAP));

    await().atMost(20, TimeUnit.SECONDS).until(new Callable<Boolean>() {
      public Boolean call() throws Exception {
        long processedEvents = ((EventHandlerImpl) eventHandler).processedEvents();
        return processedEvents > before;
      }
    });

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

    MavenArtifactUrlReference awaitility = maven("com.jayway.awaitility", "awaitility")
        .versionAsInProject();
    WrappedUrlProvisionOption awaitilityOption = wrappedBundle(awaitility);

    return new Option[]{
        junitBundles(),

        LoggingOptions.logbackBundlesAndConfiguration(),
        Options.springAndGeminiBlueprint(), awaitilityOption,

        // bundles for tests
        mavenBundle("org.apache.felix", "org.apache.felix.eventadmin").versionAsInProject(),
        mavenBundle("pl.kubiczak.felix.shark", "samples.ioc.blueprint.simple")
            .versionAsInProject()
    };
  }
}
