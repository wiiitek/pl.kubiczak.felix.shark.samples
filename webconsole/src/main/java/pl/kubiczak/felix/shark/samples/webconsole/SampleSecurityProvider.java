package pl.kubiczak.felix.shark.samples.webconsole;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.apache.felix.webconsole.WebConsoleSecurityProvider2;
import org.apache.felix.webconsole.WebConsoleSecurityProvider3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Service
public class SampleSecurityProvider implements WebConsoleSecurityProvider3 {

	private static final String USERNAME = "admin";

	private static final String PASSWORD = "admin";

	private static final String REQUEST_AUTHORIZATION_HEADER = "Authorization";

	private static final String RESPONSE_AUTHENTICATION_HEADER = "WWW-Authenticate";

	// for 'charset' authentication parameter see https://tools.ietf.org/html/rfc7617#section-2.1
	private static final String RESPONSE_AUTHENTICATION_VALUE = "Basic realm=\"Sample Security Provider for OSGi Management Console\", charset=\"UTF-8\"";

	private final Logger log = LoggerFactory.getLogger(getClass());

	public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		Auth auth = (Auth) httpServletRequest.getAttribute(WebConsoleSecurityProvider2.USER_ATTRIBUTE);
		if (auth != null) {
			log.debug("logout for username: {}", auth.username);
			httpServletRequest.removeAttribute(WebConsoleSecurityProvider2.USER_ATTRIBUTE);
			httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			log.debug("confirming logout..");
			auth = (Auth) httpServletRequest.getAttribute(WebConsoleSecurityProvider2.USER_ATTRIBUTE);
			assert auth == null;
			if (auth == null) {
				log.debug("logout successful");
			} else {
				log.error("logout error for username: {}", auth.username);
			}
		} else {
			log.debug("logout, but {} attribute is null", WebConsoleSecurityProvider2.USER_ATTRIBUTE);
		}
	}

	public boolean authenticate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		boolean authenticated = false;

		Auth auth = (Auth) httpServletRequest.getAttribute(WebConsoleSecurityProvider2.USER_ATTRIBUTE);
		boolean alreadyAuthenticated = (auth != null);
		log.debug("already authenticated: {}", alreadyAuthenticated);
		if (alreadyAuthenticated) {
			authenticated = true;
		} else {
			String authorizationHeaderValue = httpServletRequest.getHeader(REQUEST_AUTHORIZATION_HEADER);
			Pair<String, String> credentials = new BasicHttpAuth().retrieveDecodedCredentials(authorizationHeaderValue);
			String username = credentials.getLeft();

			if (matches(credentials)) {
				log.debug("password correct for: {}", username);
				Auth newAuth = new Auth(USERNAME);
				httpServletRequest.setAttribute(WebConsoleSecurityProvider2.USER_ATTRIBUTE, newAuth);
				log.debug("request attribute set for key: {}", WebConsoleSecurityProvider2.USER_ATTRIBUTE);
				authenticated = true;
				log.debug("user authenticated");
			} else {
				log.info("wrong password or invalid username: {}", username);
			}
		}

		if (!authenticated) {
			log.debug("adding authentication header to response..");
			httpServletResponse.setHeader(RESPONSE_AUTHENTICATION_HEADER, RESPONSE_AUTHENTICATION_VALUE);
			httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			httpServletResponse.setContentLength(0);
			try {
				httpServletResponse.flushBuffer();
			} catch (IOException e) {
				log.error("error while flushing buffer for response");
			}
		}
		return authenticated;
	}

	public Object authenticate(String username, String password) {
		Object auth = null;
		if (matches(username, password)) {
			log.debug("correct credentials recognized");
			auth = new Auth(USERNAME);
		}
		return auth;
	}

	public boolean authorize(Object o, String role) {
		log.debug("authorizing role {} for auth object {}", role, o);
		return true;
	}

	private boolean matches(Pair<String, String> credentials) {
		String username = credentials.getLeft();
		String password = credentials.getRight();
		return matches(username, password);
	}

	private boolean matches(String username, String password) {
		return USERNAME.equals(username) && PASSWORD.equals(password);
	}

	private class Auth {
		private String username;

		private Auth(String username) {
			this.username = username;
		}
	}
}
