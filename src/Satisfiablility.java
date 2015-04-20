import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.abs;

public class Satisfiablility {
  /**
   * Variables have to be fed in ascending order (1,2,3..)
   */
  public final LinkedList<LinkedList<Integer>> allClauses = new LinkedList<>();

  /* Constructor parses input string and initializes the allClauses */
  public Satisfiablility(String string) {
    allClauses.add(new LinkedList<>());// Initialize clause
    int thisClause = 0;
    int i = 0;
    while (i < string.length()) {
      char thisCharacter = string.charAt(i);
      // If currentChar is a number, add to current clause
      if (isItANumber(thisCharacter)) {
        int number = Character.getNumericValue(thisCharacter);
        // check if variable is negative
        if (i != 0 && string.charAt(i - 1) == '-') {
          number *= (-1);
        }
        allClauses.get(thisClause).add(number);
      }
      // If comma, add one more clause
      else if (thisCharacter == ',') {
        thisClause++;
        allClauses.add(new LinkedList<>());
      }
      i++;
    }
  }

  public boolean isItANumber(char c) {
    return ((int) c > 47 && (int) c < 58);
  }

  public int minimumVariable(LinkedList<LinkedList<Integer>> clauses) {
    int minimum = clauses.peek().peek();
    int i = 0, clausesSize = clauses.size();
    while (i < clausesSize) {
      LinkedList<Integer> clause = clauses.get(i);
      minimum = (abs(clause.peek()) < abs(minimum)) ? clause.peek() : minimum;
      i++;
    }
    return abs(minimum);
  }

  public boolean allLengthOneAndEqual(LinkedList<LinkedList<Integer>> clauses) {
    boolean allLengthSizeOne = true;
    boolean allEqualSize = true;
    int beginning = abs(clauses.peek().peek());
    int i = 0, clausesSize = clauses.size();
    while (i < clausesSize) {
      LinkedList<Integer> clause = clauses.get(i);
      allLengthSizeOne &= (clause.size() == 1);
      allEqualSize &= (beginning == abs(clause.peek()));
      i++;
    }
    return allLengthSizeOne & allEqualSize;
  }


  public boolean sat(LinkedList<LinkedList<Integer>> clauses) {
    /* Copy allClauses */
    LinkedList<LinkedList<Integer>> cloneClauses = clauses.stream().map(clause -> (LinkedList<Integer>) clause.clone()).collect(Collectors.toCollection(LinkedList::new));
    return satZero(cloneClauses) || satOne(clauses);
  }

  /**
   * SecondAnker: When there is only 1 variable pro clause, and all variables are equal
   * if all neg is SAT when last variable = 0 (could also be 1)
   * if all pos is SAT when last variable = 1 (could also be 0)
   * else no SAT
   */
  public boolean ankerTwo(int oneOrZero, LinkedList<LinkedList<Integer>> clauses) {
    boolean allPos = clauses.peek().peek() > 0;
    boolean allNeg = clauses.peek().peek() < 0;
    int zeroOrOne;
    if (oneOrZero == 1) {
      zeroOrOne = 1;
    }
    else {
      zeroOrOne = 0;
    }
    int i = 1;
    while (i < clauses.size()) {
      allPos &= clauses.get(i).peek() > 0;
      allNeg &= clauses.get(i).peek() > 0;
      i++;
    }
    if (allPos) {
      System.out.println("Last variable is 1.");
    }
    else if (allNeg) {
      System.out.println("Last variable is 0.");
    }
    else {
      System.out.println("When replacing with " + zeroOrOne + ", Is not SAT\n");
    }
    return (allPos || allNeg);
  }

  // replace variables with value 1.
  public boolean satOne(LinkedList<LinkedList<Integer>> clauses) {
    if (clauses.isEmpty()) {
      System.out.println("Is SAT, remaining variables can either be TRUE or FALSE.");
      return true;
    }
    else if (allLengthOneAndEqual(clauses)) {
      return ankerTwo(1, clauses);
    }
    else {
      int minVar = minimumVariable(clauses);
      int negMinVar = minVar * (-1);
      System.out.println("satOne Variable x" + minVar + ": 1");
      // Pos. variable->remove clause; Neg. variable->remove variable.
      int i = 0;
      while (i < clauses.size()) {
        if (clauses.get(i).peek() == minVar){
          clauses.remove(i--);
        }
        else if (clauses.get(i).peek() == negMinVar) {
          clauses.get(i).removeFirst(); // neg. variable
          // if clause is empty after replacing with 1. Not SAT for 1.
          if (clauses.get(i).isEmpty()) {
            System.out.println("Empty clause, not SAT with 1.");
            return false;
          }
        }
        i++;
      }
      return satOne(clauses);
    }
  }

  // replace variables with value 0.
  public boolean satZero(LinkedList<LinkedList<Integer>> clauses) {
    if (clauses.isEmpty()) {
      System.out.println("Is SAT, remaining variables can either be TRUE or FALSE.");
      return true;
    }
    else if (allLengthOneAndEqual(clauses)) {
      return ankerTwo(0, clauses);
    }
    else {
      int minLit = minimumVariable(clauses);
      int negMinLit = minLit * (-1);
      System.out.println("satZero Variable x" + minLit + ": 0");
      // Pos. variable->remove variable; Neg. variable->remove clause.
      int i = 0;
      while (i < clauses.size()) {
        if (clauses.get(i).peek() == minLit) {// positive variable
          clauses.get(i).removeFirst();
          //if clause is empty after replacing with 0, clause=0->not SAT.
          if (clauses.get(i).isEmpty()) {
            System.out.println("Empty clause, not SAT with 0.");
            return false;
          }
        }
        else if (clauses.get(i).peek() == negMinLit)// neg. variable
          clauses.remove(i--);
        i++;
      }
      return satZero(clauses);
    }
  }

  public static void main(String[] args) {
    /**Tests**/
    Satisfiablility formel = new Satisfiablility("2 3, -4 5");
    System.out.println(formel.allClauses);
    formel.sat(formel.allClauses);
  }
}