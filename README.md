
pl.kubiczak.felix.shark.samples
===============================

[![Build Status](https://travis-ci.org/wiiitek/pl.kubiczak.felix.shark.samples.svg?branch=master)](https://travis-ci.org/wiiitek/pl.kubiczak.felix.shark.samples)


Code samples for Shark.

Project site available at [https://shark.kubiczak.pl/samples/][project-site]

Maven artifacts at [http://maven.kubiczak.pl/pl/kubiczak/felix/shark/][custom-maven-repo]

Release instructions
--------------------

The integration tests module (samples.ioc.blueprint.it) can not find some artifacts
as release:prepare doesn't install them in local repo.
For a workaround please use:

    -Darguments=-Dmaven.test.skip=true

[project-site]: https://shark.kubiczak.pl/samples/
[custom-maven-repo]: http://maven.kubiczak.pl/pl/kubiczak/felix/shark/
