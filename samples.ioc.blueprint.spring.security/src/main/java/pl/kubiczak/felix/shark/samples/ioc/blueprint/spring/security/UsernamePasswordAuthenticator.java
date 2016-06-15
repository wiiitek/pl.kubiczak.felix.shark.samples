package pl.kubiczak.felix.shark.samples.ioc.blueprint.spring.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class UsernamePasswordAuthenticator {

  private final Logger log = LoggerFactory.getLogger(getClass());

  private AuthenticationManager authenticationManager;

  public void setAuthenticationManager(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  public AuthenticationManager getAuthenticationManager() {
    return authenticationManager;
  }

  /**
   * Authenticator that creates a service (see <code>context-osgi.xml</code>)
   * for authenticating users outside of Spring Security.
   * Uses Spring authentication manager.
   *
   * @param username user login
   * @param password user authentication secret
   * @return Spring authentication object or null if credentials are incorrect
   */
  public Authentication authenticate(String username, String password) {
    Authentication authentication = null;
    Authentication usernamePasswordToken =
            new UsernamePasswordAuthenticationToken(username, password);
    try {
      authentication = authenticationManager.authenticate(usernamePasswordToken);
    } catch (AuthenticationException e) {
      log.info("failed authentication for: '{}': {}", username, e.getMessage());
    }
    return authentication;
  }
}
