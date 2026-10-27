# TCP and UDP programming

Examples for [chapter 08, TCP & UDP
programming](https://heigvd-dai-26.github.io/chapters/08-tcp-and-udp.html).
Each program is a single Java file: run it with `java <File>.java`,
from its directory, with no Maven project. Start the server (or
receiver) first, in one terminal, and the client (or emitter) in a
second one. Stop a server with `Ctrl` + `C`.

| Directory | Programs | Port |
|---|---|---|
| [`01-tcp-echo`](01-tcp-echo) | `EchoServer`, `EchoClient`, `echo_client.py` | TCP 6430 |
| [`02-tcp-file-transfer`](02-tcp-file-transfer) | `FileReceiver`, `FileSender` | TCP 6431 |
| [`03-guess-the-number`](03-guess-the-number) | `GuessServer`, `GuessClient` | TCP 6433 |
| [`04-udp-unicast`](04-udp-unicast) | `UdpReceiver`, `UdpSender` | UDP 6438 |
| [`05-udp-request-response`](05-udp-request-response) | `UdpEchoServer`, `UdpEchoClient` | UDP 6439 |
| [`06-udp-broadcast`](06-udp-broadcast) | `BroadcastReceiver`, `BroadcastEmitter` | UDP 6440 |
| [`07-udp-multicast`](07-udp-multicast) | `TemperatureReceiver`, `TemperatureEmitter` | UDP 6436, group 239.255.1.2 |
| [`08-udp-service-discovery`](08-udp-service-discovery) | `DiscoveryServer`, `DiscoveryClient` | UDP 6441, group 239.255.1.3 |
| [`exercises`](exercises) | `BrokenEchoServer`, `BrokenEchoClient` | TCP 6430 |

## TCP

```sh
# 01: the server sends back each line
cd 01-tcp-echo
java EchoServer.java
java EchoClient.java          # in a second terminal
python3 echo_client.py        # the same client, in Python
ncat localhost 6430           # or type the lines yourself

# 02: send a file; the receiver saves it as received-1.bin, received-2.bin...
cd ../02-tcp-file-transfer
java FileReceiver.java
java FileSender.java FileSender.java

# 03: the "Guess the number" game of chapter 06
cd ../03-guess-the-number
java GuessServer.java         # prints the number to find
java GuessClient.java
```

The servers serve one client at a time. Chapter 09 removes that limit.

## UDP

```sh
# 04: one datagram, no answer
cd 04-udp-unicast
java UdpReceiver.java
java UdpSender.java "Hello from Java"

# 05: a request, an answer, a timeout and retries
cd ../05-udp-request-response
java UdpEchoServer.java
java UdpEchoClient.java "hello"

# 06: one datagram to every machine of the local network
cd ../06-udp-broadcast
java BroadcastReceiver.java
java BroadcastEmitter.java

# 07: sensors that send to a multicast group
cd ../07-udp-multicast
java TemperatureReceiver.java
java TemperatureEmitter.java Kitchen
java TemperatureEmitter.java Bedroom 2

# 08: find the providers of a service
cd ../08-udp-service-discovery
java DiscoveryServer.java guess 6433
java DiscoveryServer.java guess 7000
java DiscoveryClient.java guess
```

Several receivers of 07 and 08 can run on the same machine: start
some in more terminals. 06 takes one receiver per machine: run more on
the machines of neighbours, on the same network.

If a multicast receiver gets nothing, give it the network interface to
use: `java TemperatureReceiver.java en0` on a Mac, `eth0` on Linux and
WSL (`ip addr` or `ifconfig` lists the interfaces). Broadcast and
multicast stay inside the local network, and some networks (public
Wi-Fi, some school networks) block them between machines.

## Exercises

`exercises/` holds a broken echo client and server, for the exercise of
the chapter. They compile and start, and they do not work.
