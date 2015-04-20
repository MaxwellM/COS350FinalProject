public class LongestCommonSubstring {
  public static int total =0;

  public static int[][] longestCommonSubstring(char[] query, char[] text) {
    int[][] queryArray = new int[query.length][text.length];
    int[][] textArray = new int[query.length][text.length];
    int i=0;
    //Loop through our query length
    while (i<query.length) {
      int j=0;
      //Loops through our database length
      while (j<text.length) {
        if (i==0&&j==0){
          if (query[i]==text[j]) {
            queryArray[i][j] = 1;
            textArray[i][j] = -2;
            total += 1;
          }
        }
        else if (i==0) {
          if (query[i]==text[j]) {
            textArray[i][j] = -2;
            queryArray[i][j] = 1;
            total += 1;
          }
          else {
            queryArray[i][j] = queryArray[i][j-1];
            textArray[i][j] = -1;
          }
        }
        else if (j==0) {
          if(query[i]==text[j]) {
            queryArray[i][j] = 1;
            textArray[i][j] = -2;
            total += 1;
          }
          else {
            queryArray[i][j] = queryArray[i-1][j];
            textArray[i][j] = 1;
          }
        }
        else {
          if(query[i]==text[j]) {
            queryArray[i][j] = queryArray[i-1][j-1]+1;
            textArray[i][j] = 2;

          }
          else if (queryArray[i-1][j]>= queryArray[i][j-1]){
            queryArray[i][j] = queryArray[i-1][j];
            textArray[i][j] = 1;
          }
          else {
            queryArray[i][j] = queryArray[i][j-1];
            textArray[i][j] = -1;
          }
        }
        //Increment j
        j++;
      }
      //Increment i
      i++;
    }
    return textArray;
  }

  public static void paint(int[][] allArrays, char[] query, int i, int j) {
    if(allArrays[i][j] == -2) {
      System.out.print(query[i]+" ");
    } else if(allArrays[i][j]==2) {
      paint(allArrays, query, i - 1, j - 1);
      System.out.print(query[i]+" ");
    } else if (allArrays[i][j] == 1) {
      paint(allArrays, query, i - 1, j);
    } else if (allArrays[i][j] == -1){
      paint(allArrays, query, i, j - 1);

    }
  }

  public static void main(String[] args){
    String query="acbabba";
    String text="acaccbabbbc";
    int[][] c = longestCommonSubstring(query.toCharArray(), text.toCharArray());
    paint(c, query.toCharArray(), query.length() - 1, text.length() - 1);
    System.out.println("\n"+"Total: "+total);
  }
}