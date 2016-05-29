package pl.kubiczak.felix.shark.samples.ioc.blueprint.spring.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(immediate = true)
@Service(value = javax.servlet.Servlet.class)
@Properties({
		@Property(name = HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN, value = ServletImpl.SERVLET_PATTERN)
})
public class ServletImpl extends HttpServlet {

	private static final String PATTERN = "Date = '%tY-%tm-%td %tH%tM%tS.%tL'\nRequestURI = '%s'\nPathInfo = '%s'\nQueryString = '%s'";

	private static int seq = 0;

	static final String SERVLET_PATTERN = "/samples.ioc.blueprint.spring.security/servlet/*";

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		log.debug("remote host: '{}'", req.getRemoteHost());

		res.setContentType("text/plain");
		res.setCharacterEncoding("UTF-8");
		res.setStatus(HttpServletResponse.SC_OK);

		String msg = String.format(PATTERN,
				new Date(),
				req.getRequestURI(),
				req.getPathInfo(),
				req.getQueryString());

		PrintWriter out = res.getWriter();
		out.println(msg);
		out.flush();
	}
}
