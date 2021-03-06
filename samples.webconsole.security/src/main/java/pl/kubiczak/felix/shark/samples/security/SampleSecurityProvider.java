package pl.kubiczak.felix.shark.samples.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.felix.webconsole.WebConsoleSecurityProvider2;
import org.apache.felix.webconsole.WebConsoleSecurityProvider3;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import pl.kubiczak.felix.shark.samples.ioc.blueprint.spring.security.UsernamePasswordAuthenticator;

@Component
public class SampleSecurityProvider implements WebConsoleSecurityProvider3 {

  @Reference
  private UsernamePasswordAuthenticator authenticator;

  private final Logger log = LoggerFactory.getLogger(getClass());

  /**
   * Implements the logout mechanism for user pressing logout button in Felix  web console.
   */
  @Override
  public void logout(HttpServletRequest httpServletRequest,
                     HttpServletResponse httpServletResponse) {
    WebConsoleAuthHelper webConsoleAuthHelper =
        new WebConsoleAuthHelper(httpServletRequest, httpServletResponse);
    if (webConsoleAuthHelper.isAuthenticated()) {
      webConsoleAuthHelper.doLogout();
    }
  }

  /**
   * Decides if request is authenticated and sets basic HTTP authentication headers if no.
   *
   * @return true if user is already authenticated, false otherwise
   */
  @Override
  public boolean authenticate(HttpServletRequest httpServletRequest,
                              HttpServletResponse httpServletResponse) {
    boolean authenticated = false;

    WebConsoleAuthHelper webConsoleAuthHelper =
        new WebConsoleAuthHelper(httpServletRequest, httpServletResponse);
    if (webConsoleAuthHelper.isAuthenticated()) {
      authenticated = true;
    } else {
      BasicHttpAuthDecoder decoder = new BasicHttpAuthDecoder(httpServletRequest);
      Pair<String, String> credentials = decoder.retrieveDecodedCredentials();

      Authentication authentication =
          authenticator.authenticate(credentials.getLeft(), credentials.getRight());

      if (authentication != null) {
        String principal = authentication.getPrincipal().toString();
        log.debug("password correct for: {}", principal);
        httpServletRequest.setAttribute(WebConsoleSecurityProvider2.USER_ATTRIBUTE, authentication);
        log.debug("request attribute set for key: {}", WebConsoleSecurityProvider2.USER_ATTRIBUTE);
        authenticated = true;
        log.debug("user authenticated");
      } else {
        log.warn("wrong password or invalid username: {}", credentials.getLeft());
      }
    }

    if (!authenticated) {
      webConsoleAuthHelper.setupAuthenticationHeaders();
    }
    return authenticated;
  }

  @Override
  public Object authenticate(String username, String password) {
    log.debug("authenticating username '{}'", username);
    return authenticator.authenticate(username, password);
  }

  @Override
  public boolean authorize(Object authentication, String role) {
    log.info("authorizing role {} for auth object {}", role, authentication);
    return true;
  }
}
