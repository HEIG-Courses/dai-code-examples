# Docker and Docker Compose: code examples

Examples for [chapter 05, Docker and Docker
Compose](https://heigvd-dai-26.github.io/chapters/05-docker.html). Try them in
this order and read the README of each one:

1. [Basic Dockerfile](01-basic-dockerfile): `FROM` and `WORKDIR`, the
   smallest image you can build.
2. [Dockerfile with command](02-dockerfile-with-command): `CMD`, the
   default command of the container, and how `docker run` overrides it.
3. [Dockerfile with entrypoint and command](03-dockerfile-with-entrypoint-and-command):
   `ENTRYPOINT` and `CMD` together, and the difference between them.
4. [Dockerfile with run and copy](04-dockerfile-with-run-and-copy):
   `RUN` to execute a command at build time, `COPY` to bring files into
   the image.
5. [Dockerfile with build arguments](05-dockerfile-with-build-arguments):
   `ARG`, a value given at build time.
6. [Basic Docker Compose](06-basic-docker-compose): `services` and
   `image`, two containers described in one file.
7. [Docker Compose with ports](07-docker-compose-with-ports): publish a
   container port on your machine, with nginx.
8. [Docker Compose with volumes](08-docker-compose-with-volumes): share
   files between your machine and the container.
9. [Docker Compose with environment variables](09-docker-compose-with-environment-variables):
   configure a container without rebuilding its image.
10. [Two containers talking to each other, with Docker](10-make-two-containers-communicate-with-each-other-with-docker):
    a Docker network, and one container reaching the other by name.
11. [Two containers talking to each other, with Docker Compose](11-make-two-containers-communicate-with-each-other-with-docker-compose):
    the same, with the network Compose creates for you.
12. [An application to package](12-an-app-to-package): a small Java
    application, used by the exercises of the chapter.

## Usage

Build an image from a directory that has a `Dockerfile`:

```sh
cd 02-dockerfile-with-command
docker build -t my-image .
docker run --rm my-image
```

Start the services of a directory that has a `compose.yaml`:

```sh
cd 06-basic-docker-compose
docker compose up
docker compose down
```
