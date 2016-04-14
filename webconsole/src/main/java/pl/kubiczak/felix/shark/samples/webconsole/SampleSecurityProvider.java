package pl.kubiczak.felix.shark.samples.webconsole;

import org.apache.commons.codec.binary.Base64;
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

	private static final String RESPONSE_AUTHENTICATION_VALUE = "Basic realm=\"Sample Security Provider for OSGi Management Console\"";

	private static final String BASIC_AUTH_PREFIX_UPPER = "BASIC";

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
			final String authorizationHeader = httpServletRequest.getHeader(REQUEST_AUTHORIZATION_HEADER);
			log.debug("authorization header value: {}", authorizationHeader);
			if (authorizationHeader != null && authorizationHeader.toUpperCase().startsWith(BASIC_AUTH_PREFIX_UPPER)) {
				String encoded = authorizationHeader.substring(BASIC_AUTH_PREFIX_UPPER.length());
				log.debug("encoded basic auth value: {}", encoded);
				String decoded = new String(Base64.decodeBase64(encoded));
				int index = decoded.indexOf(":");
				if (index == -1) {
					log.debug("username and password missing");
				} else if (index == 0) {
					log.info("username missing");
				} else {
					String username = decoded.substring(0, index);
					log.debug("username: {}", username);
					String password = decoded.substring(index + 1);
					if (matches(username, password)) {
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

	public boolean authorize(Object o, String s) {
		log.debug("all roles are authorized");
		return true;
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
