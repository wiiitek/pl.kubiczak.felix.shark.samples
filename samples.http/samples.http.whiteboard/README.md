
samples.http.servlet
====================

This module creates sample Servlet, Filter and Context with a little help of

```
org.osgi.service.http.whiteboard
```

Project site available at [http://site.kubiczak.pl/][project-site]

Maven artifacts at [http://maven.kubiczak.pl/][custom-maven-repo]

[project-site]: http://site.kubiczak.pl/
[custom-maven-repo]: http://maven.kubiczak.pl/


Whiteboard
----------

[Felix documentation][felix-osgi-http-whiteboard] for Http services whiteboard pattern.

See also [Felix samples][felix-whiteboard-samples] for whiteboard pattern.

Details
-------


### Whiteboard Servlet

[http://localhost:8080/whiteboard/servlet/*](http://localhost:8080/whiteboard/servlet/*)

* `http://localhost:8080` is the domain and port for the Felix HTTP service
* `/whiteboard` is whiteboard context path
* `/*` is servlet pattern (same pattern is used for filter)


### Whiteboard Filter

Adds a `Whiteboard-Filter-Timestamp` header to the servlet response with timestamp value.


### Whiteboard Error Page

Redirects to [/system/console/httpservice](http://localhost:8080/system/console/httpservice) for requests that don't match servlet pattern.

In this sample project works within specified context with path: `/whiteboard`. Try to access following URL:

* [http://localhost:8080/whiteboard/non-existing-resource](http://localhost:8080/whiteboard/non-existing-resource)



[felix-osgi-http-whiteboard]: http://felix.apache.org/documentation/subprojects/apache-felix-http-service.html#using-the-osgi-http-whiteboard
[felix-whiteboard-samples]: https://github.com/apache/felix/tree/trunk/http/samples/whiteboard
