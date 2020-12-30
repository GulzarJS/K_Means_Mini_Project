/*
 *  Created by Gulzar Safar on 12/28/2020
 */

package option_1;

import util.Utility;

import java.util.ArrayList;

public class Application {

    public static void main(String[] args) {


        // Number of clusters
        int k = 2;


        ArrayList<ArrayList<Integer>> points = Utility.createData(100);
        ArrayList<ArrayList<Integer>> kCenters = Utility.createKCenters(k);



        // Creating k-means object
        KMeans classifier = new KMeans(points, kCenters,  k, 2);

        // Creating initial clusters
        System.out.println("*** Initial clusters ***");
        classifier.initClusters();
        classifier.printClusters();

        // Doing clustering 1000 times
        for(int i = 0; i < 32; i++) {
            classifier.kMeansStep();
        }
        System.out.println("\n*** Final clusters ***");
        classifier.printClusters();

        // Exporting data to file
        Utility.exportDataToCSV("clusters.data", classifier.toString());

        // Reading data from new file for plotting
//        ArrayList<ArrayList<Float>> newData = Utility.dataForScatterPlot("src/dataset/clusters.data", " ", 5);

    }
}
