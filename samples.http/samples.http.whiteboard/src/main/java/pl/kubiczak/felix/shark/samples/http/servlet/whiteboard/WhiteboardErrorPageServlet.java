package pl.kubiczak.felix.shark.samples.http.servlet.whiteboard;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// see also
// http://www.slideshare.net/mfrancis/http-whiteboard-osgi-compendium-60-how-web-apps-should-have-been-r-auge
@Component
        (
                service = Servlet.class,
                property = {
                        HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT + "="
                                + WhiteboardContext.CONTEXT_FILTER,
                        HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN + "="
                                + WhiteboardErrorPageServlet.SERVLET_PATTERN,
                        HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_ERROR_PAGE + "="
                                + "[404,403]",
                        Constants.SERVICE_RANKING + ":Integer="
                                + "100",
                }
        )
public class WhiteboardErrorPageServlet extends HttpServlet {

  static final String SERVLET_PATTERN = "/*";

  private static final String REDIRECT_TO_URL = "/system/console/httpservice";

  private final transient Logger log = LoggerFactory.getLogger(this.getClass());

  /**
   * Initializes pattern for servlet response.
   */
  @Activate
  public void activate() {
    log.debug("activating error page servlet");
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res)
          throws ServletException, IOException {

    String requestedPath = null;
    try {
      requestedPath = collectRequestedPath(req);
    } catch (MalformedURLException murle) {
      log.error("error while collecting the path from request", murle);
    }
    log.debug("page not found: '{}', redirecting to: '{}'", requestedPath, REDIRECT_TO_URL);

    res.setHeader("Redirect-Type", "Page not found: " + requestedPath);
    try {
      res.sendRedirect(REDIRECT_TO_URL);
    } catch (IOException ioe) {
      log.error("error while sending redirect: {}", ioe.getMessage(), ioe);
    }
  }

  private String collectRequestedPath(HttpServletRequest request) throws MalformedURLException {
    String requested = request.getRequestURL().toString();
    URL requestedUrl = new URL(requested);
    return requestedUrl.getPath();
  }
}
