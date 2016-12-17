package pl.kubiczak.felix.shark.samples.security;

import org.apache.felix.webconsole.WebConsoleSecurityProvider2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

class WebConsoleAuthHelper {

  private static final String RESPONSE_AUTHENTICATION_HEADER = "WWW-Authenticate";

  // for 'charset' authentication parameter see https://tools.ietf.org/html/rfc7617#section-2.1
  private static final String RESPONSE_AUTHENTICATION_VALUE =
          "Basic realm=\"Sample Security Provider for OSGi Management Console\", charset=\"UTF-8\"";

  private final Logger log = LoggerFactory.getLogger(getClass());

  private final HttpServletRequest request;

  private final HttpServletResponse response;

  WebConsoleAuthHelper(HttpServletRequest request, HttpServletResponse response) {
    this.request = request;
    this.response = response;
  }

  boolean isAuthenticated() {
    return getAuthentication(request) != null;
  }

  void doLogout() {
    if (isAuthenticated()) {
      Authentication authentication = getAuthentication(request);
      if (log.isDebugEnabled()) {
        Object authObject = authentication == null ? null : authentication.getPrincipal();
        log.info("logging out: '{}'", authObject);
      }

      // testing if we could retrieve a session in SecurityContextLogoutHandler
      HttpSession session = request.getSession(false);
      if (session == null) {
        log.warn("error while retrieving session from request: '{}'", request);
      }

      new SecurityContextLogoutHandler().logout(request, response, authentication);
    } else {
      log.debug("there is no authenticated user to log out");
    }
    request.removeAttribute(WebConsoleSecurityProvider2.USER_ATTRIBUTE);
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
  }

  void setupAuthenticationHeaders() {
    log.debug("adding authentication header to response..");
    response.setHeader(RESPONSE_AUTHENTICATION_HEADER, RESPONSE_AUTHENTICATION_VALUE);
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentLength(0);
    try {
      response.flushBuffer();
    } catch (IOException e) {
      log.error("error while flushing buffer for response", e);
    }
  }

  private Authentication getAuthentication(HttpServletRequest request) {
    Object userAttributeValue = request.getAttribute(WebConsoleSecurityProvider2.USER_ATTRIBUTE);
    Authentication result = null;
    if (userAttributeValue instanceof Authentication) {
      result = (Authentication) userAttributeValue;
    }
    return result;
  }
}
