
samples.http.whiteboard.context
===============================

This module creates sample HTTP context and registers resources with a little help of

```
org.osgi.service.http.whiteboard
```

Project site available at [http://site.kubiczak.pl/][project-site]

Maven artifacts at [http://maven.kubiczak.pl/][custom-maven-repo]

[project-site]: http://site.kubiczak.pl/
[custom-maven-repo]: http://maven.kubiczak.pl/


Details
-------


### Whiteboard Servlet

[http://localhost:8080/sample-context/resources/](http://localhost:8080/sample-context/resources/)

* `http://localhost:8080` is the domain and port for the Felix HTTP service
* `/sample-context` is whiteboard context path
* `/sample-context/resources` is a path for resources registration
