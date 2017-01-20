package pl.kubiczak.felix.shark.samples.tests.functional.webconsole;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import pl.kubiczak.felix.shark.samples.tests.functional.SimpleHttpRequest;

import java.io.IOException;

public class HttpServiceTest {

  private static final Integer STATUS_OK = 200;

  private static final String HTTP_SERVICE_URL =
          "http://admin:admin@localhost:8080/system/console/httpservice";

  @Test
  public void httpServicePageShouldBeAvailable() throws IOException {
    SimpleHttpRequest request = new SimpleHttpRequest(HTTP_SERVICE_URL);
    Integer actual = request.retrieveStatusLine().getStatusCode();

    assertThat(actual, equalTo(STATUS_OK));
  }
}
