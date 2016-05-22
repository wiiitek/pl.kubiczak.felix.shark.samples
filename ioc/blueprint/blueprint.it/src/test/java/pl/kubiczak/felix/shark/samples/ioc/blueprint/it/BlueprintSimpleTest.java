package pl.kubiczak.felix.shark.samples.ioc.blueprint.it;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;
import static org.ops4j.pax.exam.CoreOptions.junitBundles;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.systemProperty;

import java.util.HashMap;
import java.util.Map;

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
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

import pl.kubiczak.felix.shark.samples.ioc.blueprint.simple.EventHandlerImpl;


@RunWith(PaxExam.class)
@ExamReactorStrategy(PerMethod.class)
public class BlueprintSimpleTest {

	private static final Map<String, String> TEST_EVENT_PROPERTIES = new HashMap<String, String>() {{
		put("test.event.title", "Test Event Title");
		put("test.event.description", "Test Event Description");
	}};

	@Inject
	private EventAdmin eventAdmin;

	/**
	 * EventHandler implementation from blueprint.simple bundle
	 */
	@Inject
	@Filter("(" + EventConstants.EVENT_TOPIC + "=" + EventHandlerImpl.TOPIC + ")")
	private EventHandler eventHandler;

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
				// bundles for tests
				mavenBundle("org.apache.felix", "org.apache.felix.eventadmin").versionAsInProject(),
				mavenBundle("pl.kubiczak.felix.shark.samples.ioc", "blueprint.simple").versionAsInProject()
		};
	}

	@Test
	public void eventAdminServiceShouldBeAvailable() {
		assertThat(eventAdmin, is(not(nullValue())));
	}

	@Test
	public void eventHandlerServiceShouldBeAvailable() {
		assertThat(eventHandler, is(not(nullValue())));
	}

	@Test
	public void filterShouldWorkCorrectly() {
		assertThat(eventHandler, instanceOf(EventHandlerImpl.class));
	}

	@Test
	public void shouldPostOsgiEvent() {
		long before = ((EventHandlerImpl) eventHandler).processedEvents();
		// sends event synchronously
		eventAdmin.sendEvent(new Event(EventHandlerImpl.TOPIC, TEST_EVENT_PROPERTIES));
		long after = ((EventHandlerImpl) eventHandler).processedEvents();
		assertThat(after, is(before + 1));
	}

}
