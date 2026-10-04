package ch.heigvd.dai;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * A deliberately boring application, so that chapter 05 has something to package with Docker. It
 * prints where it runs and with which Java, which is enough to see the difference between your
 * machine and a container.
 */
public class WhereAmI {

  public static void main(String[] args) throws InterruptedException {
    System.out.println("Hello from " + hostName() + "!");
    System.out.println("Java version:      " + System.getProperty("java.version"));
    System.out.println("Operating system:  " + System.getProperty("os.name"));
    System.out.println("Architecture:      " + System.getProperty("os.arch"));
    System.out.println("Working directory: " + System.getProperty("user.dir"));
    System.out.println("Arguments:         " + String.join(" ", args));

    // With --loop, the application keeps running, so you can see it in `docker ps`,
    // read its output with `docker logs` and stop it with `docker stop`.
    if (args.length > 0 && args[0].equals("--loop")) {
      int seconds = 0;
      while (true) {
        Thread.sleep(1000);
        System.out.println("Still here, " + (++seconds) + " s");
      }
    }
  }

  private static String hostName() {
    try {
      return InetAddress.getLocalHost().getHostName();
    } catch (UnknownHostException e) {
      return "an unknown host";
    }
  }
}
