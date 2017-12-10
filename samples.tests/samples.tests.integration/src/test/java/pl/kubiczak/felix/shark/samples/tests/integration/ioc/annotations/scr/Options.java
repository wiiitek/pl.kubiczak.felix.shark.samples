package pl.kubiczak.felix.shark.samples.tests.integration.ioc.annotations.scr;

import static org.ops4j.pax.exam.CoreOptions.mavenBundle;

import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.options.DefaultCompositeOption;

class Options {

  public static Option bundlesForApacheScr() {

    return new DefaultCompositeOption(
        mavenBundle("org.apache.felix",
            "org.apache.felix.scr").versionAsInProject()
    );
  }

}
