package pl.kubiczak.felix.shark.samples.http.servlet.whiteboard;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.osgi.service.http.context.ServletContextHelper;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

@Component
@Service(value = ServletContextHelper.class)
@Properties({
		@Property(name = HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME, value = WhiteboardContext.CONTEXT_NAME),
		@Property(name = HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_PATH, value = WhiteboardContext.CONTEXT_PATH)
})
public class WhiteboardContext extends ServletContextHelper {

	static final String CONTEXT_NAME = "pl.kubiczak.felix.shark.samples.http.servlet.whiteboard.WhiteboardContext";

	static final String CONTEXT_FILTER = "(" + HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME + "=" + CONTEXT_NAME + ")";

	static final String CONTEXT_PATH = "/samples.http.servlet.whiteboard";

	public WhiteboardContext() {
		super();
	}
}
