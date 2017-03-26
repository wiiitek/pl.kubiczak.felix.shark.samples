package pl.kubiczak.felix.shark.samples.http.whiteboard.context;

import org.apache.commons.lang3.StringUtils;
import org.osgi.framework.Bundle;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.context.ServletContextHelper;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;
import org.owasp.esapi.ESAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Set;

/**
 * See http://blog.osoco.de/2016/10/http-whiteboard-simply-simple-part-iii/
 */
@Component(
        service = {
                ServletContextHelper.class,
                SampleContext.class
        },
        scope = ServiceScope.BUNDLE,
        property = {
                HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME + "="
                        + SampleContext.CONTEXT_NAME,
                HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_PATH + "="
                        + SampleContext.CONTEXT_PATH
        })
public class SampleContext extends ServletContextHelper implements HttpContext {

  static final String CONTEXT_NAME =
          "pl.kubiczak.felix.shark.samples.http.whiteboard.context.SampleContext";

  static final String CONTEXT_PATH = "/sample-context/*";

  private static final String DEFAULT_PAGE = "index.html";

  private final Logger log = LoggerFactory.getLogger(this.getClass());

  private ServletContextHelper helper;

  /**
   * Creates Whiteboard HTTP context.
   *
   * @param componentContext current comoponent context
   */
  @Activate
  private void activate(final ComponentContext componentContext) {
    Bundle bundle = componentContext.getUsingBundle();
    if (bundle == null) {
      log.error("error while getting bundle for current context: '{}'", componentContext);
    } else {
      helper = new ServletContextHelper(bundle) {
      };
      log.debug("context created for bundle: '{}'", bundle);
    }
  }

  @Override
  public URL getResource(String name) {
    String pathWithDefaultPage = withDefaultPage(name);
    return helper.getResource(pathWithDefaultPage);
  }

  @Override
  public String getMimeType(String name) {
    return helper.getMimeType(name);
  }

  @Override
  public Set<String> getResourcePaths(String path) {
    return helper.getResourcePaths(path);
  }

  @Override
  public String getRealPath(String path) {
    return helper.getRealPath(path);
  }

  String withDefaultPage(String name) {
    final String result;
    if (name == null) {
      result = null;
      log.warn("resource name should not be null");
    } else if (StringUtils.EMPTY.equals(name)) {
      result = "/" + DEFAULT_PAGE;
    } else {
      String canonical = canonicalize(name);
      String uriPath = validateUriPath(canonical);
      result = appendDefaultPageIfNeeded(uriPath);
    }
    return result;
  }

  private String canonicalize(String name) {
    String canonical = ESAPI.encoder().canonicalize(name);
    log.debug("canonical name: '{}'", canonical);
    String singleSlashes = canonical.replaceAll("/+", "/");
    log.debug("single slashes: '{}'", singleSlashes);
    return singleSlashes;
  }

  private String validateUriPath(String name) {
    String uriPath = null;
    try {
      URI dirtyUri = new URI(name);
      String dirtyPath = dirtyUri.getPath();
      log.debug("path before canonicalization: '{}'", dirtyPath);

      if (StringUtils.isEmpty(dirtyUri.getHost()) && StringUtils.isEmpty(dirtyUri.getScheme())) {
        uriPath = dirtyPath;
      } else {
        uriPath = null;
      }
    } catch (URISyntaxException urise) {
      log.error("error while decoding path: '{}'", name, urise);
    }
    return uriPath;
  }

  private String appendDefaultPageIfNeeded(String canonicalUriPath) {
    final String result;
    if (canonicalUriPath == null) {
      result = null;
    } else if (canonicalUriPath.endsWith("/")) {
      result = canonicalUriPath + DEFAULT_PAGE;
    } else {
      result = canonicalUriPath;
    }
    return result;
  }
}
