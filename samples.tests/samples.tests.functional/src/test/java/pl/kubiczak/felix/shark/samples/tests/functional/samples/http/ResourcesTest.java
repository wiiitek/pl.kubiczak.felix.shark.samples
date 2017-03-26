package pl.kubiczak.felix.shark.samples.tests.functional.samples.http;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import pl.kubiczak.felix.shark.samples.tests.functional.SimpleHttpRequest;

import java.io.IOException;

public class ResourcesTest {

  private static final Integer STATUS_OK = 200;

  private static final String HTTP_RESOURCE_URL =
          "http://localhost:8080/samples.http.resources/index.html";

  private static final String AMDATU_RESOURCE_URL =
          "http://localhost:8080/samples.http.resources.amdatu/";

  @Test
  public void httpResourceShouldBeAvailable() throws IOException {
    SimpleHttpRequest request = new SimpleHttpRequest(HTTP_RESOURCE_URL);
    Integer actual = request.retrieveStatusLine().getStatusCode();

    assertThat(actual, equalTo(STATUS_OK));
  }

  @Test
  public void amdatuResourceShouldBeAvailable() throws IOException {
    SimpleHttpRequest request = new SimpleHttpRequest(AMDATU_RESOURCE_URL);
    Integer actual = request.retrieveStatusLine().getStatusCode();

    assertThat(actual, equalTo(STATUS_OK));
  }
}
