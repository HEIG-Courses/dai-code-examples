import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * A TCP server that saves the bytes a client sends into a file.
 *
 * <p>Usage: {@code java FileReceiver.java [port]}, port 6431 by default.
 *
 * <p>The protocol has no message: the file is every byte that arrives, and it ends when the client
 * closes the connection.
 */
public class FileReceiver {

  public static void main(String[] args) {
    int port = args.length > 0 ? Integer.parseInt(args[0]) : 6431;

    try (ServerSocket serverSocket = new ServerSocket(port)) {
      System.out.println("[Receiver] Listening on port " + port);

      int count = 0;
      while (true) {
        try (Socket socket = serverSocket.accept();
            InputStream in = new BufferedInputStream(socket.getInputStream())) {
          count++;
          String filename = "received-" + count + ".bin";
          System.out.println("[Receiver] Client connected, saving to " + filename);

          try (OutputStream file = new BufferedOutputStream(new FileOutputStream(filename))) {
            // Copies until the end of the stream, that is until the client closes the connection
            long bytes = in.transferTo(file);
            System.out.println("[Receiver] " + bytes + " bytes saved to " + filename);
          }
        } catch (IOException e) {
          System.out.println("[Receiver] Error with this client: " + e.getMessage());
        }
      }
    } catch (IOException e) {
      System.out.println("[Receiver] Cannot listen on port " + port + ": " + e.getMessage());
    }
  }
}
