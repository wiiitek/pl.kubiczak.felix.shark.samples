package pl.kubiczak.felix.shark.samples.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.felix.webconsole.WebConsoleSecurityProvider2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class BasicHttpAuth {

	private static final String RESPONSE_AUTHENTICATION_HEADER = "WWW-Authenticate";

	// for 'charset' authentication parameter see https://tools.ietf.org/html/rfc7617#section-2.1
	private static final String RESPONSE_AUTHENTICATION_VALUE = "Basic realm=\"Sample Security Provider for OSGi Management Console\", charset=\"UTF-8\"";

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final HttpServletRequest httpServletRequest;

	private final HttpServletResponse httpServletResponse;

	BasicHttpAuth(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		this.httpServletRequest = httpServletRequest;
		this.httpServletResponse = httpServletResponse;
	}

	boolean isAuthenticated() {
		return retrieve(httpServletRequest) != null;
	}

	String getUsername() {
		Authority authority = retrieve(httpServletRequest);
		return authority != null ? authority.username : null;
	}

	void doLogout() {
		if (log.isDebugEnabled()) {
			if (isAuthenticated()) {
				log.debug("logging out user: '{}'..", getUsername());
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

	private Authority retrieve(HttpServletRequest httpServletRequest) {
		Authority result = null;
		Object userAttributeValue = httpServletRequest.getAttribute(WebConsoleSecurityProvider2.USER_ATTRIBUTE);
		if (userAttributeValue instanceof Authority) {
			result = (Authority) userAttributeValue;
		}
		return result;
	}
}
