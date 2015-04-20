import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Maxwell on 4/20/2015.
 */
public class TestSat {
  @Test
  @Repeat(1000)
  public void testSat() {
    for (int i = 0; i < 1000; i++) {
      long startTime = System.nanoTime();
      Satisfiablility sat = new Satisfiablility("2 3, -4 5");
      assertEquals(sat.cnf_sat(sat.clauses), sat.cnf_sat(sat.clauses));
      long endTime = System.nanoTime();
      long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
      System.out.println("Execution Time (Milliseconds): " + duration);
    }
  }
}
