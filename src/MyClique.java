import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;

public class MyClique {

    static MyClique myclique = new MyClique();




    public static void main(String [] args) throws NumberFormatException, IOException{

        System.out.println("Bruteforce for Clique Algorithm");

        //Initializing Variables
        int i;
        int j;
        int n = 0;
        int k;
        int input;
        int p;
        int q;
        int r;
        int s;
        int min;
        int edge;
        int counter = 0;

        //Reading in inpute file
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        String str = "";

        int lineNumber = 0;

        //2D vector to represent adjacency matrix of graph
        Vector<Vector<Integer>> graph = new Vector<Vector<Integer>>();

        while ((str = br.readLine()) != null) {
            // Tokenize every line.
            StringTokenizer st = new StringTokenizer(str);

            // Accessing first number to know the amount of rows and columns involved
            while (st.hasMoreTokens()) {
                int entry = Integer.parseInt(st.nextToken());


                //Uses line number 0 as a constraint
                if(lineNumber==0){
                    n=entry;
                    System.out.println("This graph has "+n+ " vertices.");
                }


                //To capture the number for the rows and columns for the 2 dimensional Vector
                for(i=0; i<n;i++){
                    Vector<Integer> row = new Vector<Integer>(n);
                    for(j=0; j<n; j++){
                        edge = entry;
                        if(edge==0) row.add(1);
                        else row.add(0);
                    }
                    graph.add(row);
                }

                //Finding Neighbours from vector
                //Creating a 2D vector to capture neighbouring vertices data
                Vector<Vector<Integer>> neighbours = new Vector<Vector<Integer>>();

                for(i=0; i<graph.size(); i++){
                    Vector<Integer> neighbour = new Vector<Integer>();
                    Vector vv = graph.get(i);
                    for(j=0; j<graph.get(i).size(); j++){
                        if(graph.get(i).get(j)==1) {
                            neighbour.add(j);
                        }
                        neighbours.add(neighbour);
                    }
                }

                Scanner sc = new Scanner(System.in);

                //To find a maximum clique for watever size the user enters.
                System.out.println("Please enter the numerical size of clique you are looking for: ");
                input=sc.nextInt();

                k = n-input;


                //To find the Clique
                System.out.println("Finding Cliques...");
                boolean found = false;

                min = n+1;

                Vector<Vector<Integer>> covers = new Vector<Vector<Integer>>();
                Vector<Integer> allcover = new Vector<Integer>();

                for(i=0;i<graph.size();i++){
                    allcover.add(1);
                }

                for(i=0;i<allcover.size(); i++){
                    if(found) {
                        break;
                    }
                    System.out.println("Neighbors: "+neighbours.size());
                    counter++;
                    System.out.println(". ");
                    Vector<Integer> cover= new Vector<Integer>();
                    cover.addAll(allcover);
                    System.out.println("Cover: " + cover.size());
                    cover.add(i, 0);
                    System.out.println("Cover: " + cover.size());
                    cover.addAll(Process1(neighbours, cover));
                    System.out.println("Cover: " + cover.size());
                    s=coverSize(cover);
                    System.out.println("Cover: "+cover.size());
                    if(s<min) {
                        min = s;
                    }
                    if(s<=k){
                        System.out.println("Clique ( "+(n-s)+" )");

                        for(j=0; j<cover.size(); j++){
                            if(cover.get(j)==0) {
                                System.out.println((j + 1) + " ");
                            }
                            System.out.println();
                            System.out.println("Clique Size: "+(n-s));
                            covers.add(cover);
                            found=true;
                            break;
                        }

                        for(j=0; j<(n-k); j++) {
                            cover = Process2(neighbours, cover, j);
                        }

                        s=coverSize(cover);
                        if(s<min) {
                            min = s;
                        }

                        for(j=0; j<cover.size(); j++) {
                            if (cover.get(j) == 0) {
                                System.out.println("Clique Size: " + (n - s));
                            }
                        }

                        covers.add(cover);
                        if(s<=k){
                            found = true;
                            break;
                        }


                        //Determining the pairwise disjoint intersections
                        for(p=0; p<covers.size(); p++){
                            if(found) {
                                break;
                            }

                            for(q=p+1; q<covers.size(); q++){
                                if(found) {
                                    break;
                                }
                                counter++;
                                System.out.println(". ");
                                cover =allcover;

                                for(r=0; r<cover.size(); r++) {
                                    if (covers.get(p).get(r) == 0 && covers.get(q).get(r) == 0) {
                                        cover.add(r, 0);
                                    }
                                }
                                cover = myclique.Process1(neighbours, cover);
                                s=coverSize(cover);
                                if(s<min) {
                                    min = s;
                                }
                                if(s<=k){
                                    for(j=0; j<cover.size(); j++) {
                                        if (cover.get(j) == 0) {
                                            System.out.println("Clique Size: " + (n - s));
                                        }
                                    }
                                    found = true;
                                    break;
                                }
                                for (j=0; j<k; j++) {
                                    cover = Process2(neighbours, cover, j);
                                }
                                s=coverSize(cover);
                                if(s<min) {
                                    min = s;
                                }
                                System.out.println("Clique "+(n-s));


                                for(j=0; j<cover.size(); j++) {
                                    if (cover.get(j) == 0) {
                                        System.out.println("");
                                    }
                                }

                                if (s > k) {
                                    continue;
                                }
                                found = true;
                                break;
                            }

                            if(found) {
                                System.out.println("Found Clique of size at least: " + input + ".");
                            }
                            else {System.out.println("Could not find Clique of size at least"+ input+".");
                                System.out.println("Maximum Clique found is " + (n - min) + " .");
                            }

                            return ;

                        }

                    }
                }

            }

            lineNumber++; // Indicates what line we are on.

        }

        // Close the stream.
        br.close();



    }


