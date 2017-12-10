package pl.kubiczak.felix.shark.samples.tests.functional.samples.jersey;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import java.io.IOException;
import org.json.JSONObject;
import org.junit.Test;
import pl.kubiczak.felix.shark.samples.tests.functional.SimpleHttpRequest;

public class SampleResourceTest {

  private static final String TESTED_PAGE = "http://localhost:8080/jersey/sample";

  @Test
  public void jsonTest() throws IOException {
    SimpleHttpRequest request = new SimpleHttpRequest(TESTED_PAGE);
    JSONObject response = request.retrieveJson();

    assertThat(response.getString("name"), equalTo("James Bond"));
    assertThat(response.getInt("number"), equalTo(7));
  }
}
