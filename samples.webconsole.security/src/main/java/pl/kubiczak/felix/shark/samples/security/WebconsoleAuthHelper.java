package pl.kubiczak.felix.shark.samples.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.felix.webconsole.WebConsoleSecurityProvider2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

class WebconsoleAuthHelper {

	private static final String RESPONSE_AUTHENTICATION_HEADER = "WWW-Authenticate";

	// for 'charset' authentication parameter see https://tools.ietf.org/html/rfc7617#section-2.1
	private static final String RESPONSE_AUTHENTICATION_VALUE = "Basic realm=\"Sample Security Provider for OSGi Management Console\", charset=\"UTF-8\"";

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final HttpServletRequest httpServletRequest;

	private final HttpServletResponse httpServletResponse;

	WebconsoleAuthHelper(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		this.httpServletRequest = httpServletRequest;
		this.httpServletResponse = httpServletResponse;
	}

	boolean isAuthenticated() {
		return getAuthentication(httpServletRequest) != null;
	}

	void doLogout() {
		if (log.isDebugEnabled()) {
			if (isAuthenticated()) {
				Authentication authentication = getAuthentication(httpServletRequest);
				log.info("logging out: '{}'..", authentication.getPrincipal());
				new SecurityContextLogoutHandler().logout(httpServletRequest, httpServletResponse, authentication);
			} else {
				log.debug("there is no authenticated user to log out");
			}
		}
		httpServletRequest.removeAttribute(WebConsoleSecurityProvider2.USER_ATTRIBUTE);
		httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	}

	void setupAuthenticationHeaders() {
		log.debug("adding authentication header to response..");
		httpServletResponse.setHeader(RESPONSE_AUTHENTICATION_HEADER, RESPONSE_AUTHENTICATION_VALUE);
		httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		httpServletResponse.setContentLength(0);
		try {
			httpServletResponse.flushBuffer();
		} catch (IOException e) {
			log.error("error while flushing buffer for response", e);
		}
	}

	private Authentication getAuthentication(HttpServletRequest request) {
		Object userAttributeValue = request.getAttribute(WebConsoleSecurityProvider2.USER_ATTRIBUTE);
		Authentication result = null;
		if (userAttributeValue instanceof Authentication) {
			result = (Authentication) userAttributeValue;
		}
		return result;
	}
}
