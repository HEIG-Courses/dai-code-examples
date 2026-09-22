import java.io.*;

/**
 * This program copies a file block by block instead of byte by byte. read(byte[]) fills the array
 * as much as it can and returns the number of bytes read, which is often smaller than the array
 * (always at the end of the file). Only these bytes must be written: write(buffer, 0, n). Writing
 * the whole array with write(buffer) would also write the old bytes left at its end.
 */
class BlockCopyExample {

  public static void main(String[] args) throws IOException {
    try (InputStream in = new FileInputStream("BlockCopyExample.java");
        OutputStream out = new FileOutputStream("BlockCopyExample.txt")) {
      byte[] buffer = new byte[64]; // small on purpose, to see several blocks
      int n; // number of bytes read, or -1 at the end of the stream
      while ((n = in.read(buffer)) != -1) {
        System.out.println("Read a block of " + n + " bytes");
        out.write(buffer, 0, n);
      }
    }
  }
}
