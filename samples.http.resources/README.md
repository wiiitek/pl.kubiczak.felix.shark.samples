
samples.http.resources
======================

Code samples use `org.osgi.service.component.annotations` to create service
for registering HTTP resources.

Project site available at [http://site.kubiczak.pl/][project-site]

Maven artifacts at [http://maven.kubiczak.pl/][custom-maven-repo]

[project-site]: http://site.kubiczak.pl/
[custom-maven-repo]: http://maven.kubiczak.pl/


Http Resources
--------------

### Registered with HttpService

[http://localhost:8080/samples.http.resources/index.html](http://localhost:8080/samples.http.resources/index.html)

* `http://localhost:8080` is the domain and port for the Felix HTTP service
* `/samples.http.resources` is a path for Http resources registration

### Amdatu

[http://localhost:8080/samples.http.resources.amdatu/](http://localhost:8080/samples.http.resources.amdatu/)

[Amdatu][amdatu-static-resources] static resources require
[Amdatu resourcehandler bundle][amdatu-resourcehandler-bundle]
in OSGI container.

[amdatu-resourcehandler-bundle]: http://repository.amdatu.org/release/org.amdatu.web.resourcehandler/
[amdatu-static-resources]: http://www.amdatu.org/components/web.html#staticresources
