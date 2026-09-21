import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class ToUnix {
  static final String EOL = "\n";

  public static void main(String[] args) throws IOException {
    try (BufferedReader reader =
            new BufferedReader(
                new InputStreamReader(new FileInputStream(args[0]), StandardCharsets.UTF_8));
        BufferedWriter writer =
            new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8))) {
      String line;
      while ((line = reader.readLine()) != null) {
        writer.write(line + EOL); // readLine() strips the line ending
      }
    }
  }
}
