import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;

/**
 * Sends a request to the UDP echo server and waits for the answer. Without an answer within 2
 * seconds, it sends the request again, 3 times at most.
 *
 * <p>Usage: {@code java UdpEchoClient.java [message] [host] [port]}, "hello" to localhost 6439 by
 * default.
 */
public class UdpEchoClient {

  private static final int MAX_SIZE = 1024;
  private static final int TIMEOUT_MS = 2000;
  private static final int MAX_TRIES = 3;

  public static void main(String[] args) {
    String message = args.length > 0 ? args[0] : "hello";
    String host = args.length > 1 ? args[1] : "localhost";
    int port = args.length > 2 ? Integer.parseInt(args[2]) : 6439;

    try (DatagramSocket socket = new DatagramSocket()) {
      // receive() now waits at most TIMEOUT_MS, then throws SocketTimeoutException
      socket.setSoTimeout(TIMEOUT_MS);

      byte[] payload = message.getBytes(StandardCharsets.UTF_8);
      DatagramPacket request =
          new DatagramPacket(payload, payload.length, InetAddress.getByName(host), port);

      for (int attempt = 1; attempt <= MAX_TRIES; attempt++) {
        socket.send(request);
        System.out.println("[Client] Request sent (attempt " + attempt + "): " + message);

        byte[] buffer = new byte[MAX_SIZE];
        DatagramPacket response = new DatagramPacket(buffer, buffer.length);
        try {
          socket.receive(response);
          String text =
              new String(
                  response.getData(),
                  response.getOffset(),
                  response.getLength(),
                  StandardCharsets.UTF_8);
          System.out.println("[Client] Response: " + text);
          return;
        } catch (SocketTimeoutException e) {
          System.out.println("[Client] No response within " + TIMEOUT_MS + " ms");
        }
      }
      System.out.println("[Client] No response after " + MAX_TRIES + " attempts, giving up");
    } catch (IOException e) {
      System.out.println("[Client] Error: " + e.getMessage());
    }
  }
}
