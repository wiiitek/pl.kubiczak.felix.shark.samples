package pl.kubiczak.felix.shark.samples.http.servlet.whiteboard;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@Service
@Properties({
		@Property(name = HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT, value = WhiteboardContext.CONTEXT_FILTER),
		@Property(name = HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN, value = WhiteboardServlet.SERVLET_PATTERN)
})
public class WhiteboardServlet extends HttpServlet {

	static final String SERVLET_PATTERN = "/whiteboardServlet/*";

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private String pattern;

	@Activate
	public void activate() {
		StringBuilder sb = new StringBuilder();
		sb.append("Service = '").append(getClass().getCanonicalName()).append("'\n\n");
		sb.append("Test = 'zażółć gęślą jaźń'\n");
		sb.append("RequestURI = '%s'\nPathInfo = '%s'\nQueryString = '%s'");
		this.pattern = sb.toString();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		log.debug("remote host: '{}'", req.getRemoteHost());

		res.setContentType("text/plain");
		res.setCharacterEncoding("UTF-8");
		res.setStatus(HttpServletResponse.SC_OK);

		String msg = String.format(pattern, req.getRequestURI(), req.getPathInfo(), req.getQueryString());

		PrintWriter out = res.getWriter();
		out.println(msg);
		out.flush();
	}

}
