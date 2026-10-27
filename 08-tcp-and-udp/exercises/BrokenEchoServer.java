import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * A TCP server that should send back every line it receives. It does not work: find why.
 *
 * <p>Usage: {@code java BrokenEchoServer.java [port]}, port 6430 by default.
 *
 * <p>The server serves one client at a time: the next client waits until the current one has
 * closed its connection.
 */
public class BrokenEchoServer {

  public static void main(String[] args) {
    int port = args.length > 0 ? Integer.parseInt(args[0]) : 6430;

    // The server socket only accepts connections
    try (ServerSocket serverSocket = new ServerSocket(port)) {
      System.out.println("[Server] Listening on port " + port);

      while (true) {
        // accept() blocks until a client connects, then returns a socket for that client
        try (Socket socket = serverSocket.accept();
            BufferedReader in =
                new BufferedReader(
                    new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            BufferedWriter out =
                new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {
          System.out.println(
              "[Server] Client connected from "
                  + socket.getInetAddress().getHostAddress()
                  + ":"
                  + socket.getPort());

          // readLine() returns null when the client closes the connection
          String line;
          while ((line = in.readLine()) != null) {
            System.out.println("[Server] Received: " + line);
            out.write(line);
          }

          System.out.println("[Server] Client closed the connection");
        } catch (IOException e) {
          System.out.println("[Server] Error with this client: " + e.getMessage());
        }
      }
    } catch (IOException e) {
      System.out.println("[Server] Cannot listen on port " + port + ": " + e.getMessage());
    }
  }
}
