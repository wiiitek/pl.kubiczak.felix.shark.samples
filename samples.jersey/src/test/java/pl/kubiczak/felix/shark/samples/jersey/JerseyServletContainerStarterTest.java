package pl.kubiczak.felix.shark.samples.jersey;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Dictionary;
import javax.servlet.Servlet;
import org.glassfish.jersey.servlet.ServletContainer;
import org.junit.Before;
import org.junit.Test;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.HttpService;

public class JerseyServletContainerStarterTest {

  private static final String EXPECTED_JERSEY_PATH = "/jersey";

  private JerseyServletContainerStarter tested;

  @Before
  public void setUp() throws Exception {
    tested = new JerseyServletContainerStarter();
  }

  @Test
  public void shouldRegisterForCorrectPath() throws Exception {
    tested.httpService = mock(HttpService.class);
    tested.registerJerseyServletContainer();
    verify(tested.httpService).registerServlet(eq(EXPECTED_JERSEY_PATH), any(Servlet.class),
        any(Dictionary.class), any(HttpContext.class));
  }

  @Test
  public void shouldRegisterCorrectServlet() throws Exception {
    tested.httpService = mock(HttpService.class);
    tested.registerJerseyServletContainer();
    verify(tested.httpService).registerServlet(any(String.class), any(ServletContainer.class),
        any(Dictionary.class), any(HttpContext.class));
  }

  @Test
  public void shouldUnregisterServletAndDeleteReferenceToService() throws Exception {
    HttpService mockHttpService = mock(HttpService.class);
    tested.httpService = mockHttpService;
    tested.unregisterJerseyServletContainer();

    assertThat(tested.httpService, nullValue());
    verify(mockHttpService).unregister(eq(EXPECTED_JERSEY_PATH));
  }

}