    //Testing to see if two vertices are detachable
    boolean detachable(Vector<Integer> neighbor,Vector<Integer> cover) {

        boolean check = true;

        for(int i=0; i<neighbor.size(); i++) {
            if (cover.get(neighbor.get(i)) == 0) {
                check = false;
                break;
            }
        }

        return check;
    }


    int maxDetach(Vector<Vector<Integer>> neighbours,Vector<Integer> cover) {

        int r=-1;
        int max =-1;
        for(int i =0; i<cover.size(); i++){

            if(cover.get(i)==1 && myclique.detachable(neighbours.get(i), cover)){
                Vector<Integer> tempCover= new Vector<Integer>();
                tempCover.addAll(cover);
                tempCover.add(i,0);
                int sum =0;

                //int size= tempCover.size();

                for(int j =0; j<tempCover.size(); j++)
                {
                    //System.out.println("For loop entered");
                    //System.out.println("Removable reads "+myclique.removable(neighbours.get(j),tempCover));

                    //Increases the sum of the max clique
                    if ((tempCover.get(j) == 1) && myclique.detachable(neighbours.get(j), tempCover))
                    {
                        sum++;
                        //break;
                    }
                }

                //Adjusts the max clique amount
                if(sum>max){
                    max=sum;
                    r=i;
                } // For If statement 2
               // break;
            }// For If statement 1
            //break;
        }// For "for loop"
        return r;
    }





    static Vector<Integer> Process2(Vector<Vector<Integer>> neighbours, Vector<Integer> cover, int k){
        int count =0;
        Vector<Integer> tempCover = cover;
        int i=0;

        for(i=0;i<tempCover.size(); i++){
            if(tempCover.get(i)==1){
                int sum = 0;
                int index = -1;
                for(int j=0; j<neighbours.get(i).size(); j++) {
                    if (tempCover.get(neighbours.get(i).get(j)) == 0) {
                        index = j;
                        sum++;
                    }
                }
                if(sum==1 && cover.get(neighbours.get(i).get(index))==0){
                    tempCover.add(neighbours.get(i).get(index),1);
                    tempCover.add(i,0);
                    tempCover = myclique.Process1(neighbours, tempCover);
                    count++;
                }
                if(count>k) {
                    break;
                }
            }

        }
        return tempCover;
    }

    static Vector<Integer> Process1(Vector<Vector<Integer>> neighbours, Vector<Integer> cover){

        Vector<Integer> tempCover = new Vector<Integer>();
        tempCover.addAll(cover);
        int r = 0;

        while(r!=-1){
            r = myclique.maxDetach(neighbours, tempCover);
            if(r!=-1) {
                tempCover.add(r, 0);
            }
        }
        return tempCover;
    }

    //Determining Size of the cover
    static int coverSize(Vector<Integer> cover){
        int count = 0;
        for(int i=0; i<cover.size(); i++) {
            if (cover.get(i) == 1) {
                count++;
            }
        }
        return count;
    }

}



