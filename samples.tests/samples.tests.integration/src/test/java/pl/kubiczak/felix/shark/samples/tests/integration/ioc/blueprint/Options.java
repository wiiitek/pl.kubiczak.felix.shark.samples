package pl.kubiczak.felix.shark.samples.tests.integration.ioc.blueprint;

import static org.ops4j.pax.exam.CoreOptions.mavenBundle;

import org.ops4j.pax.exam.options.CompositeOption;
import org.ops4j.pax.exam.options.DefaultCompositeOption;

public class Options {

  /**
   * Provides dependant bundles for Spring and Gemini Blueprint for Pax Exam tests.
   *
   * @return bundles config for Gemini blueprint and Spring
   */
  public static CompositeOption springAndGeminiBlueprint() {
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

  /**
   Provides dependant bundles for Spring Security and Spring Web for Pax Exam tests.
   *
   * @return bundles config for Spring Security and Spring Web
   */
  public static CompositeOption springSecurity() {
    return new DefaultCompositeOption(
            // SCR for annotations in Shark Spring Security Sample
            mavenBundle("org.apache.felix",
                    "org.apache.felix.scr")
                    .versionAsInProject(),
            // HTTP Service
            mavenBundle("org.apache.felix",
                    "org.apache.felix.http.servlet-api")
                    .versionAsInProject(),
            // Spring required by Spring Web
            mavenBundle("org.apache.servicemix.bundles",
                    "org.apache.servicemix.bundles.spring-context-support")
                    .versionAsInProject(),
            // Spring Web
            mavenBundle("org.apache.servicemix.bundles",
                    "org.apache.servicemix.bundles.spring-web")
                    .versionAsInProject(),
            mavenBundle("org.apache.servicemix.bundles",
                    "org.apache.servicemix.bundles.spring-webmvc")
                    .versionAsInProject(),
            // Spring Security
            mavenBundle("org.apache.servicemix.bundles",
                    "org.apache.servicemix.bundles.spring-security-core")
                    .versionAsInProject(),
            mavenBundle("org.apache.servicemix.bundles",
                    "org.apache.servicemix.bundles.spring-security-config")
                    .versionAsInProject(),
            mavenBundle("org.apache.servicemix.bundles",
                    "org.apache.servicemix.bundles.spring-security-web")
                    .versionAsInProject()
    );
  }
}
