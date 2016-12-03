package pl.kubiczak.felix.shark.samples.ioc.blueprint.spring.security;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * Redirect calls for 'login' page that doesn't have trailing '/'.
 * This needs to be a filter as Amdatu is already registered for the path.
 * For URLS different from 'login' Spring security filter chain will be applied.
 * But no for the 'login' page itself as it is available without authentication.
 */
@Component
        (
                property = {
                        HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_PATTERN + "="
                                + LoginPageFilter.PATTERN,
                        HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT + "="
                                + LoginPageFilter.CONTEXT_FILTER
                }
        )
public class LoginPageFilter implements Filter {

  static final String PATTERN = ServletImpl.PATH_PREFIX + "/login";

  // needs to be in the same context as Amdatu servlet
  static final String CONTEXT_FILTER =
          "(" + HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME + "=org.osgi.service.http)";

  private static final String REDIRECT_TO = ServletImpl.PATH_PREFIX + "/login/";

  private final Logger log = LoggerFactory.getLogger(this.getClass());

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    if (log.isDebugEnabled()) {
      log.debug("init filter with servlet context: {}",
              filterConfig.getServletContext().getContextPath());
    }
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
          throws IOException, ServletException {
    if (response instanceof HttpServletResponse) {
      log.debug("redirecting requests for login page without trailing '/'");
      ((HttpServletResponse) response).addHeader("Redirect-Type", "Amdatu Resource");
      ((HttpServletResponse) response).sendRedirect(REDIRECT_TO);
    }
  }

  @Override
  public void destroy() {
    log.debug("destroying filter...");
  }
}
