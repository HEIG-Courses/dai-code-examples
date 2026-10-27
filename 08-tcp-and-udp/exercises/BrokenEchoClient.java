import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * A TCP client that should send three lines to the echo server and print the answers. It does
 * not work: find why.
 *
 * <p>Usage: {@code java BrokenEchoClient.java [host] [port]}, localhost 6430 by default.
 */
public class BrokenEchoClient {

  public static void main(String[] args) {
    String host = args.length > 0 ? args[0] : "localhost";
    int port = args.length > 1 ? Integer.parseInt(args[1]) : 6430;

    try (Socket socket = new Socket(host, port);
        BufferedReader in =
            new BufferedReader(
                new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        BufferedWriter out =
            new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {
      System.out.println("[Client] Connected to " + host + ":" + port);

      for (int i = 1; i <= 3; i++) {
        String line = "Hello " + i;
        out.write(line);
        System.out.println("[Client] Sent: " + line);
        System.out.println("[Client] Received: " + in.readLine());
      }

      System.out.println("[Client] Closing the connection");
    } catch (IOException e) {
      System.out.println("[Client] Error: " + e);
    }
  }
}
