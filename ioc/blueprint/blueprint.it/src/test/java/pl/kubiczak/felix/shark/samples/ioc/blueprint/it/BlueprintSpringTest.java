package pl.kubiczak.felix.shark.samples.ioc.blueprint.it;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertThat;
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

import pl.kubiczak.felix.shark.samples.ioc.blueprint.simple.SimpleBean;
import pl.kubiczak.felix.shark.samples.ioc.blueprint.spring.Printable;
import pl.kubiczak.felix.shark.samples.ioc.blueprint.spring.SpringBean;

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerMethod.class)
public class BlueprintSpringTest {

	@Inject
	@Filter("(osgi.service.blueprint.compname=springBeanA)")
	private Printable springBeanA;

	@Inject
	@Filter("(osgi.service.blueprint.compname=springBeanB)")
	private Printable springBeanB;

	@Inject
	@Filter("(osgi.service.blueprint.compname=springBeanC)")
	private Printable springBeanC;

	@Configuration
	public Option[] provideRequiredBundles() {
		return new Option[]{
				junitBundles(),
				// logging for container
				mavenBundle("org.slf4j", "slf4j-api").versionAsInProject(),
				mavenBundle("ch.qos.logback", "logback-core").versionAsInProject(),
				mavenBundle("ch.qos.logback", "logback-classic").versionAsInProject(),
				systemProperty("logback.configurationFile")
						.value("file:" + PathUtils.getBaseDir() + "/src/test/resources/logback-test.xml"),
				// aries blueprint
				mavenBundle("org.apache.aries.blueprint", "org.apache.aries.blueprint.api").versionAsInProject(),
				mavenBundle("org.apache.aries.blueprint", "org.apache.aries.blueprint.core").versionAsInProject(),
				mavenBundle("org.apache.aries", "org.apache.aries.util").versionAsInProject(),
				mavenBundle("org.apache.aries.proxy", "org.apache.aries.proxy").versionAsInProject(),
				mavenBundle("org.apache.aries.blueprint", "org.apache.aries.blueprint.spring").versionAsInProject(),
				// spring
				mavenBundle("org.apache.servicemix.bundles","org.apache.servicemix.bundles.spring-aop").versionAsInProject(),
				mavenBundle("org.apache.servicemix.bundles","org.apache.servicemix.bundles.spring-context").versionAsInProject(),
				mavenBundle("org.apache.servicemix.bundles","org.apache.servicemix.bundles.spring-core").versionAsInProject(),
				mavenBundle("org.apache.servicemix.bundles","org.apache.servicemix.bundles.spring-beans").versionAsInProject(),
				mavenBundle("org.apache.servicemix.bundles","org.apache.servicemix.bundles.spring-expression").versionAsInProject(),
				// bundles for tests
				mavenBundle("org.apache.felix", "org.apache.felix.eventadmin").versionAsInProject(),
				mavenBundle("pl.kubiczak.felix.shark.samples.ioc", "blueprint.simple").versionAsInProject(),
				mavenBundle("pl.kubiczak.felix.shark.samples.ioc", "blueprint.spring").versionAsInProject(),
				mavenBundle("org.apache.felix","org.apache.felix.http.servlet-api").versionAsInProject(),
				mavenBundle("org.apache.commons","commons-lang3").versionAsInProject()
		};
	}

	@Test
	public void springBeanAShouldPrintCorrectName() {
		String expectedPrefix = "Spring Bean A[" + SimpleBean.class.getCanonicalName();
		assertThat(springBeanA.toString(), startsWith(expectedPrefix));
	}

	@Test
	public void springBeanBShouldPrintCorrectName() {
		assertThat(springBeanB.toString(), is(equalTo("Spring Bean B")));
	}

	@Test
	public void springBeanCShouldPrintCorrectName() {
		String expectedPrefix = SpringBean.class.getCanonicalName();
		String expectedContain = "[" + SimpleBean.class.getCanonicalName();
		assertThat(springBeanC.toString(), startsWith(expectedPrefix));
		assertThat(springBeanC.toString(), containsString(expectedContain));
	}
}
