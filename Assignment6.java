/*
Jonathan Steininger 4/29/2017 CS-202-18371
This program populates an adjacency matrix with information regarding the 
distances between cities and then uses those values to determine the minimum 
spanning tree between all the cities.
 */
package assignment6;

public class Assignment6 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //int N = 29;
        int[] n = {12, 13, 14, 15, 16, 19, 29};
        for (int i = 0; i < n.length; i++) {
            System.out.println("Number of cities: " + n[i]);
            System.out.println("Path of minimum spanning tree (city, cost): ");
            MST l4 = new MST(n[i]);
            l4.populateMatrix();
            l4.mst();
            System.out.println();
        }//for

    }//main
}
