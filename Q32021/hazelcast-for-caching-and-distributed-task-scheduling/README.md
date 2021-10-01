# Hazelcast for caching and distributed task scheduling

*Kushtrim Hajrizi - 01.10.2021*

## Demo

To run the demo, an _Oracle 11g_ database is needed.
A user with the following data *must* be created. If the user data is different, please update the _application.properties_ instead.

* _Username:_ hazelcastexample
* _Password:_ hazelcastexample

You can additionally download and run _Hazelcast Management Center_ and connect it to the created cluster, but this is *not required* to run the demo.

Demo is configured to support up to 3 running instances of the application. Port must be overridden for the other instances if more than 1 will be started.

Documentation at http://localhost:20300/swagger-ui.html