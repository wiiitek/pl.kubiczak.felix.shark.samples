package pl.kubiczak.felix.shark.samples.tests.integration.ioc;

import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.systemProperty;

import org.ops4j.pax.exam.options.CompositeOption;
import org.ops4j.pax.exam.options.DefaultCompositeOption;
import org.ops4j.pax.exam.util.PathUtils;

public class LoggingOptions {

  /**
   * Prepares PAx Exam to enable logging support.
   *
   * @return bundles for logging support inside Pax Exam container.
   */
  public static CompositeOption logbackBundlesAndConfiguration() {
    return new DefaultCompositeOption(
            // slf4j configuration for container
            mavenBundle("org.slf4j", "slf4j-api").versionAsInProject(),
            mavenBundle("org.slf4j", "jcl-over-slf4j").versionAsInProject(),
            mavenBundle("ch.qos.logback", "logback-core").versionAsInProject(),
            mavenBundle("ch.qos.logback", "logback-classic").versionAsInProject(),
            // org.apache.commons.logging
            //mavenBundle("org.slf4j", "jcl-over-slf4j").versionAsInProject(),

            systemProperty("logback.configurationFile")
                    .value("file:" + PathUtils.getBaseDir()
                            + "/src/test/resources/logback-for-osgi.xml")
    );
  }
}
