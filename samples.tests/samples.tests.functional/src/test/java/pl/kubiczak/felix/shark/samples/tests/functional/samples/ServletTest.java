package pl.kubiczak.felix.shark.samples.tests.functional.samples;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import pl.kubiczak.felix.shark.samples.tests.functional.SharkWebconsoleRequest;

import java.io.IOException;

public class ServletTest {

  private static final String SERVLET_RESPONSE_FRAGMENT = "Test = 'zażółć gęślą jaźń'";

  private static final String WHITEBOARD_SEVLET_URL =
          "http://localhost:8080/whiteboard/servlet/";

  @Test
  public void whiteboardServletShouldBeAvailable() throws IOException {
    String suffix = Long.toString(System.currentTimeMillis());
    SharkWebconsoleRequest request = new SharkWebconsoleRequest(WHITEBOARD_SEVLET_URL + suffix);
    String content = request.retrieveContent();

    assertThat(content, containsString(suffix));
    assertThat(content, containsString(SERVLET_RESPONSE_FRAGMENT));
  }
}
