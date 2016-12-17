package pl.kubiczak.felix.shark.samples.ioc.blueprint.spring.security;

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
                                + ServletImpl.CONTEXT_FILTER,
                        HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN + "="
                                + ServletImpl.SERVLET_PATTERN
                }

        )
public class ServletImpl extends HttpServlet {

  /**
   * Http context for Spring Security sample.
   * The same context as Amdatu uses is used here.
   */
  public static final String CONTEXT_NAME = "org.osgi.service.http";

  static final String DATE_PATTERN = "%1$tY-%1$tm-%1$td %1$tH.%1$tM.%1$tS.%1$tL";

  static final String SERVLET_PATTERN = "/spring.security/*";

  static final String CONTEXT_FILTER =
          "(" + HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME + "=" + CONTEXT_NAME + ")";

  private static final String RESPONSE_PATTERN =
          "Date = '%1s'\nRequestURI = '%2s'\nPathInfo = '%3s'\nQueryString = '%4s'";

  private final transient Logger log = LoggerFactory.getLogger(this.getClass());

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res)
          throws ServletException, IOException {

    log.debug("remote host: '{}'", req.getRemoteHost());

    res.setContentType("text/plain");
    res.setCharacterEncoding("UTF-8");
    res.setStatus(HttpServletResponse.SC_OK);

    String dateString = String.format(DATE_PATTERN, new Date());
    String msg = String.format(RESPONSE_PATTERN,
            dateString,
            req.getRequestURI(),
            req.getPathInfo(),
            req.getQueryString());

    try (PrintWriter out = res.getWriter()) {
      out.println(msg);
      out.flush();
    } catch (IllegalStateException | IOException exception) {
      log.error("error while getting writer: {}", exception.getMessage(), exception);
    }
  }
}
