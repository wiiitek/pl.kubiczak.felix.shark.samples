
samples.http.resources
======================

Code samples use `org.apache.felix.scr.annotations` and `maven-scr-plugin`
for managing HTTP Service.

Project site available at [http://site.kubiczak.pl/][project-site]

Maven artifacts at [http://maven.kubiczak.pl/][custom-maven-repo]

[project-site]: http://site.kubiczak.pl/
[custom-maven-repo]: http://maven.kubiczak.pl/


Http Resources
--------------

### Registered with HttpService

[http://localhost:8080/shark/samples/http/resources/index.html](http://localhost:8080/shark/samples/http/resources/index.html)

* `http://localhost:8080` is the domain and port for the Felix HTTP service
* `/shark/samples/http/resources` is a path for Http resources registration

### Amdatu

[http://localhost:8080/shark/samples/http/resources/amdatu/](http://localhost:8080/shark/samples/http/resources/amdatu/)

[Amdatu][amdatu-static-resources] static resources require
[Amdatu resourcehandler bundle][amdatu-resourcehandler-bundle]
in OSGI container.

[amdatu-resourcehandler-bundle]: http://repository.amdatu.org/release/org.amdatu.web.resourcehandler/
[amdatu-static-resources]: http://www.amdatu.org/components/web.html#staticresources
