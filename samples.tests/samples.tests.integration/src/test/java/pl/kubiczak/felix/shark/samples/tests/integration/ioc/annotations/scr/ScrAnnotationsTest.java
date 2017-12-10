package pl.kubiczak.felix.shark.samples.tests.integration.ioc.annotations.scr;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.ops4j.pax.exam.CoreOptions.junitBundles;
import static org.ops4j.pax.exam.CoreOptions.maven;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.wrappedBundle;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.kubiczak.felix.shark.samples.ioc.annotations.scr.Simple;
import pl.kubiczak.felix.shark.samples.tests.integration.ioc.LoggingOptions;

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class ScrAnnotationsTest {

  private final Logger log = LoggerFactory.getLogger(getClass());

  /**
   * Service for Simple.class provided by ioc.annotations.scr bundle.
   */
  @Inject
  private Simple simple;

  @Test
  public void simpleServiceShouldBeAvailable() {
    assertThat(simple, is(not(nullValue())));
  }

  @Test
  public void simpleServiceIsNotConfigured() {
    assertThat(simple.getName(), is(nullValue()));
  }

  /**
   * Provides configuration for Pax Exam container.
   * Contains bundles for logging, scr and tested bundles
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
        Options.bundlesForApacheScr(), awaitilityOption,

        // bundles for tests
        //mavenBundle("org.apache.felix", "org.apache.felix.eventadmin").versionAsInProject(),
        mavenBundle("pl.kubiczak.felix.shark", "samples.ioc.annotations.scr")
            .versionAsInProject()
    };
  }
}
