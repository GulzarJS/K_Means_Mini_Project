/*
 *  Created by Gulzar Safar on 12/28/2020
 */

package model;

import util.Utility;

import java.util.ArrayList;

public class Application {

    public static void main(String[] args) {


        // Number of clusters
        int k = 3;


        ArrayList<ArrayList<Integer>> points = Utility.createData(100);
        ArrayList<ArrayList<Integer>> kCenters = Utility.createKCenters(k);
        ArrayList<ArrayList<Point>> centerOccur = new ArrayList<ArrayList<Point>>(32);


        // adding new centers to points
        kCenters.forEach(center -> {
            points.add(center);
        });

        // Creating k-means object
        KMeans classifier = new KMeans(points, kCenters,  k, 2);

        // Creating initial clusters
        System.out.println("*** Initial clusters ***");
        classifier.initClusters();
        classifier.printClusters();

        // Doing clustering 32 times
        for(int i = 0; i < 32; i++) {
            classifier.kMeansStep();

            ArrayList<Point> centerList = new ArrayList<Point>(k);
            for (int j = 0; j < k; j++) {
                centerList.add(classifier.getCluster(j).getCenter());

            }
            centerOccur.add(centerList);


        }

        System.out.println("\n*** Final clusters ***");
        classifier.printClusters();

        // print centers
        System.out.println("Centers:");
        System.out.println(centerOccur);

        // Compute entropy of centers of clusters
        System.out.println("Entropy of clusters");
        ArrayList<Float> entropy = Utility.getEntropyOfList(centerOccur);

        entropy.forEach(el -> System.out.println(el.toString()));

        // Exporting data to file
        Utility.exportDataToCSV("clusters.data", classifier.toString());



        // Reading data from new file for plotting
//        ArrayList<ArrayList<Float>> newData = Utility.dataForScatterPlot("src/dataset/clusters.data", " ", 5);

    }
}
