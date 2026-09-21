import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * This program shows that Java does not remove the UTF-8 byte order mark (BOM). Some Windows tools
 * (Notepad, Excel "CSV UTF-8") start their files with the bytes EF BB BF. Java decodes them as the
 * invisible character U+FEFF, which ends up at the start of the first line.
 */
class ByteOrderMarkExample {

  public static final char BOM = '\uFEFF';

  public static void main(String[] args) throws IOException {
    // Write a small CSV file starting with a BOM, as Excel does
    try (OutputStream os = new FileOutputStream("with-bom.csv")) {
      os.write(new byte[] {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF});
      os.write("name,age\nAda,36\n".getBytes(StandardCharsets.UTF_8));
    }

    try (BufferedReader br =
        new BufferedReader(
            new InputStreamReader(new FileInputStream("with-bom.csv"), StandardCharsets.UTF_8))) {
      String header = br.readLine();

      System.out.println("Header: " + header);
      System.out.println("Length: " + header.length() + " (\"name,age\" has 8 characters)");
      System.out.println("Starts with \"name\": " + header.startsWith("name"));
      System.out.println("First char: " + String.format("U+%04X", (int) header.charAt(0)));

      // The fix: remove the BOM if it is there
      if (!header.isEmpty() && header.charAt(0) == BOM) {
        header = header.substring(1);
      }
      System.out.println("After removing the BOM, starts with \"name\": " + header.startsWith("name"));
    }
  }
}
