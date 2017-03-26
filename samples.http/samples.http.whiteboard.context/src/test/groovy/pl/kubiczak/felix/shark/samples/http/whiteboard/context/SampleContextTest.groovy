package pl.kubiczak.felix.shark.samples.http.whiteboard.context

import spock.lang.Specification
import spock.lang.Unroll

class SampleContextTest extends Specification {

    private SampleContext tested

    void setup() {
        tested = new SampleContext();
    }

    @Unroll
    def "should generate path with default page '#requestedResource' => '#expected'"() {

        expect:
        tested.withDefaultPage(requestedResource) == expected

        where:
        requestedResource                   | expected
        null                                | null
        ""                                  | "/index.html"
        "/"                                 | "/index.html"
        "/foo"                              | "/foo"
        "////"                              | "/index.html"
        "//foo"                             | "/foo"
        "/foo///bar//"                      | "/foo/bar/index.html"
        // no spaces allowed in resource name
        "foo bar"                           | null
        "/foo bar"                          | null
        "/foo?key=value"                    | "/foo"
        "/bar//?a=1&b=2"                    | "/bar/index.html"
        "foo"                               | "foo"
        "/foo%20bar"                        | null
        "/foo/../bar"                       | "/foo/../bar"
        "../../.."                          | "../../.."
        "!%40%23%24%23%25%5E%24%26%25%24"   | null
        "http://some.url.address.com"       | null
        "https://some.url.address.com/path" | null
        "ftp://some.server.com/path"        | null
        "not an uri :)"                     | null
    }


}
