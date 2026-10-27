# SMTP and ncat

Examples for [chapter 07, SMTP &
ncat](https://heigvd-dai-26.github.io/chapters/07-smtp-and-ncat.html):

- `compose.yaml` starts [Mailpit](https://mailpit.axllent.org/), a mock
  SMTP server: it accepts every mail, delivers none, and shows them in a
  web interface.
- `SmtpClient.java` sends one mail to an SMTP server, with the same
  commands you type in ncat.

## Start Mailpit

```sh
docker compose up -d
```

Mailpit listens for SMTP on port `1025`. Its web interface is on
<http://localhost:8025>.

To follow its log (each error it answers), in another terminal:

```sh
docker compose logs -f
```

## Send a mail with ncat

```sh
ncat -C localhost 1025
```

`-C` ends each line with `\r\n`, as SMTP requires. Wait for the `220`
greeting, then type, one line at a time, waiting for each answer:

```text
EHLO my-laptop.local
MAIL FROM:<alice@example.com>
RCPT TO:<bob@example.com>
DATA
From: Alice <alice@example.com>
To: Bob <bob@example.com>
Subject: Hello from ncat

Hi Bob,
this mail was typed by hand.
.
QUIT
```

The server answers `221` to `QUIT` and closes the connection; ncat
stops. The mail is in the web interface.

## Send a mail with Java

```sh
java SmtpClient.java
```

The program prints the exchange (`C:` the client, `S:` the server). To
connect to another server or port:

```sh
java SmtpClient.java <host> <port>
```

## Be the server

In one terminal, listen on port 2525 with ncat:

```sh
ncat -l -C 2525
```

In another one, point the client to it:

```sh
java SmtpClient.java localhost 2525
```

The client's commands appear in the first terminal, and you type the
server's answers, starting with a `220` greeting.

## Stop Mailpit

```sh
docker compose down
```

Mailpit keeps the mails in a temporary database: they are gone after
`down`.
