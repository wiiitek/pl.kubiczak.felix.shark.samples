package pl.kubiczak.felix.shark.samples.security;

import org.apache.commons.lang3.tuple.Pair;

class Authenticator {

	private static final String USERNAME = "admin";

	private static final String PASSWORD = "admin";

	Authority authenticate(String username, String password) {
		Authority result = null;
		if (USERNAME.equals(username) && PASSWORD.equals(password)) {
			result = new Authority(username);
		}
		return result;
	}

	Authority authenticate(Pair<String, String> credentials) {
		return authenticate(credentials.getLeft(), credentials.getRight());
	}
}
