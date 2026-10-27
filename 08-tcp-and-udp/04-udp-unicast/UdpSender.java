import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

/**
 * Sends one UDP datagram and quits, without waiting for an answer (fire and forget).
 *
 * <p>Usage: {@code java UdpSender.java [message] [host] [port]}, "Hello" to localhost 6438 by
 * default.
 */
public class UdpSender {

  public static void main(String[] args) {
    String message = args.length > 0 ? args[0] : "Hello";
    String host = args.length > 1 ? args[1] : "localhost";
    int port = args.length > 2 ? Integer.parseInt(args[2]) : 6438;

    // No port given: the system chooses a free one for this socket
    try (DatagramSocket socket = new DatagramSocket()) {
      byte[] payload = message.getBytes(StandardCharsets.UTF_8);

      // Each datagram carries its own destination: there is no connection
      DatagramPacket packet =
          new DatagramPacket(payload, payload.length, InetAddress.getByName(host), port);

      socket.send(packet);
      System.out.println("[Sender] Sent " + payload.length + " bytes to " + host + ":" + port);
    } catch (IOException e) {
      System.out.println("[Sender] Error: " + e.getMessage());
    }
  }
}
