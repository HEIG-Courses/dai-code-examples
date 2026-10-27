import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;

/**
 * Receives UDP datagrams on a port and prints them, with the address of their sender.
 *
 * <p>Usage: {@code java UdpReceiver.java [port]}, port 6438 by default.
 */
public class UdpReceiver {

  // The largest datagram this program accepts; a longer one is cut to this size
  private static final int MAX_SIZE = 1024;

  public static void main(String[] args) {
    int port = args.length > 0 ? Integer.parseInt(args[0]) : 6438;

    // A DatagramSocket bound to a port receives every datagram sent to that port
    try (DatagramSocket socket = new DatagramSocket(port)) {
      System.out.println("[Receiver] Listening on port " + port);

      while (true) {
        byte[] buffer = new byte[MAX_SIZE];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        // receive() blocks until a datagram arrives, then fills the packet
        socket.receive(packet);

        // Only the first getLength() bytes of the buffer belong to this datagram
        String message =
            new String(packet.getData(), packet.getOffset(), packet.getLength(), StandardCharsets.UTF_8);

        System.out.println(
            "[Receiver] From "
                + packet.getAddress().getHostAddress()
                + ":"
                + packet.getPort()
                + ": "
                + message);
      }
    } catch (IOException e) {
      System.out.println("[Receiver] Error: " + e.getMessage());
    }
  }
}
