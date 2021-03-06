package pl.kubiczak.felix.shark.samples.http.servlet.whiteboard;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
    (
        service = Filter.class,
        property = {
            HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT + "="
                + WhiteboardContext.CONTEXT_FILTER,
            HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_PATTERN + "="
                + WhiteboardServlet.SERVLET_PATTERN
        }
    )
public class WhiteboardFilter implements Filter {

  private static final String HTTP_HEADER_NAME = "Whiteboard-Filter-Timestamp";

  private final Logger log = LoggerFactory.getLogger(this.getClass());

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    if (log.isDebugEnabled()) {
      log.debug("init filter with servlet context path: '{}'",
          filterConfig.getServletContext().getContextPath());
    }
  }

  /**
   * Adds timestamp header to a response (for testing purposes).
   */
  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    HttpServletResponse response = (HttpServletResponse) res;
    String timestampString = Long.toString(System.currentTimeMillis());
    response.addHeader(HTTP_HEADER_NAME, timestampString);
    chain.doFilter(req, res);
  }

  @Override
  public void destroy() {
    log.debug("destroying filter...");
  }
}
