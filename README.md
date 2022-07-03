# exchange-rate-service

To start

````shell
.\gradlew bootRun
````

To build a docker container

````shell
.\gradlew bootBuildImage
````

To run the image

````shell
 docker run -p 8080:8080 docker.io/library/exchange-rate-service:0.0.1-SNAPSHOT
 ````