import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;

/**
 * The consumer side of a service discovery by query: sends one "DISCOVER" query to a multicast
 * group, then collects the answers of the providers for 2 seconds.
 *
 * <p>Usage: {@code java DiscoveryClient.java [service]}, "guess" by default.
 */
public class DiscoveryClient {

  private static final String GROUP = "239.255.1.3";
  private static final int PORT = 6441;
  private static final int MAX_SIZE = 256;
  private static final int WAIT_MS = 2000;

  public static void main(String[] args) {
    String service = args.length > 0 ? args[0] : "guess";

    // An ordinary DatagramSocket: the answers come back in unicast, to its own port
    try (DatagramSocket socket = new DatagramSocket()) {
      byte[] payload = ("DISCOVER " + service + "\n").getBytes(StandardCharsets.UTF_8);
      socket.send(new DatagramPacket(payload, payload.length, InetAddress.getByName(GROUP), PORT));
      System.out.println("[Consumer] Looking for " + service + "...");

      // Any number of providers may answer, or none: collect answers until the time is up
      long deadline = System.currentTimeMillis() + WAIT_MS;
      int found = 0;
      while (true) {
        long remaining = deadline - System.currentTimeMillis();
        if (remaining <= 0) {
          break;
        }
        socket.setSoTimeout((int) remaining);

        byte[] buffer = new byte[MAX_SIZE];
        DatagramPacket answer = new DatagramPacket(buffer, buffer.length);
        try {
          socket.receive(answer);
        } catch (SocketTimeoutException e) {
          break;
        }

        String message =
            new String(answer.getData(), answer.getOffset(), answer.getLength(), StandardCharsets.UTF_8);
        String[] parts = message.strip().split(" ");
        if (parts.length == 3 && parts[0].equals("SERVICE") && parts[1].equals(service)) {
          found++;
          System.out.println(
              "[Consumer] Found " + service + " at " + answer.getAddress().getHostAddress() + ":" + parts[2]);
        }
      }
      System.out.println("[Consumer] " + found + " provider(s) found");
    } catch (IOException e) {
      System.out.println("[Consumer] Error: " + e.getMessage());
    }
  }
}
