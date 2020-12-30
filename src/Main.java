/*
 *  Created by Gulzar Safar & Aghateymur Hasanzade on 12/28/2020
 */

import model.*;
import util.Utility;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class Main {

    public static void main(String[] args) {


        // Number of clusters
        int k = 5;

        // Entropy of clustering
        double entropy;

        // Weight of the density and scattering in Quality formula
        float densityWeight = 1, scatteringWeight = 1;

        // Creating random centers and data for classification
        ArrayList<Point> kCenters = Utility.createKCenters(k);
        ArrayList<Point> data = Utility.createData(100, kCenters);

        // Dictionary for holding entropies and qualities for each k
        Dictionary<Integer,Float> entropies = new Hashtable<>();
        Dictionary<Integer,Float> qualitites = new Hashtable<>();


        // Looping through k-s
        for(k = 2; k <= 20; k++) {

            // List for holding center points of each cluster
            ArrayList<Point> centerOccur = new ArrayList<Point>();

            // Quality for the current k
            float quality = 0;

            // Run k means algorithm l times
            for (int l = 0; l < 32; l++) {

                // Initializing new KMeans object
                KMeans classifier = new KMeans(data, k);

                // Creating initial clusters
                classifier.initClusters();

                // Doing clustering 100 times
                for(int i = 0; i < 100; i++) {
                    classifier.kMeansStep();
                }

                // Loop through each cluster
                for (int j = 0; j < k; j++) {
                    Cluster clust = classifier.getCluster(j);

                    // Add only unique centers
                    if(!centerOccur.contains(clust.getCenter())) {
                        centerOccur.add(clust.getCenter());
                    }

                    // We should only calculate quality once for each k, so do it at the last k means run
                    if (l == 31){
                        quality += clust.getSize()*(densityWeight * clust.getDensity() + scatteringWeight * clust.getScattering());
                    }
                }


                System.out.println("\n*** Final clusters ***");
                classifier.printClusters();

            }

            // Calculation of entropy of clustering
            entropy = Utility.log2(centerOccur.size());

            // Add entropy and quality to their respective dictionaries with the key of k
            entropies.put(k, (float) entropy);
            qualitites.put(k, quality);
        }

        System.out.println("Ent: " + entropies.toString());
        System.out.println("Qual: " + qualitites.toString());

    }
}
