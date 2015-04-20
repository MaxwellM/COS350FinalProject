import org.junit.Test;

/**
 * Created by Maxwell on 4/20/2015.
 */
public class TestLCS {
  String query="acbabba";
  String text="acaccbabbbc";
  @Test
  public void testLCS() {
    for (int i = 0; i < 10000; i++) {
      long startTime = System.nanoTime();
        int[][] c = LongestCommonSubstring.longestCommonSubstring(query.toCharArray(), text.toCharArray());
        LongestCommonSubstring.paint(c, query.toCharArray(), query.length() - 1, text.length() - 1);
      long endTime = System.nanoTime();
      long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
      System.out.println("Execution Time (Milliseconds): " + duration);
    }
  }
}
