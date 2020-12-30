/*
 *  Created by Gulzar Safar & Aghateymur Hasanzade on 12/28/2020
 */

package model;

import util.Utility;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class Application {

    public static void main(String[] args) {


        // Number of clusters
        int k = 5;
        double entropy;

        float densityWeight = 1, scatteringWeight = 1;


        ArrayList<Point> kCenters = Utility.createKCenters(k);
        ArrayList<Point> data = Utility.createData(100, kCenters);

        Dictionary<Integer,Float> entropies = new Hashtable<>();
        Dictionary<Integer,Float> qualitites = new Hashtable<>();




        for(k = 2; k <= 20; k++) {
            ArrayList<Point> centerOccur = new ArrayList<Point>();

            float quality = 0;

            for (int l = 0; l < 32; l++) {

                KMeans classifier = new KMeans(data, k);

                // Creating initial clusters
                classifier.initClusters();

                // Doing clustering 100 times
                for(int i = 0; i < 100; i++) {
                    classifier.kMeansStep();
                }

                for (int j = 0; j < k; j++) {
                    Cluster clust = classifier.getCluster(j);
                    if(!centerOccur.contains(clust.getCenter())) {
                        centerOccur.add(clust.getCenter());
                    }

                    if (l == 31){
                        quality += clust.getSize()*(densityWeight * clust.getDensity() + scatteringWeight * clust.getScattering());
                    }
                }


                System.out.println("\n*** Final clusters ***");
                classifier.printClusters();

            }


            entropy = Utility.log2(centerOccur.size());

            entropies.put(k, (float) entropy);
            qualitites.put(k, quality);

//            System.out.println("Ent: " + entropy);
//            System.out.println("Qual: " + quality);
        }

        System.out.println("Ent: " + entropies.toString());
        System.out.println("Qual: " + qualitites.toString());

    }
}
