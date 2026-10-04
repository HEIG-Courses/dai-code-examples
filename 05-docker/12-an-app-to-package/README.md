# An application to package

A deliberately boring Java application, so that the exercises of chapter
05 have something to put in an image. It prints where it runs, with
which Java, and nothing else.

## Build and run it, without Docker

```sh
mvn clean package
java -jar target/where-am-i-1.0-SNAPSHOT.jar
```

With `--loop`, it keeps printing every second until you stop it, which
is handy to see a container in `docker ps`, read `docker logs` and use
`docker stop`.

```sh
java -jar target/where-am-i-1.0-SNAPSHOT.jar --loop
```

## Then package it

The exercises of the chapter ask you to write the `Dockerfile`, build
the image, run it, publish it, and start it with Docker Compose. Compare
what the application prints on your machine and in the container: the
host name, the Java version and the operating system are not the same.
