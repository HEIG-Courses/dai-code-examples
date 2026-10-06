import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * This program demonstrates how to read a text file character per character and write it to another
 * file character per character. FileReader decodes the bytes of the file into characters with the
 * given charset, and FileWriter encodes them back. It reads the file
 * TextReadAndWriteFileExample.java and writes it to TextReadAndWriteFileExample.txt.
 */
class TextReadAndWriteFileExample {

  public static void main(String[] args) throws IOException {
    Reader reader = new FileReader("TextReadAndWriteFileExample.java", StandardCharsets.UTF_8);
    Writer writer = new FileWriter("TextReadAndWriteFileExample.txt", StandardCharsets.UTF_8);

    // -1 indicates the end of the file
    int c;
    while ((c = reader.read()) != -1) {
      writer.write(c);
    }

    writer.close();
    reader.close();
  }
}
