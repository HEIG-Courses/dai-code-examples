import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

public class Transcode {
  public static void main(String[] args) {
    if (args.length != 4) {
      System.err.println("Usage: java Transcode.java <input> <input-charset> <output> <output-charset>");
      System.exit(1);
    }
    Charset inCharset = Charset.forName(args[1]);
    Charset outCharset = Charset.forName(args[3]);

    try (BufferedReader reader =
            new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), inCharset));
        BufferedWriter writer =
            new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[2]), outCharset))) {
      int c;
      while ((c = reader.read()) != -1) {
        writer.write(c);
      }
    } catch (IOException e) {
      System.err.println("Error: " + e.getMessage());
      System.exit(1);
    }
  }
}
