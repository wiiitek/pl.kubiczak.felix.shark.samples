package pl.kubiczak.felix.shark.samples.tests.integration.ioc.blueprint.spring.security;

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
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.kubiczak.felix.shark.samples.tests.integration.ioc.LoggingOptions;
import pl.kubiczak.felix.shark.samples.tests.integration.ioc.blueprint.Options;

import javax.inject.Inject;

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class FilterChainTest {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Inject
  @Filter("(" + HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_PATTERN
          + "=/samples.ioc.blueprint.spring.security/*)")
  private javax.servlet.Filter springSecurityFilterChain;

  @Test
  public void springSecurityFilterChainShouldBeAvailable() {
    assertThat(springSecurityFilterChain, instanceOf(javax.servlet.Filter.class));
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

            LoggingOptions.logbackBundlesAndConfiguration(),
            Options.springAndGeminiBlueprint(),
            Options.springSecurity(),

            // bundles for tests
            mavenBundle("pl.kubiczak.felix.shark",
                    "samples.ioc.blueprint.spring.security").versionAsInProject()
    };
  }
}
