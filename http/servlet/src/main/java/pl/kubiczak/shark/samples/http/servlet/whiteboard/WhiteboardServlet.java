package pl.kubiczak.shark.samples.http.servlet.whiteboard;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.kubiczak.shark.samples.http.servlet.WhiteboardServletContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@Service
@Properties({
		@Property(name = HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN, value = WhiteboardServlet.SERVLET_PATTERN),
		@Property(name = HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT, value = WhiteboardServletContext.CONTEXT_FILTER)
})
public class WhiteboardServlet extends HttpServlet {

	static final String SERVLET_PATTERN = "/whiteboardServlet/*";

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		log.debug("remote host: {}", req.getRemoteHost());

		res.setContentType("text/plain");
		res.setCharacterEncoding("UTF-8");
		res.setStatus(HttpServletResponse.SC_OK);

		PrintWriter out = res.getWriter();
		out.println("Test = zażółć gęślą jaźń");
		out.println("RequestURI = " + req.getRequestURI());
		out.println("PathInfo = " + req.getPathInfo());
		out.println("QueryString = " + req.getQueryString());
		out.flush();
	}

}
