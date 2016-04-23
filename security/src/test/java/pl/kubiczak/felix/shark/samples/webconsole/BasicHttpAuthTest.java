package pl.kubiczak.felix.shark.samples.webconsole;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.felix.webconsole.WebConsoleSecurityProvider2;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class BasicHttpAuthTest {

	private BasicHttpAuth tested;

	@Mock
	private HttpServletRequest httpServletRequest;

	@Mock
	private HttpServletResponse httpServletResponse;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		tested = new BasicHttpAuth(httpServletRequest, httpServletResponse);
	}

	@Test
	public void isAuthenticated_shouldReturnFalseForNullAttributeValue() {
		when(httpServletRequest.getAttribute(eq(WebConsoleSecurityProvider2.USER_ATTRIBUTE)))
				.thenReturn(null);
		boolean isAuthenticated = tested.isAuthenticated();
		assertFalse(isAuthenticated);
	}

	@Test
	public void isAuthenticated_shouldReturnFalseForEmptyAttributeValue() {
		when(httpServletRequest.getAttribute(eq(WebConsoleSecurityProvider2.USER_ATTRIBUTE)))
				.thenReturn(StringUtils.EMPTY);
		boolean isAuthenticated = tested.isAuthenticated();
		assertFalse(isAuthenticated);
	}

	@Test
	public void isAuthenticated_shouldReturnTrueForAuthObject() {
		when(httpServletRequest.getAttribute(eq(WebConsoleSecurityProvider2.USER_ATTRIBUTE)))
				.thenReturn(new Authority("username"));
		boolean isAuthenticated = tested.isAuthenticated();
		assertTrue(isAuthenticated);
	}

	@Test
	public void isAuthenticated_shouldReturnFalseForNonAuthObject() {
		when(httpServletRequest.getAttribute(eq(WebConsoleSecurityProvider2.USER_ATTRIBUTE)))
				.thenReturn(new String("username"));
		boolean isAuthenticated = tested.isAuthenticated();
		assertFalse(isAuthenticated);
	}

}