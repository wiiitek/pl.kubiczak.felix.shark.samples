package pl.kubiczak.felix.shark.samples.jersey;

import com.google.gson.GsonBuilder;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("sample")
public class SampleResource {

  private static final int STATUS_SUCCESS = 200;

  /**
   * Process GET method for the sample path.
   *
   * @return JSON string for sample model
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getStatus() {
    SampleModel model = new SampleModel("James Bond", 007);
    String json = new GsonBuilder().setPrettyPrinting().create().toJson(model);
    return Response.status(STATUS_SUCCESS).entity(json).build();
  }
}
