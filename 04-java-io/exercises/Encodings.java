import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Encodings {
  public static void main(String[] args) {
    // Part 1: one word, four encodings
    String word = "élève";
    show(word, StandardCharsets.US_ASCII);
    show(word, StandardCharsets.ISO_8859_1);
    show(word, StandardCharsets.UTF_8);
    show(word, StandardCharsets.UTF_16);

    byte[] utf8 = word.getBytes(StandardCharsets.UTF_8);
    System.out.println("UTF-8 read as ISO-8859-1: " + new String(utf8, StandardCharsets.ISO_8859_1));
    System.out.println();

    // Part 2: "student" in English, French, Japanese, Ukrainian and Arabic
    String[] students = {"student", "élève", "学生", "студент", "طالب"};
    for (String s : students) {
      System.out.printf(
          "%-8s %d characters, %2d bytes in UTF-8, %2d bytes in UTF-16BE%n",
          s,
          s.codePointCount(0, s.length()),
          s.getBytes(StandardCharsets.UTF_8).length,
          s.getBytes(StandardCharsets.UTF_16BE).length);
    }
  }

  static void show(String text, Charset charset) {
    byte[] bytes = text.getBytes(charset);
    StringBuilder hex = new StringBuilder();
    for (byte b : bytes) {
      hex.append(String.format("%02X ", b));
    }
    System.out.printf("%-10s %2d bytes: %s%n", charset, bytes.length, hex);
  }
}
