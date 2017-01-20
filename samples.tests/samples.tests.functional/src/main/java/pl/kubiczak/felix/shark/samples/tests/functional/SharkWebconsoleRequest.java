package pl.kubiczak.felix.shark.samples.tests.functional;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class SharkWebconsoleRequest {

  private final Logger log = LoggerFactory.getLogger(getClass());

  private final URL url;

  public SharkWebconsoleRequest(String url) throws MalformedURLException {
    this.url = new URL(url);
  }

  /**
   * Checks HTTP response status code.
   *
   * @return HTTP status code
   * @throws IOException if there are some errors with HTTP client
   */
  public StatusLine retrieveStatusLine() throws IOException {
    StatusLine statusLine;
    try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
      HttpGet httpGet = new HttpGet(this.url.toString());
      try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
        statusLine = response.getStatusLine();
      }
    }
    return statusLine;
  }

  /**
   * Gets the body of HTTP GET request.
   *
   * @return response body content
   * @throws IOException if there are some errors with HTTP client
   */
  public String retrieveContent() throws IOException {
    String content;
    try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
      HttpGet httpGet = new HttpGet(this.url.toString());
      try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
        content = collectContent(response);
      }
    }
    return content;
  }

  /**
   * Gets the response of GET request and converts it to JSONObject.
   *
   * @return response body converted to JSONObject
   * @throws IOException if there are some errors with HTTP client
   */
  public JSONObject retrieveJson() throws IOException {
    JSONObject result = null;
    String content = retrieveContent();
    if (content != null) {
      try {
        result = new JSONObject(content);
      } catch (JSONException je) {
        log.error("error while parsing JSON: [{}]", content, je);
      }
    }
    return result;
  }

  private String collectContent(CloseableHttpResponse response) throws IOException {
    StringBuilder sb = new StringBuilder();
    BufferedReader br;
    try {
      HttpEntity entity = response.getEntity();
      if (entity != null) {
        br = toBufferedReader(entity);
        String line;
        while ((line = br.readLine()) != null) {
          sb.append(line);
        }
      }
      EntityUtils.consume(entity);
    } finally {
      response.close();
    }
    return sb.toString();
  }

  private BufferedReader toBufferedReader(HttpEntity entity) {
    BufferedReader br = null;
    InputStream is = null;
    InputStreamReader ir = null;
    String encoding = StandardCharsets.UTF_8.name();
    try {
      is = entity.getContent();
    } catch (IOException ioe) {
      log.error("error while getting content from HTTP entity: '{}'", entity, ioe);
    }
    if (is != null) {
      try {
        ir = new InputStreamReader(is, encoding);
      } catch (UnsupportedEncodingException uee) {
        log.error("unsupported encoding: '{}'", encoding, uee);
      }
    }
    if (ir != null) {
      br = new BufferedReader(ir);
    }
    return br;
  }
}
