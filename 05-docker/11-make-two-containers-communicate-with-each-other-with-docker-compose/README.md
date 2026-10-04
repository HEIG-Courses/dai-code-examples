# Make two containers communicate with each other with Docker Compose

This Docker Compose example shows how to use Docker networks to make two
containers communicate with each other.

## Build the Docker image

The `ncat` image comes from example 10: build it first if you have not done so,
in the `10-make-two-containers-communicate-with-each-other-with-docker`
directory.

## Run the Docker Compose services

Explore the `docker-compose.yaml` file in this directory. Take some time to
understand what it does.

### Run the first service

Run the first service:

```sh
# Run the first service
docker compose run --rm ncat-server
```

This command runs the `ncat-server` Docker Compose service defined in the
`docker-compose.yaml` file.

Once you have run the command, you should see no output. The container is now
listening for incoming connections on port `1234`.

### Run the second service

Run the second service:

```sh
# Run the second service
docker compose run --rm ncat-client
```

This command runs the `ncat-client` Docker Compose service defined in the
`docker-compose.yaml` file.

Once you have run the command, you should see no output as well. However, if no
errors are displayed, the second container has connected to the first container.

Try typing some text in the second container. You should see the text appear in
the first container.

Both containers are now communicating with each other using the `ncat` tool.

To stop the containers, press `Ctrl+C` in each terminal.

## Starting both services at once

`docker compose up` starts the two services at the same time. The client may
then try to connect before the server is listening, and fail with
`Ncat: Connection refused.`. This is why the client has `restart: on-failure`
in the `docker-compose.yaml`: Docker Compose starts it again until the
connection succeeds. Both containers then exit, because the client sends
nothing and closes the connection.
