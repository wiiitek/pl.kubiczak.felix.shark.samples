package pl.kubiczak.felix.shark.samples.tests.functional.samples.http;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.util.Map;
import org.apache.http.StatusLine;
import org.junit.Test;
import pl.kubiczak.felix.shark.samples.tests.functional.SimpleHttpRequest;

public class WhiteboardContextResourcesTest {

  private static final Integer STATUS_OK = 200;

  private static final String TESTED_PAGE
      = "http://localhost:8080/samples.http.whiteboard.context/resources/subpage/";

  @Test
  public void shouldReturnCorrectCodeForWhiteboardResources() throws IOException {
    SimpleHttpRequest request = new SimpleHttpRequest(TESTED_PAGE);
    StatusLine statusLine = request.retrieveStatusLine();

    assertThat(statusLine.getStatusCode(), equalTo(STATUS_OK));
  }

  @Test
  public void shouldReturnHtmlMarkupForResource() throws IOException {
    SimpleHttpRequest request = new SimpleHttpRequest(TESTED_PAGE);
    String loginPageContent = request.retrieveContent();

    assertThat(loginPageContent, startsWith("<!DOCTYPE html>"));
    assertThat(loginPageContent, containsString("<title>Whiteboard Context: Sub-page</title>"));
  }

  /**
   * We don't want any content-cache header,
   * as we are managing those with Spring security.
   */
  @Test
  public void shouldNotHaveCacheControlHeader() throws IOException {
    SimpleHttpRequest request = new SimpleHttpRequest(TESTED_PAGE);
    Map<String, String> headers = request.retrieveHeaders();

    assertThat(headers.keySet(), not(hasItem("Cache-Control")));
  }
}
