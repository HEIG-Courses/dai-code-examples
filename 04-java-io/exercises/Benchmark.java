import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Benchmark {
  static final int SIZE = 10 * 1024 * 1024; // 10 MiB

  public static void main(String[] args) throws IOException {
    long start = System.nanoTime();
    try (OutputStream out = new FileOutputStream("data.bin")) {
      for (int i = 0; i < SIZE; i++) {
        out.write(1);
      }
    }
    System.out.println("Unbuffered write: " + ms(start) + " ms");

    start = System.nanoTime();
    try (InputStream in = new FileInputStream("data.bin")) {
      while (in.read() != -1) {}
    }
    System.out.println("Unbuffered read:  " + ms(start) + " ms");

    start = System.nanoTime();
    try (OutputStream out = new BufferedOutputStream(new FileOutputStream("data.bin"))) {
      for (int i = 0; i < SIZE; i++) {
        out.write(1);
      }
    }
    System.out.println("Buffered write:   " + ms(start) + " ms");

    start = System.nanoTime();
    try (InputStream in = new BufferedInputStream(new FileInputStream("data.bin"))) {
      while (in.read() != -1) {}
    }
    System.out.println("Buffered read:    " + ms(start) + " ms");
  }

  static long ms(long start) {
    return (System.nanoTime() - start) / 1_000_000;
  }
}
