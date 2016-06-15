package pl.kubiczak.felix.shark.samples.security;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.StringUtils;
import org.apache.felix.webconsole.WebConsoleSecurityProvider2;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebConsoleAuthHelperTest {

  private WebConsoleAuthHelper tested;

  @Mock
  private HttpServletRequest httpServletRequest;

  @Mock
  private Authentication authentication;

  @Mock
  private HttpServletResponse httpServletResponse;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    tested = new WebConsoleAuthHelper(httpServletRequest, httpServletResponse);
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
            .thenReturn(authentication);
    boolean isAuthenticated = tested.isAuthenticated();
    assertTrue(isAuthenticated);
  }

  @Test
  public void isAuthenticated_shouldReturnFalseForNonAuthObject() {
    when(httpServletRequest.getAttribute(eq(WebConsoleSecurityProvider2.USER_ATTRIBUTE)))
            .thenReturn(new Object());
    boolean isAuthenticated = tested.isAuthenticated();
    assertFalse(isAuthenticated);
  }

}
