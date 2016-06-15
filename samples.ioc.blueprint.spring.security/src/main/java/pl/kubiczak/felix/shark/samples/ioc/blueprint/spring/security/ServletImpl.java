package pl.kubiczak.felix.shark.samples.ioc.blueprint.spring.security;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component(immediate = true)
@Service(value = javax.servlet.Servlet.class)
@Properties
        ({
                @Property(
                        name = HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN,
                        value = ServletImpl.SERVLET_PATTERN)
        })
public class ServletImpl extends HttpServlet {

  static final String DATE_PATTERN = "%1$tY-%1$tm-%1$td %1$tH.%1$tM.%1$tS.%1$tL";

  private static final String RESPONSE_PATTERN =
          "Date = '%1s'\nRequestURI = '%2s'\nPathInfo = '%3s'\nQueryString = '%4s'";

  private static int seq = 0;

  static final String SERVLET_PATTERN = "/samples.ioc.blueprint.spring.security/servlet/*";

  private final Logger log = LoggerFactory.getLogger(this.getClass());

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res)
          throws ServletException, IOException {

    log.debug("remote host: '{}'", req.getRemoteHost());

    res.setContentType("text/plain");
    res.setCharacterEncoding("UTF-8");
    res.setStatus(HttpServletResponse.SC_OK);

    String dateString = String.format(DATE_PATTERN, new Date());
    String msg = String.format(RESPONSE_PATTERN,
            dateString,
            req.getRequestURI(),
            req.getPathInfo(),
            req.getQueryString());

    PrintWriter out = res.getWriter();
    out.println(msg);
    out.flush();
  }
}
