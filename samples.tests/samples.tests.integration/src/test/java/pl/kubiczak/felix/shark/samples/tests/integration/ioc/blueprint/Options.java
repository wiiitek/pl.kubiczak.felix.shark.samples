package pl.kubiczak.felix.shark.samples.tests.integration.ioc.blueprint;

import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.systemProperty;

import org.ops4j.pax.exam.options.CompositeOption;
import org.ops4j.pax.exam.options.DefaultCompositeOption;
import org.ops4j.pax.exam.util.PathUtils;

class Options {

  static CompositeOption logbackBundlesAndConfiguration() {
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

  static CompositeOption springAndGeminiBlueprint() {
    return new DefaultCompositeOption(

            mavenBundle("org.apache.servicemix.bundles",
                    "org.apache.servicemix.bundles.aopalliance").versionAsInProject(),
            mavenBundle("org.apache.servicemix.bundles",
                    "org.apache.servicemix.bundles.spring-aop").versionAsInProject(),
            mavenBundle("org.apache.servicemix.bundles",
                    "org.apache.servicemix.bundles.spring-context").versionAsInProject(),
            mavenBundle("org.apache.servicemix.bundles",
                    "org.apache.servicemix.bundles.spring-core").versionAsInProject(),
            mavenBundle("org.apache.servicemix.bundles",
                    "org.apache.servicemix.bundles.spring-beans").versionAsInProject(),
            mavenBundle("org.apache.servicemix.bundles",
                    "org.apache.servicemix.bundles.spring-expression").versionAsInProject(),
            mavenBundle("org.eclipse.gemini.blueprint", "gemini-blueprint-core")
                    .versionAsInProject(),
            mavenBundle("org.eclipse.gemini.blueprint", "gemini-blueprint-io")
                    .versionAsInProject(),
            mavenBundle("org.eclipse.gemini.blueprint", "gemini-blueprint-extender")
                    .versionAsInProject()
    );
  }
}
