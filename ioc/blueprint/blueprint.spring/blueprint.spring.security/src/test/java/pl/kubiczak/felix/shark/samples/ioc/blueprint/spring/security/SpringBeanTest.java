package pl.kubiczak.felix.shark.samples.ioc.blueprint.spring.security;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.servlet.Filter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:OSGI-INF/blueprint/spring-context.xml")
public class SpringBeanTest {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	public void springSecurityFilterChainShouldBeCreated() {
		FilterChainProxy securityFilterChain
				= applicationContext.getBean("springSecurityFilterChain", FilterChainProxy.class);
		assertThat(securityFilterChain, is(notNullValue()));
	}

	@Test
	public void springSecurityFilterChainShouldContainFilters() {
		FilterChainProxy securityFilterChain
				= applicationContext.getBean("springSecurityFilterChain", FilterChainProxy.class);
		List<SecurityFilterChain> filterChains = securityFilterChain.getFilterChains();

		boolean containsFilter = false;
		if (log.isDebugEnabled()) {
			for (SecurityFilterChain filterChain : filterChains) {
				log.debug("logging filterChain...");
				for (Filter filter : filterChain.getFilters()) {
					log.debug("filter: '{}'", filter.getClass().getName());
					containsFilter = true;
				}
			}
		}

		assertTrue("Should contain at least one filter", containsFilter);
	}
}