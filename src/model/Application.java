/*
 *  Created by Gulzar Safar & Aghateymur Hasanzade on 12/28/2020
 */

package model;

import util.Utility;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;

public class Application {

    public static void main(String[] args) {


        // Number of clusters
        int k = 5;
        double entropy;


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

            if(centerOccur.contains(centerList) == false) {
                centerOccur.add(centerList);
            }

        }

        entropy = Utility.log2(centerOccur.size());

        System.out.println(entropy);

        System.out.println("\n*** Final clusters ***");
        classifier.printClusters();




//        System.out.println(centerOccur.size());
//        centerOccur.forEach(element -> System.out.println(element.toString()));


        // Exporting data to file
        Utility.exportDataToCSV("clusters.data", classifier.toString());



        // Reading data from new file for plotting
//        ArrayList<ArrayList<Float>> newData = Utility.dataForScatterPlot("src/dataset/clusters.data", " ", 5);

    }
}
