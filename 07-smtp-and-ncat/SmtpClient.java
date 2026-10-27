import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Sends one email to an SMTP server, with the same commands you type in ncat.
 *
 * <p>Usage: {@code java SmtpClient.java [host] [port]}, by default localhost 1025 (Mailpit).
 *
 * <p>Every line sent ends with CRLF ({@code \r\n}), as RFC 5321 requires. Every reply is read in
 * full: a reply can take several lines, and only its last line has a space after the code.
 */
public class SmtpClient {

  public static void main(String[] args) throws IOException {
    String host = args.length > 0 ? args[0] : "localhost";
    int port = args.length > 1 ? Integer.parseInt(args[1]) : 1025;

    try (Socket socket = new Socket(host, port);
        BufferedReader in =
            new BufferedReader(
                new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        BufferedWriter out =
            new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {

      // The server speaks first
      expect(in, 220);

      send(out, "EHLO my-laptop.local");
      expect(in, 250);

      // The envelope: who sends, who receives
      send(out, "MAIL FROM:<alice@example.com>");
      expect(in, 250);
      send(out, "RCPT TO:<bob@example.com>");
      expect(in, 250);

      // The message: headers, an empty line, the body, then a line with a single dot
      send(out, "DATA");
      expect(in, 354);
      send(out, "From: Alice <alice@example.com>");
      send(out, "To: Bob <bob@example.com>");
      send(out, "Subject: Hello from Java");
      send(out, "");
      send(out, "Hi Bob,");
      send(out, "this mail was sent by a Java program.");
      send(out, ".");
      expect(in, 250);

      send(out, "QUIT");
      expect(in, 221);
    }
  }

  /** Sends one line, ended by CRLF. */
  private static void send(BufferedWriter out, String line) throws IOException {
    System.out.println("C: " + line);
    out.write(line + "\r\n");
    out.flush();
  }

  /** Reads a whole reply and checks its code. Lines "250-..." announce that more lines follow. */
  private static void expect(BufferedReader in, int code) throws IOException {
    String line;
    do {
      line = in.readLine();
      if (line == null) {
        throw new IOException("The server closed the connection");
      }
      System.out.println("S: " + line);
    } while (line.length() > 3 && line.charAt(3) == '-');

    if (!line.startsWith(code + "")) {
      throw new IOException("Expected " + code + ", received: " + line);
    }
  }
}
