package pl.kubiczak.felix.shark.samples.tests.integration.ioc.blueprint;

import static org.ops4j.pax.exam.CoreOptions.mavenBundle;

import org.ops4j.pax.exam.options.CompositeOption;
import org.ops4j.pax.exam.options.DefaultCompositeOption;

class Options {

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
