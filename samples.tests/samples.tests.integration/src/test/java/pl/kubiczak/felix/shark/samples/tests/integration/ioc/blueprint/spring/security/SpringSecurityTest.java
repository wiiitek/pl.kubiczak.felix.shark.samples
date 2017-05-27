package pl.kubiczak.felix.shark.samples.tests.integration.ioc.blueprint.spring.security;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import pl.kubiczak.felix.shark.samples.ioc.blueprint.spring.security.UsernamePasswordAuthenticator;
import pl.kubiczak.felix.shark.samples.tests.integration.ioc.LoggingOptions;
import pl.kubiczak.felix.shark.samples.tests.integration.ioc.blueprint.Options;

import javax.inject.Inject;

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class SpringSecurityTest {

  @Inject
  @Filter("(" + HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_PATTERN
          + "=/samples.ioc.blueprint.spring.security/*)")
  private javax.servlet.Filter springSecurityFilterChain;

  @Inject
  private UsernamePasswordAuthenticator authenticator;

  @Test
  public void springSecurityFilterChainShouldBeAvailable() {
    assertThat(springSecurityFilterChain, instanceOf(javax.servlet.Filter.class));
  }

  @Test
  public void authenticatorShouldBeAvailable() {
    assertNotNull(authenticator);
  }

  @Test
  public void authenticatorHasSpringAuthenticationManager() {
    AuthenticationManager authenticationManager = authenticator.getAuthenticationManager();
    assertNotNull(authenticationManager);
  }

  @Test
  public void authenticatorDoesntAllowWithNullPassword() {
    Authentication authentication = authenticator.authenticate("anonymous", null);
    assertNull(authentication);
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
