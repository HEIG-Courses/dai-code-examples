import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

/**
 * Sends one UDP datagram to every machine of the local network (broadcast).
 *
 * <p>Usage: {@code java BroadcastEmitter.java [message] [address] [port]}, "Hello everybody" to
 * 255.255.255.255 port 6440 by default.
 */
public class BroadcastEmitter {

  public static void main(String[] args) {
    String message = args.length > 0 ? args[0] : "Hello everybody";
    String address = args.length > 1 ? args[1] : "255.255.255.255";
    int port = args.length > 2 ? Integer.parseInt(args[2]) : 6440;

    try (DatagramSocket socket = new DatagramSocket()) {
      // A DatagramSocket allows broadcast by default; in C, this option must be set before
      // sending to a broadcast address. Setting it here says what the program intends.
      socket.setBroadcast(true);

      byte[] payload = message.getBytes(StandardCharsets.UTF_8);
      DatagramPacket packet =
          new DatagramPacket(payload, payload.length, InetAddress.getByName(address), port);
      socket.send(packet);
      System.out.println("[Emitter] Sent to " + address + ":" + port + ": " + message);
    } catch (IOException e) {
      System.out.println("[Emitter] Error: " + e.getMessage());
    }
  }
}
