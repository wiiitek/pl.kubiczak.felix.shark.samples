package pl.kubiczak.felix.shark.samples.http.servlet.whiteboard;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;

/**
 * This is unsupported in current version of Felix framework.
 * See https://gist.github.com/rotty3000/e39a837364e6eba6bf04
 */
@Component
        (
                service = WhiteboardResources.class,
                immediate = true,
                property = {
                        HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT + "="
                                + WhiteboardContext.CONTEXT_FILTER,
                        HttpWhiteboardConstants.HTTP_WHITEBOARD_RESOURCE_PATTERN + "="
                                + "/resources/*",
                        HttpWhiteboardConstants.HTTP_WHITEBOARD_RESOURCE_PREFIX + "="
                                + "/content"
                }
        )
public class WhiteboardResources extends HttpServlet {
}
