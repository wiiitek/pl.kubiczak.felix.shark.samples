package pl.kubiczak.felix.shark.samples.http.servlet.whiteboard;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.http.context.ServletContextHelper;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

/**
 * Compare also with
 * https://groups.google.com/forum/m/#!msg/bndtools-users/S8fZK_ikkuA/YdnCKBr5AAAJ
 */
@Component
        (
                service = {
                        ServletContextHelper.class,
                        WhiteboardContext.class
                },
                name = WhiteboardContext.CONTEXT_NAME,
                property = {
                        HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME + "="
                                + WhiteboardContext.CONTEXT_NAME,
                        HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_PATH + "="
                                + WhiteboardContext.CONTEXT_PATH
                }
        )
public class WhiteboardContext extends ServletContextHelper {

  static final String CONTEXT_NAME =
          "pl.kubiczak.felix.shark.samples.http.servlet.whiteboard.WhiteboardContext";

  static final String CONTEXT_FILTER =
          "(" + HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME + "=" + CONTEXT_NAME + ")";

  static final String CONTEXT_PATH = "/samples.http.whiteboard";

  public WhiteboardContext() {
    super();
  }
}
