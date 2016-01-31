cgarbs-javalib-test
===================

[![Build Status](https://travis-ci.org/mmitch/cgarbs-javalib-test.svg?branch=master)](https://travis-ci.org/mmitch/cgarbs-javalib-test)
[![Coverage Status](https://codecov.io/github/mmitch/cgarbs-javalib-test/coverage.svg?branch=master)](https://codecov.io/github/mmitch/cgarbs-javalib-test?branch=master)
[![GPL 3+](https://img.shields.io/badge/license-GPL%203%2B-blue.svg)](http://www.gnu.org/licenses/gpl-3.0-standalone.html)


about
-----

My own Java framework, test stuff (Hamcrest Matchers etc.).

The project homepage is at <https://github.com/mmitch/cgarbs-javalib-test>

In order to separate runtime and compile/test dependencies to my Java
framework, this package has been split from the other parts of my
[cgarbs-javalib][1] framework.

[1]: <https://github.com/mmitch/cgarbs-javalib>


dependencies
------------

- for using the tests in your projects:
  - at least Java 6
  - JUnit 4/Hamcrest

- for building:
  - Gradle build environment
  - bash for extended build tools (optional)


repository
----------

To use cgarbs-javalib-test in your own Gradle project, use this:

    repositories {
    	maven {
    		url "https://www.cgarbs.de/maven2/"
    	}
    }
    dependencies {
    	testCompile 'de.cgarbs:cgarbs-javalib-test:0.1.0'
    }

Adjust the version number accordingly, this is just an example.  Maven
users should also be able to use this repository, but I don't know the
maven syntax for that.


building with Gradle
--------------------

Building via ``build.gradle`` should be straightforward.

There are some additional build targets available:

* ``version`` shows the current version number that would be used when
  invoking the ``publish`` target

* ``fixit`` runs a bash script to fix line breaks and indentation on
  empty lines.


copyright
---------

cgarbs-javalib-test - my own Java framework, test stuff (Hamcrest Matchers etc.)  
Copyright (C) 2015  Christian Garbs <mitch@cgarbs.de>  
Licensed under GNU GPL v3 (or later)

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
