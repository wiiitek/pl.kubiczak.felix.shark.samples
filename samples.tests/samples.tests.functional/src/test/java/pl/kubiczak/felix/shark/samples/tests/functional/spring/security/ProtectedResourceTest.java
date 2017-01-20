package pl.kubiczak.felix.shark.samples.tests.functional.spring.security;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.apache.http.StatusLine;
import org.junit.Test;

import pl.kubiczak.felix.shark.samples.tests.functional.SimpleHttpRequest;

import java.io.IOException;
import java.util.Map;

public class ProtectedResourceTest {

  private static final Integer STATUS_REDIRECT = 302;

  private static final String REDIRECT_HEADER_KEY = "Location";

  private static final String PROTECTED_URL =
          "http://localhost:8080/spring.security/protected-resource/";

  private static final String LOGIN_PAGE_URL =
          "http://localhost:8080/spring.security/login/";

  @Test
  public void shouldRedirect() throws IOException {
    SimpleHttpRequest request = new SimpleHttpRequest(PROTECTED_URL);
    StatusLine statusLine = request.retrieveStatusLine();

    assertThat(statusLine.getStatusCode(), equalTo(STATUS_REDIRECT));
  }

  @Test
  public void shouldRedirectToLoginPage() throws IOException {
    SimpleHttpRequest request = new SimpleHttpRequest(PROTECTED_URL);
    Map<String, String> headers = request.retrieveHeaders();
    String redirectTo = headers.get(REDIRECT_HEADER_KEY);

    assertThat(redirectTo, equalTo(LOGIN_PAGE_URL));
  }
}
