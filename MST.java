/*
Jonathan Steininger 4/29/2017 CS-202-18371
This program populates an adjacency matrix with information regarding the 
distances between cities and then uses those values to determine the minimum 
spanning tree between all the cities.
 */
package assignment6;

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author Jonathan Steininger
 */
public class MST {

    int CITI;
    int[][] adjacency;
    int bestCost = Integer.MAX_VALUE;

    /**
     * Creates an MST object with N number of cities and an adjacency matrix of
     * size NxN.
     *
     * @param N: number of cities in the path
     */
    public MST(int N) {
        CITI = N;
        adjacency = new int[N][N];
    }

    /**
     * This method reads a file determined by the value of N and populates a
     * matrix with the values read from the file.
     */
    public void populateMatrix() {
        File f = new File("tsp" + CITI + ".txt");
        try {
            Scanner input = new Scanner(f);
            int value, i, j;
            for (i = 0; i < CITI && input.hasNext(); i++) {
                for (j = i; j < CITI && input.hasNext(); j++) {
                    if (i == j) {
                        adjacency[i][j] = 0;
                    } else {
                        value = input.nextInt();
                        adjacency[i][j] = value;
                        adjacency[j][i] = value;
                    }//else
                }//for
            }//for
            //this.displayMatrix();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//populateMatrix

    /**
     * Determines the minimum spanning tree of the cities in the adjacency
     * matrix
     */
    public void mst() {
        Stack pathStack = new Stack();
        boolean[] visitedCities = new boolean[CITI];
        visitedCities[0] = true;
        pathStack.push(0);
        int closestCity = 0;
        boolean minFlag = false;
        //int currentCity = 0;
        int min;
        int total = 0;
        System.out.print("(" + pathStack.peek() + ", 0)");

        while (!pathStack.isEmpty()) {
            //currentCity = (int) pathStack.peek();
            min = Integer.MAX_VALUE;
            for (int i = 1; i < CITI; i++) {
                if (adjacency[(int) pathStack.peek()][i] > 0 && !visitedCities[i]) {
                    if (adjacency[(int) pathStack.peek()][i] < min) {
                        min = adjacency[(int) pathStack.peek()][i];
                        closestCity = i;
                        minFlag = true;
                    }//if
                }//if
            }//for
            if (minFlag) {
                visitedCities[closestCity] = true;
                pathStack.push(closestCity);
                total += min;
                System.out.print(" (" + closestCity + ", " + min + ")");
                minFlag = false;
                continue;
            }//if
            int element = (int) pathStack.pop();
        }//while    
        System.out.println("\nTotal Cost: " + total);
    }//mst

    //END OF RELEVANT CODE
    /**
     * Recursively travels over the possible tours to find the best cost using a
     * depth first search approach and then pruning any subsequent undesirable
     * tours.
     *
     * @param partialTour: the cities in the tour at a given point that have
     * been traveled
     * @param remainingCities: the cities that have not yet been traveled
     */
    public void recdfs(ArrayList<Integer> partialTour, ArrayList<Integer> remainingCities) {
        if (remainingCities.isEmpty()) {
            int currentCost = computeCost(partialTour);
            if (currentCost < bestCost) {
                bestCost = currentCost;
                System.out.println("Tour: " + partialTour + " Cost: " + bestCost);
            }//if
        } else {
            for (int i = 0; i < remainingCities.size(); i++) {
                ArrayList<Integer> newPartialTour = new ArrayList<>(partialTour);
                newPartialTour.add(remainingCities.get(i));
                int currentCost = computeCost(newPartialTour);
                if (currentCost < bestCost) {
                    ArrayList<Integer> newRemainingCities = new ArrayList<>(remainingCities);
                    newRemainingCities.remove(i);
                    recdfs(newPartialTour, newRemainingCities);
                }//if
            }//for
        }//else
    }//recdfs

    /**
     * Computes the cost for the tour so far.
     *
     * @param tour: the cities in the current partial tour
     * @return totalCost: the cost of traveling the cities of tour
     */
    public int computeCost(ArrayList<Integer> tour) {
        int totalCost = 0;
        for (int i = 0; i < tour.size() - 1; i++) {
            totalCost += adjacency[tour.get(i)][tour.get(i + 1)];
        }
        if (tour.size() == CITI) {
            totalCost += adjacency[tour.get(tour.size() - 1)][0];
        }
        return totalCost;
    }//computeCost

    /**
     * Displays the elements in the adjacency matrix. Simply for debugging
     * purposes.
     */
    public void displayMatrix() {
        int i = 0;
        int j = 0;
        for (i = 0; i < adjacency.length; i++) {
            for (j = 0; j < adjacency[i].length; j++) {
                System.out.print(adjacency[i][j] + "\t");
            }
            System.out.println();
        }//for
    }//displayMatrix

}//class

