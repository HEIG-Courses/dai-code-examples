# Make two containers communicate with each other with Docker Compose

This Docker Compose example shows how to use Docker networks to make two
containers communicate with each other.

## Build the Docker image

The `ncat` image comes from example 10: build it first if you have not done so,
in the `10-make-two-containers-communicate-with-each-other-with-docker`
directory.

## Run the services

Explore the `compose.yaml` file in this directory. Take some time to
understand what it does.

You need **two terminals**: the server keeps running in the first one, and you
type in the second one.

### Terminal 1: start the server

```sh
# Start the server, and stay attached to see what it receives
docker compose up ncat-server
```

The server is now listening on port `1234`. Nothing else is printed yet.

### Terminal 2: start the client

```sh
# Start the client
docker compose run --rm ncat-client
```

The client connects to the server by its name, `my-server`, on the network
declared in `compose.yaml`. No output means the connection worked.

Type some text, press Enter, and watch it appear in the first terminal:

```text
ncat-server-1  | hello from the server next door
```

The two containers are talking to each other over the Docker network.

Press `Ctrl+C` in the client, then in the server. The server also stops on its
own once the client disconnects, because `ncat -l` serves one connection and
exits.

### Clean up

```sh
# Remove the containers and the network
docker compose down
```

## Starting both services at once

`docker compose up` starts the two services at the same time. The client may
then try to connect before the server is listening, and fail with
`Ncat: Connection refused.`. This is why the client has `restart: on-failure`
in the `compose.yaml`: Docker Compose starts it again until the
connection succeeds. Both containers then exit, because the client sends
nothing and closes the connection.
