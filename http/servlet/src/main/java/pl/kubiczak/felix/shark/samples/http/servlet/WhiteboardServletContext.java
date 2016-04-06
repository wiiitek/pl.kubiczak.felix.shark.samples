package pl.kubiczak.felix.shark.samples.http.servlet;

import org.osgi.framework.Bundle;
import org.osgi.service.http.context.ServletContextHelper;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

public class WhiteboardServletContext extends ServletContextHelper {

	public static final String CONTEXT_NAME = "pl.kubiczak.shark.samples.http.servlet.whiteboard.WhiteboardServletContext";

	public static final String CONTEXT_FILTER = "(" + HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME + "=" + CONTEXT_NAME + ")";

	static final String CONTEXT_PATH = "/shark/samples/http/servlet/whiteboard";

	WhiteboardServletContext(Bundle bundle) {
		super(bundle);
	}
}
