
samples.http.resources.amdatu
=============================

Code samples use `org.osgi.service.component.annotations` to create service
for registering HTTP resources.

Project site available at [http://site.kubiczak.pl/][project-site]

Maven artifacts at [http://maven.kubiczak.pl/][custom-maven-repo]

[project-site]: http://site.kubiczak.pl/
[custom-maven-repo]: http://maven.kubiczak.pl/


Amdatu
------

[Amdatu Web][amdatu-static-resources] that serves static resources requires
[Amdatu resourcehandler bundle][amdatu-resourcehandler-bundle]
in your OSGI container.

After deploying this sample try static resource at

* [http://localhost:8080/samples.http.resources.amdatu/](http://localhost:8080/samples.http.resources.amdatu/)

### Headers

Amdatu Web registers resources with `ResourceRegister` OSGI service.

`ResourceRegister` is a Managed Service (see [source code][resource-register-source]).
The PID for Amdatu Web is `org.amdatu.web.resourcehandler` so a configuration file
should be named

```
org.amdatu.web.resourcehandler.cfg
```

See also `sample.managedservices` submodule of Shark Samples.

#### Cache-Control

By default `Cache-Control` header is set to one week, but this could b configured with
a `cache.timeout` key in configuration file. If the value is negative number then this header is disabled.

#### Content-Type charset

Amdatu uses `Charset.defaultCharset()` for determining resources encoding.
In order to have the correct value for the encoding `-Dfile.encoding=UTF-8`
parameter was added to JAVA start options.

[amdatu-static-resources]: https://bitbucket.org/amdatu/amdatu-web
[amdatu-resourcehandler-bundle]: http://repository.amdatu.org/release/org.amdatu.web.resourcehandler/
[resource-register-source]: https://bitbucket.org/amdatu/amdatu-web/src/master/org.amdatu.web.resourcehandler/src/org/amdatu/web/resourcehandler/ResourceRegister.java
