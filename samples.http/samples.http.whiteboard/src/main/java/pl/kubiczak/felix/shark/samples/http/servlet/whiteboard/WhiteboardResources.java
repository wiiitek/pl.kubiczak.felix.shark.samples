package pl.kubiczak.felix.shark.samples.http.servlet.whiteboard;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

/**
 * This is unsupported in current version of Felix framework.
 */
@Component
        (
                //service = Servlet.class,
                service = WhiteboardResources.class,
                property = {
                        HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT + "="
                                + WhiteboardContext.CONTEXT_FILTER,
                        HttpWhiteboardConstants.HTTP_WHITEBOARD_RESOURCE_PATTERN + "="
                                + "/resources/*",
                        HttpWhiteboardConstants.HTTP_WHITEBOARD_RESOURCE_PREFIX + "="
                                + "/content"
                }
        )
public class WhiteboardResources {
}
