package pl.kubiczak.felix.shark.samples.http.servlet.whiteboard;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
        (
                service = Servlet.class,
                property = {
                        HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT + "="
                                + WhiteboardContext.CONTEXT_FILTER,
                        HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN + "="
                                + WhiteboardServlet.SERVLET_PATTERN
                }
        )
public class WhiteboardServlet extends HttpServlet {

  static final String SERVLET_PATTERN = "/servlet/*";

  private final transient Logger log = LoggerFactory.getLogger(this.getClass());

  private static String responsePattern;

  /**
   * Initializes pattern for servlet response.
   */
  @Activate
  public void activate() {
    StringBuilder sb = new StringBuilder();
    sb.append("Service = '").append(getClass().getCanonicalName()).append("'\n\n");
    sb.append("Test = 'zażółć gęślą jaźń'\n");
    sb.append("Service activation at: ").append(new Date()).append("\n");
    sb.append("RequestURI = '%s'\nPathInfo = '%s'\nQueryString = '%s'");
    WhiteboardServlet.setResponsePattern(sb.toString());
  }

  private static void setResponsePattern(String pattern) {
    WhiteboardServlet.responsePattern = pattern;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res)
          throws ServletException, IOException {

    log.debug("remote host: '{}'", req.getRemoteHost());

    res.setContentType("text/plain");
    res.setCharacterEncoding("UTF-8");
    res.setStatus(HttpServletResponse.SC_OK);

    String msg = String.format(responsePattern,
            req.getRequestURI(), req.getPathInfo(), req.getQueryString());

    try (PrintWriter out = res.getWriter()) {
      out.println(msg);
      out.flush();
    } catch (IllegalStateException | IOException exception) {
      log.error("error while getting writer: {}", exception.getMessage(), exception);
    }
  }

}
