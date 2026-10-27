import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * A TCP client that sends a file to the receiver, then closes the connection.
 *
 * <p>Usage: {@code java FileSender.java <file> [host] [port]}, localhost 6431 by default.
 */
public class FileSender {

  public static void main(String[] args) {
    if (args.length < 1) {
      System.out.println("Usage: java FileSender.java <file> [host] [port]");
      return;
    }
    String filename = args[0];
    String host = args.length > 1 ? args[1] : "localhost";
    int port = args.length > 2 ? Integer.parseInt(args[2]) : 6431;

    try (InputStream file = new BufferedInputStream(new FileInputStream(filename));
        Socket socket = new Socket(host, port);
        OutputStream out = new BufferedOutputStream(socket.getOutputStream())) {
      long bytes = file.transferTo(out);
      out.flush();
      System.out.println("[Sender] " + bytes + " bytes of " + filename + " sent to " + host);
    } catch (IOException e) {
      System.out.println("[Sender] Error: " + e);
    }
    // Leaving the try-with-resources closes the socket: the receiver sees the end of the stream
  }
}
