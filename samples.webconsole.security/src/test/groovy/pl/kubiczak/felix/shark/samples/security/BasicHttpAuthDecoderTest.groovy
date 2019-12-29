package pl.kubiczak.felix.shark.samples.security

import org.apache.commons.lang3.tuple.ImmutablePair
import spock.lang.Specification
import spock.lang.Unroll

import javax.servlet.http.HttpServletRequest

import static org.mockito.Matchers.eq
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

class BasicHttpAuthDecoderTest extends Specification {

    @Unroll
    def "should decode '#headerValue' to '#username' and '#password'"() {

        given:
        HttpServletRequest mockHttpServletRequest = mock(HttpServletRequest.class)
        when(mockHttpServletRequest.getHeader(eq("Authorization"))).thenReturn(headerValue)

        BasicHttpAuthDecoder tested = new BasicHttpAuthDecoder(mockHttpServletRequest)

        expect:
        tested.retrieveDecodedCredentials() == new ImmutablePair<String, String>(username, password)

        where:
        headerValue                                                          | username            | password
        "Basic dXNlcm5hbWU6cGFzc3dvcmQ="                                     | "username"          | "password"
        "Basic dXNlcm5hbWU6UEBzc3cwcmQ="                                     | "username"          | "P@ssw0rd"
        "Basic dXNlcm5hbWU6Q3VTVz91RHBPR2hkXl5HYTdqSFE="                     | "username"          | "CuSW?uDpOGhd^^Ga7jHQ"
        "Basic dXNlcm5hbWU6"                                                 | "username"          | ""
        "Basic OnBhc3N3b3Jk"                                                 | ""                  | "password"
        "Basic Og=="                                                         | ""                  | ""
        "Basic YTrFvA=="                                                     | "a"                 | "ż"
        "Basic emHFvMOzxYLEhzptw7xkZSBiw7h0dGUgwr8="                         | "zażółć"            | "müde bøtte ¿"
        "Basic dGVzdDo6Ojo="                                                 | "test"              | ":::"
        "Basic fiFAIyQlXiYqKClfK3t9fDoiPD4/YDEyMzQ1OjY3ODkwLT1bXVw7JywuLw==" | "~!@#\$%^&*()_+{}|" | "\"<>?`12345:67890-=[]\\;',./"

        "BASIC dXNlcm5hbWU6UEBzc3cwcmQ="                                     | "username"          | "P@ssw0rd"
        "basic dXNlcm5hbWU6UEBzc3cwcmQ="                                     | "username"          | "P@ssw0rd"
        "BAsiC dXNlcm5hbWU6UEBzc3cwcmQ="                                     | "username"          | "P@ssw0rd"

        null                                                                 | null                | null
        ""                                                                   | null                | null
        "A"                                                                  | null                | null
        "Basic"                                                              | null                | null
        "Basic "                                                             | null                | null
        "Basic 123"                                                          | null                | null
        "Basic MDAwMDAwMDA="                                                 | null                | null
        "Adv   dXNlcm5hbWU6UEBzc3cwcmQ="                                     | null                | null
        "      dXNlcm5hbWU6UEBzc3cwcmQ="                                     | null                | null
    }
}
