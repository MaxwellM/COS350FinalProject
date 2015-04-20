public class LongestCommonSubstring {
// a[i] == b[j] c[i][j] = c[i-1][j-1]+1
// else c[i][j] = max(c[i-1][j], c[i][j-1])
    private static int[][] getArray(char[] a, char[] b) {
        int[][] c = new int[a.length][b.length];
        int[][] o = new int[a.length][b.length];
        for(int i=0;i<a.length;i++){
            for(int j=0;j<b.length;j++){
                if (i==0&&j==0){
                    if (a[i]==b[j]) {
                        c[i][j] = 1;
                        o[i][j] = -2;
                    }
                } else if (i==0) {
                    if (a[i]==b[j]) {
                        o[i][j] = -2;
                        c[i][j] = 1;
                    } else {
                        c[i][j] = c[i][j-1];
                        o[i][j] = -1;
                    }
                } else if (j==0) {
                    if(a[i]==b[j]) {
                        c[i][j] = 1;
                        o[i][j] = -2;
                    } else {
                        c[i][j] = c[i-1][j];
                        o[i][j] = 1;
                    }
                } else {
                    if(a[i]==b[j]) {
                        c[i][j] = c[i-1][j-1]+1;
                        o[i][j] = 2;
                    } else if (c[i-1][j]>= c[i][j-1]){
                        c[i][j] = c[i-1][j];
                        o[i][j] = 1;
                    } else {
                        c[i][j] = c[i][j-1];
                        o[i][j] = -1;
                    }
                }
            }
        }
        printArray(c);
        return o;
    }
    private static void display(int[][] o, char[] a, int i, int j) {
        if(o[i][j] == -2) {
            System.out.print(a[i]+" ");
        } else if(o[i][j]==2) {
            display(o, a, i-1, j-1);
            System.out.print(a[i]+" ");
        } else if (o[i][j] == 1) {
            display(o, a, i-1, j);
        } else if (o[i][j] == -1){
            display(o, a, i, j-1);
        }
    }
    public static void main(String[] args){
        String b = "The quick brown fox jumps over the lazy dog";
        String a = "algorithms class is the best class in the world";
        int[][] c = getArray(a.toCharArray(), b.toCharArray());
        // printArray(c);
        display(c, a.toCharArray(), a.length()-1, b.length()-1);
    }
    private static void printArray(int[][] c){
        for (int[] aC : c) {
            for (int anAC : aC) {
                System.out.print(anAC + " ");
            }
            System.out.println();
        }
    }
}