/*
 *  Created by Gulzar Safar on 12/28/2020
 */

package option_2;

import util.Utility;

import java.util.ArrayList;
import java.util.Random;

public class Application {

    public static void main(String[] args) {


        Random rand = new Random();

        ArrayList<ArrayList<Integer>> points = new ArrayList<>(100);
        ArrayList<ArrayList<Integer>> kCenters = new ArrayList<>(4);

        int x, y;

        for (int i = 0; i < 100 ; i++) {

            ArrayList<Integer> point = new ArrayList<>(2);

            x = rand.nextInt(1000);
            y = rand.nextInt(400);


            point.add(x);
            point.add(y);

            points.add(point);
        }

        // Creating k-means object
        KMeans classifier = new KMeans(points,  2, 2);

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
