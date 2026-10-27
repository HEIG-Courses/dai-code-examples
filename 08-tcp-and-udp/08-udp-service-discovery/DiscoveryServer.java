import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.nio.charset.StandardCharsets;

/**
 * The provider side of a service discovery by query: listens to a multicast group, and answers a
 * "DISCOVER" query for its service, in unicast, with the port where the service can be reached.
 *
 * <p>Usage: {@code java DiscoveryServer.java [service] [port of the service] [network interface]},
 * by default "guess" on port 6433.
 *
 * <p>Messages, in UTF-8, one per datagram, ending with "\n":
 *
 * <ul>
 *   <li>{@code DISCOVER <service>}: sent by a client to 239.255.1.3, port 6441.
 *   <li>{@code SERVICE <service> <port>}: the answer of each provider of that service, sent in
 *       unicast to the address and port of the query.
 * </ul>
 */
public class DiscoveryServer {

  private static final String GROUP = "239.255.1.3";
  private static final int PORT = 6441;
  private static final int MAX_SIZE = 256;

  public static void main(String[] args) {
    String service = args.length > 0 ? args[0] : "guess";
    int servicePort = args.length > 1 ? Integer.parseInt(args[1]) : 6433;

    try (MulticastSocket socket = new MulticastSocket(PORT)) {
      NetworkInterface networkInterface =
          args.length > 2 ? NetworkInterface.getByName(args[2]) : null;
      if (args.length > 2 && networkInterface == null) {
        System.out.println("[Provider] Unknown network interface: " + args[2]);
        return;
      }
      socket.joinGroup(new InetSocketAddress(GROUP, PORT), networkInterface);
      System.out.println(
          "[Provider] Service " + service + " on port " + servicePort + ", waiting for queries");

      while (true) {
        byte[] buffer = new byte[MAX_SIZE];
        DatagramPacket query = new DatagramPacket(buffer, buffer.length);
        socket.receive(query);

        String message =
            new String(query.getData(), query.getOffset(), query.getLength(), StandardCharsets.UTF_8);
        System.out.println("[Provider] Query from " + query.getSocketAddress() + ": " + message.strip());

        // Answer only the queries for this service; stay silent otherwise
        if (message.equals("DISCOVER " + service + "\n")) {
          byte[] payload =
              ("SERVICE " + service + " " + servicePort + "\n").getBytes(StandardCharsets.UTF_8);
          socket.send(new DatagramPacket(payload, payload.length, query.getSocketAddress()));
        }
      }
    } catch (IOException e) {
      System.out.println("[Provider] Error: " + e.getMessage());
    }
  }
}
