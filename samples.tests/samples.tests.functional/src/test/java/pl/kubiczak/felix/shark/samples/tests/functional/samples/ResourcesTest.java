package pl.kubiczak.felix.shark.samples.tests.functional.samples;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import pl.kubiczak.felix.shark.samples.tests.functional.SharkWebconsoleRequest;

import java.io.IOException;

public class ResourcesTest {

  private static final Integer STATUS_OK = 200;

  private static final String HTTP_RESOURCE_URL =
          "http://localhost:8080/resources/index.html";

  private static final String AMDATU_RESOURCE_URL =
          "http://localhost:8080/amdatu/";

  @Test
  public void httpResourceShouldBeAvailable() throws IOException {
    SharkWebconsoleRequest request = new SharkWebconsoleRequest(HTTP_RESOURCE_URL);
    Integer actual = request.retrieveStatusLine().getStatusCode();

    assertThat(actual, equalTo(STATUS_OK));
  }

  @Test
  public void amdatuResourceShouldBeAvailable() throws IOException {
    SharkWebconsoleRequest request = new SharkWebconsoleRequest(AMDATU_RESOURCE_URL);
    Integer actual = request.retrieveStatusLine().getStatusCode();

    assertThat(actual, equalTo(STATUS_OK));
  }
}
