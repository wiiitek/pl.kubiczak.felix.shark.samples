package pl.kubiczak.felix.shark.samples.ioc.blueprint.spring;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.Service;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.kubiczak.felix.shark.samples.ioc.blueprint.simple.DateFormatter;

@Component(immediate = true)
@Service(value = javax.servlet.Servlet.class)
@Properties({
		@Property(name = HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN, value = ServletImpl.SERVLET_PATTERN)
})
public class ServletImpl extends HttpServlet {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	static final String SERVLET_PATTERN = "/pl.kubiczak.felix.shark.samples.ioc.blueprint.spring/*";

	private static final String PATTERN = "Date = '%s'";

	private static int seq = 0;

	@Reference(
			cardinality = ReferenceCardinality.OPTIONAL_MULTIPLE,
			referenceInterface = DateFormatter.class
	)
	private List<DateFormatter> formatter = new ArrayList<DateFormatter>();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/plain");
		res.setCharacterEncoding("UTF-8");
		res.setStatus(HttpServletResponse.SC_OK);

		String msg = String.format(PATTERN, format(new Date()));

		PrintWriter out = res.getWriter();
		out.println(msg);
		out.flush();
	}

	private synchronized String format(Date date) {
		String result;
		if (formatter.isEmpty()) {
			result = new Date().toString();
		} else {
			int index = seq++ % formatter.size();
			result = formatter.get(index).getFormatted(date);
		}
		return result;
	}

	protected synchronized void bindFormatter(DateFormatter dateFormatter) {
		log.debug("binding: '{}'..", dateFormatter);
		formatter.add(dateFormatter);
	}

	protected synchronized void unbindFormatter(DateFormatter dateFormatter) {
		log.debug("unbinding: '{}'..", dateFormatter);
		formatter.remove(dateFormatter);
	}
}
