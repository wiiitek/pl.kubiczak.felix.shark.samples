samples.managedservices
=======================

Project site available at [http://site.kubiczak.pl/][project-site]

Maven artifacts at [http://maven.kubiczak.pl/][custom-maven-repo]

[project-site]: http://site.kubiczak.pl/
[custom-maven-repo]: http://maven.kubiczak.pl/

Managed Services
----------------

Managed services may be [configured with the use of felix fileinstall][managed-fileinstall].

See also [Felis fileinstall documentation][felix-fileinstall-conf].

In order to test this sample module copy the
`pl.kubiczak.felix.shark.samples.managedservices.Service.cfg`
into a folder that is observed by fileinstall
(i.e. `.../felix-framework/load/level-20-samples/`)
and watch the log file for updated configuration messages.

To escape UTF-8 values in `.cfg` file use `native2ascii` tool:

* [http://stackoverflow.com/a/4660195](http://stackoverflow.com/a/4660195)

[managed-fileinstall]: http://www.codeaffine.com/2013/10/25/configure-your-osgi-services-with-apache-felix-file-install/
[felix-fileinstall-conf]: http://felix.apache.org/documentation/subprojects/apache-felix-file-install.html#setup
