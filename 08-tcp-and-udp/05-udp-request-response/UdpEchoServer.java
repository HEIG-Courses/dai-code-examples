import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;

/**
 * Answers each UDP datagram with the same text in capitals, sent back to its sender
 * (request-response).
 *
 * <p>Usage: {@code java UdpEchoServer.java [port]}, port 6439 by default.
 */
public class UdpEchoServer {

  private static final int MAX_SIZE = 1024;

  public static void main(String[] args) {
    int port = args.length > 0 ? Integer.parseInt(args[0]) : 6439;

    try (DatagramSocket socket = new DatagramSocket(port)) {
      System.out.println("[Server] Listening on port " + port);

      while (true) {
        byte[] buffer = new byte[MAX_SIZE];
        DatagramPacket request = new DatagramPacket(buffer, buffer.length);
        socket.receive(request);

        String text =
            new String(
                request.getData(), request.getOffset(), request.getLength(), StandardCharsets.UTF_8);
        System.out.println(
            "[Server] Request from " + request.getSocketAddress() + ": " + text);

        // The request carries the address and port of its sender: answer there
        byte[] payload = text.toUpperCase().getBytes(StandardCharsets.UTF_8);
        DatagramPacket response =
            new DatagramPacket(payload, payload.length, request.getSocketAddress());
        socket.send(response);
      }
    } catch (IOException e) {
      System.out.println("[Server] Error: " + e.getMessage());
    }
  }
}
