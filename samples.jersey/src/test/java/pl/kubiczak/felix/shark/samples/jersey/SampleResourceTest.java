package pl.kubiczak.felix.shark.samples.jersey;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import org.junit.Before;
import org.junit.Test;

public class SampleResourceTest {

  private SampleResource tested;

  @Before
  public void setUp() throws Exception {
    tested = new SampleResource();
  }

  @Test
  public void shouldReturnCorrectStatus() throws Exception {
    Response response = tested.getStatus();
    assertThat(response.getStatus(), equalTo(HttpServletResponse.SC_OK));
  }

  @Test
  public void shouldReturnProperJsonString() {
    Response response = tested.getStatus();
    String entity = (String) response.getEntity();

    JsonElement jsonElement = new JsonParser().parse(entity);
    JsonObject jsonObject = jsonElement.getAsJsonObject();

    JsonPrimitive name = jsonObject.getAsJsonPrimitive("name");
    JsonPrimitive number = jsonObject.getAsJsonPrimitive("number");

    assertThat(name.getAsString(), equalTo("James Bond"));
    assertThat(number.getAsInt(), equalTo(007));
  }
}
