package pl.kubiczak.felix.shark.samples.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.apache.felix.webconsole.WebConsoleSecurityProvider2;
import org.apache.felix.webconsole.WebConsoleSecurityProvider3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@Service
public class SampleSecurityProvider implements WebConsoleSecurityProvider3 {


	private final Logger log = LoggerFactory.getLogger(getClass());

	public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		BasicHttpAuth basicHttpAuth = new BasicHttpAuth(httpServletRequest, httpServletResponse);
		if (basicHttpAuth.isAuthenticated()) {
			basicHttpAuth.doLogout();
		}
	}

	public boolean authenticate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		boolean authenticated = false;

		BasicHttpAuth basicHttpAuth = new BasicHttpAuth(httpServletRequest, httpServletResponse);
		if (basicHttpAuth.isAuthenticated()) {
			authenticated = true;
		} else {
			BasicHttpAuthDecoder decoder = new BasicHttpAuthDecoder(httpServletRequest);
			Pair<String, String> credentials = decoder.retrieveDecodedCredentials();

			Authority authority = new Authenticator().authenticate(credentials);

			if (authority != null) {
				log.debug("password correct for: {}", authority.username);
				httpServletRequest.setAttribute(WebConsoleSecurityProvider2.USER_ATTRIBUTE, authority);
				log.debug("request attribute set for key: {}", WebConsoleSecurityProvider2.USER_ATTRIBUTE);
				authenticated = true;
				log.debug("user authenticated");
			} else {
				log.warn("wrong password or invalid username: {}", credentials.getLeft());
			}
		}

		if (!authenticated) {
			basicHttpAuth.setupAuthenticationHeaders();
		}
		return authenticated;
	}

	public Object authenticate(String username, String password) {
		log.debug("authenticating username '{}'", username);
		Authenticator authenticator = new Authenticator();
		return authenticator.authenticate(username, password);
	}

	public boolean authorize(Object o, String role) {
		log.info("authorizing role {} for auth object {}", role, o);
		return true;
	}
}
