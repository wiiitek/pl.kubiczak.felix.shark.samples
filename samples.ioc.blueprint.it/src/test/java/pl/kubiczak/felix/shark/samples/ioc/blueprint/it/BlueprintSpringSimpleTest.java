package pl.kubiczak.felix.shark.samples.ioc.blueprint.it;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.ops4j.pax.exam.CoreOptions.junitBundles;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.systemProperty;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerMethod;
import org.ops4j.pax.exam.util.Filter;
import org.ops4j.pax.exam.util.PathUtils;

import pl.kubiczak.felix.shark.samples.ioc.blueprint.spring.simple.Bean;

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerMethod.class)
public class BlueprintSpringSimpleTest {

    private static final String BLUEPRINT_COMPONENT_NAME_KEY = "osgi.service.blueprint.compname";

    @Inject
    @Filter("(" + BLUEPRINT_COMPONENT_NAME_KEY + "=beanA)")
    private Bean beanA;

    @Test
    public void springBeanAService() {
        assertThat(beanA.toString(), is("Bean A[Sample Value]"));
    }

    @Configuration
    public Option[] provideRequiredBundles() {
        return new Option[]{
                junitBundles(),
                // slf4j configuration for container (see also exam.properties file)
                mavenBundle("org.slf4j", "slf4j-api").versionAsInProject(),
                mavenBundle("org.slf4j", "jcl-over-slf4j").versionAsInProject(),
                mavenBundle("ch.qos.logback", "logback-core").versionAsInProject(),
                mavenBundle("ch.qos.logback", "logback-classic").versionAsInProject(),
                systemProperty("logback.configurationFile")
                        .value("file:" + PathUtils.getBaseDir() + "/src/test/resources/logback.xml"),
                // gemini blueprint
                mavenBundle("org.eclipse.gemini.blueprint", "gemini-blueprint-core").versionAsInProject(),
                mavenBundle("org.eclipse.gemini.blueprint", "gemini-blueprint-io").versionAsInProject(),
                mavenBundle("org.eclipse.gemini.blueprint", "gemini-blueprint-extender").versionAsInProject(),
                // spring
                mavenBundle("org.apache.servicemix.bundles", "org.apache.servicemix.bundles.aopalliance").versionAsInProject(),
                mavenBundle("org.apache.servicemix.bundles", "org.apache.servicemix.bundles.spring-aop").versionAsInProject(),
                mavenBundle("org.apache.servicemix.bundles", "org.apache.servicemix.bundles.spring-context").versionAsInProject(),
                mavenBundle("org.apache.servicemix.bundles", "org.apache.servicemix.bundles.spring-core").versionAsInProject(),
                mavenBundle("org.apache.servicemix.bundles", "org.apache.servicemix.bundles.spring-beans").versionAsInProject(),
                mavenBundle("org.apache.servicemix.bundles", "org.apache.servicemix.bundles.spring-expression").versionAsInProject(),
                // bundles for tests
                mavenBundle("org.apache.commons", "commons-lang3").versionAsInProject(),
                mavenBundle("pl.kubiczak.felix.shark.samples.ioc", "blueprint.spring.simple").versionAsInProject(),
        };
    }
}
