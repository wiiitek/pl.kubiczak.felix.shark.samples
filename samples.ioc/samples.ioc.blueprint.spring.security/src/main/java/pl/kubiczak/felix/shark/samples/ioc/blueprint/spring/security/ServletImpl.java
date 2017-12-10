package pl.kubiczak.felix.shark.samples.ioc.blueprint.spring.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.Date;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

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

  static final String SERVLET_PATTERN = "/samples.ioc.blueprint.spring.security/*";

  static final String CONTEXT_FILTER =
      "(" + HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME + "=" + CONTEXT_NAME + ")";

  private static final String RESPONSE_PATTERN =
      "User = '%1s'\nDate = '%2s'\nRequestURI = '%3s'\nPathInfo = '%4s'\nQueryString = '%5s'";

  private final transient Logger log = LoggerFactory.getLogger(this.getClass());

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {

    log.debug("remote host: '{}'", req.getRemoteHost());

    res.setContentType("text/plain");
    res.setCharacterEncoding("UTF-8");
    res.setStatus(HttpServletResponse.SC_OK);

    SecurityContext securityContext = SecurityContextHolder.getContext();
    log.debug("spring security context: '{}'", securityContext);
    String dateString = String.format(DATE_PATTERN, new Date());
    String msg = String.format(RESPONSE_PATTERN,
        retrieveSpringUserId(securityContext, req),
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

  private String retrieveSpringUserId(SecurityContext securityContext, HttpServletRequest req) {
    String user = null;

    Authentication authentication = securityContext.getAuthentication();
    if (authentication == null) {
      log.warn("there is no authentication object for currently logged user");
    } else {
      user = authentication.getName();
    }
    // tries to get user from request
    if (user == null) {
      Principal principal = req.getUserPrincipal();
      user = principal.getName();
    }
    return user;
  }
}
