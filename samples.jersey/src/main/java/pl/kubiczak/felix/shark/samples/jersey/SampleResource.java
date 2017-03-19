package pl.kubiczak.felix.shark.samples.jersey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("sample")
public class SampleResource {

  private static final int STATUS_SUCCESS = 200;

  private final Logger log = LoggerFactory.getLogger(this.getClass());

  /**
   * Process GET method for the sample path.
   *
   * @return JSON string for sample model
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getStatus() {
    SampleModel model = new SampleModel("James Bond", 007);
    String jsonString = model.toJsonString();
    log.debug("returning JSON string: '{}'", jsonString);
    return Response.status(STATUS_SUCCESS).entity(jsonString).build();
  }
}
