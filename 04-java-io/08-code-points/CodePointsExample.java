/**
 * This program shows that a Java char is a UTF-16 unit, not a character. Characters outside the
 * Basic Multilingual Plane (such as emojis) take two chars, so String.length() and charAt() give
 * surprising results. Use code points to work with real characters.
 */
class CodePointsExample {

  public static void main(String[] args) {
    String text = "I😂DAI";

    // Wrong: iterates over UTF-16 units
    System.out.println("The string " + text + " has " + text.length() + " chars:");
    for (int i = 0; i < text.length(); i++) {
      char c = text.charAt(i);
      System.out.println((int) c + "\t" + c);
    }

    System.out.println();

    // Right: iterates over code points
    int[] codePoints = text.codePoints().toArray();
    System.out.println("The string " + text + " has " + codePoints.length + " code points:");
    for (int cp : codePoints) {
      System.out.println(String.format("U+%04X", cp) + "\t" + Character.toString(cp));
    }
  }
}
