import socket

# Same steps as EchoClient.java: connect, send lines, read the answers
with socket.create_connection(("localhost", 6430)) as sock:
    with sock.makefile("rw", encoding="utf-8", newline="\n") as stream:
        for i in range(1, 4):
            stream.write(f"Hello {i} from Python\n")
            stream.flush()
            print("Received:", stream.readline().rstrip("\n"))
