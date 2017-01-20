package pl.kubiczak.felix.shark.samples.tests.functional.webconsole;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import org.json.JSONObject;
import org.junit.Test;

import pl.kubiczak.felix.shark.samples.tests.functional.SharkWebconsoleRequest;

import java.io.IOException;

public class BundlesTest {

  private static final String BUNDLES_URL =
          "http://admin:admin@localhost:8080/system/console/bundles.json";

  @Test
  public void bundlesShouldHaveCorrectStatus() throws IOException {
    SharkWebconsoleRequest request = new SharkWebconsoleRequest(BUNDLES_URL);
    JSONObject response = request.retrieveJson();

    String status = response.getString("status");
    assertThat(status, not(containsString("resolved")));
    assertThat(status, not(containsString("installed")));
  }
}
