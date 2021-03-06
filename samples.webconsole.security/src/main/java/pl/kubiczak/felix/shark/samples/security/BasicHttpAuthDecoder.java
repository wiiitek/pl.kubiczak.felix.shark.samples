package pl.kubiczak.felix.shark.samples.security;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class BasicHttpAuthDecoder {

  private static final String REQUEST_AUTHORIZATION_HEADER = "Authorization";

  private static final String BASIC_AUTH_PREFIX_UPPERCASE = "BASIC";

  private static final String CREDENTIALS_SEPARATOR = ":";

  private static final int PREFIX_LEN = BASIC_AUTH_PREFIX_UPPERCASE.length();

  private final Logger log = LoggerFactory.getLogger(getClass());

  private final String headerValue;

  BasicHttpAuthDecoder(HttpServletRequest httpServletRequest) {
    this.headerValue = httpServletRequest.getHeader(REQUEST_AUTHORIZATION_HEADER);
  }

  /**
   * Retrieves username and password from Base64 encoded <code>[username]:[password]</code>.
   * Username may not contain ':' character. For more information about encoding please see <a
   * href="https://tools.ietf.org/html/rfc7617#section-2.1">https://tools.ietf.org/html/rfc7617#section-2.1</a>
   *
   * @return username and password or pair of <code>null</code>
   */
  Pair<String, String> retrieveDecodedCredentials() {
    String username = null;
    String password = null;
    String credentials = null;
    if (headerValue != null && hasCorrectPrefix(headerValue)) {
      String encoded = removePrefix(headerValue);
      credentials = decode(encoded);
    }
    if (credentials != null) {
      int index = credentials.indexOf(CREDENTIALS_SEPARATOR);
      if (index == -1) {
        log.debug("wrong format of credentials. missing separator: {}", CREDENTIALS_SEPARATOR);
      } else if (index == 0) {
        log.info("username missing");
        username = StringUtils.EMPTY;
        password = credentials.substring(index + 1);
      } else {
        username = credentials.substring(0, index);
        password = credentials.substring(index + 1);
      }
    }
    log.debug("decoded username: {}", username);
    return new ImmutablePair<>(username, password);
  }

  private boolean hasCorrectPrefix(String headerValue) {
    return headerValue.toUpperCase().startsWith(BASIC_AUTH_PREFIX_UPPERCASE);
  }

  private String removePrefix(String headerValue) {
    return headerValue.substring(PREFIX_LEN);
  }

  private String decode(String encoded) {
    String result = null;
    if (StringUtils.isNotBlank(encoded)) {
      try {
        byte[] bytes = Base64.decodeBase64(encoded);
        result = new String(bytes, StandardCharsets.UTF_8);
      } catch (IllegalArgumentException iae) {
        log.error("Illegal base 64 input: '{}'. Error message: {}", encoded, iae.getMessage());
      }
    }
    return result;
  }
}
