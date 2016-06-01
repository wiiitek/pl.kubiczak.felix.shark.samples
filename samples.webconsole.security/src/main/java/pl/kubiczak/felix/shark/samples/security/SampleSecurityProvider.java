package pl.kubiczak.felix.shark.samples.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.felix.webconsole.WebConsoleSecurityProvider2;
import org.apache.felix.webconsole.WebConsoleSecurityProvider3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import pl.kubiczak.felix.shark.samples.ioc.blueprint.spring.security.UsernamePasswordAuthenticator;

@Component
@Service
public class SampleSecurityProvider implements WebConsoleSecurityProvider3 {

	@Reference
	private UsernamePasswordAuthenticator usernamePasswordAuthenticator;

	private final Logger log = LoggerFactory.getLogger(getClass());

	public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		WebconsoleAuthHelper webconsoleAuthHelper = new WebconsoleAuthHelper(httpServletRequest, httpServletResponse);
		if (webconsoleAuthHelper.isAuthenticated()) {
			webconsoleAuthHelper.doLogout();
		}
	}

	public boolean authenticate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		boolean authenticated = false;

		WebconsoleAuthHelper webconsoleAuthHelper = new WebconsoleAuthHelper(httpServletRequest, httpServletResponse);
		if (webconsoleAuthHelper.isAuthenticated()) {
			authenticated = true;
		} else {
			BasicHttpAuthDecoder decoder = new BasicHttpAuthDecoder(httpServletRequest);
			Pair<String, String> credentials = decoder.retrieveDecodedCredentials();

			Authentication authentication = usernamePasswordAuthenticator.authenticate(credentials.getLeft(), credentials.getRight());

			if (authentication != null) {
				String principal = authentication.getPrincipal().toString();
				log.info("password correct for: {}", principal);
				httpServletRequest.setAttribute(WebConsoleSecurityProvider2.USER_ATTRIBUTE, authentication);
				log.debug("request attribute set for key: {}", WebConsoleSecurityProvider2.USER_ATTRIBUTE);
				authenticated = true;
				log.debug("user authenticated");
			} else {
				log.warn("wrong password or invalid username: {}", credentials.getLeft());
			}
		}

		if (!authenticated) {
			webconsoleAuthHelper.setupAuthenticationHeaders();
		}
		return authenticated;
	}

	public Object authenticate(String username, String password) {
		log.debug("authenticating username '{}'", username);
		return usernamePasswordAuthenticator.authenticate(username, password);
	}

	public boolean authorize(Object o, String role) {
		log.info("authorizing role {} for auth object {}", role, o);
		return true;
	}
}