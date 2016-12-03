
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


Whiteboard Servlet
------------------

[http://localhost:8080/samples.http.servlet.whiteboard/whiteboardServlet/*](http://localhost:8080/samples.http.servlet.whiteboard/whiteboardServlet/*)

* `http://localhost:8080` is the domain and port for the Felix HTTP service
* `/shark/samples/http/servlet/whiteboard` is whiteboard context path
* `/whiteboardServlet/*` is servlet pattern (same pattern is used for filter)
