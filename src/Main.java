/*
 *  Created by Gulzar Safar & Aghateymur Hasanzade on 12/28/2020
 */

import model.*;
import util.Utility;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import org.apache.commons.cli.*;

public class Main {

    public static void main(String[] args) {

        // initialize cli parser
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        Options options = new Options();

        options.addOption("K", true, "k value for creating the date");
        options.addOption("N", true, "creates N points");
        options.addOption("q", "kNumber", true, "check k-s till kNumber");
        options.addOption("l", "algoRun", true, "number of times to run each k in k means algorithm");
        options.addOption("t", "regenCenter", true, "number of times to recreate centers for each k");
        options.addOption("a", "densityWeight", true, "weight of the density in quality formula");
        options.addOption("b", "scatteringWeight", true, "weight of the scattering in quality formula");
        options.addOption("h", "help", false, "help");

        // Starting params. Described above
        int k = 5;
        int N = 100;
        int kNumber = 20;
        int algoRun = 32;
        int regenCenter = 100;

        // Weight of the density and scattering in Quality formula
        float densityWeight = 1, scatteringWeight = 1;

        // Parse cli arguments
        try {
            cmd = parser.parse(options, args);

            if(cmd.hasOption('K')) {
                k = Integer.parseInt(cmd.getParsedOptionValue("K").toString());
            }
            if(cmd.hasOption('N')) {
                N = Integer.parseInt(cmd.getParsedOptionValue("N").toString());
            }
            if(cmd.hasOption('q')) {
                kNumber = Integer.parseInt(cmd.getParsedOptionValue("q").toString());
            }
            if(cmd.hasOption('l')) {
                algoRun = Integer.parseInt(cmd.getParsedOptionValue("l").toString());
            }
            if(cmd.hasOption('t')) {
                regenCenter = Integer.parseInt(cmd.getParsedOptionValue("t").toString());
            }
            if(cmd.hasOption('a')) {
                densityWeight = Float.parseFloat(cmd.getParsedOptionValue("a").toString());
            }
            if(cmd.hasOption('b')) {
                scatteringWeight = Float.parseFloat(cmd.getParsedOptionValue("b").toString());
            }
            if(cmd.hasOption('h')) {
                formatter.printHelp("k means project", options);
                System.exit(0);
            }

        } catch (ParseException e) {
            formatter.printHelp("k means project", options);

            System.exit(1);
        }


        // Entropy of clustering
        double entropy;


        // Creating random centers and data for classification
        ArrayList<Point> kCenters = Utility.createKCenters(k);
        ArrayList<Point> data = Utility.createData(N, kCenters);

        // Dictionary for holding entropies and qualities for each k
        Dictionary<Integer,Float> entropies = new Hashtable<>();
        Dictionary<Integer,Float> qualitites = new Hashtable<>();


        // Looping through k-s
        for(k = 2; k <= kNumber; k++) {

            // List for holding center points of each cluster
            ArrayList<Point> centerOccur = new ArrayList<Point>();

            // Quality for the current k
            float quality = 0;

            // Run k means algorithm l times
            for (int l = 0; l < algoRun; l++) {

                // Initializing new KMeans object
                KMeans classifier = new KMeans(data, k);

                // Creating initial clusters
                classifier.initClusters();

                // Doing clustering regenCenter times
                for(int i = 0; i < regenCenter; i++) {
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

        System.out.println("Entropy: " + entropies.toString());
        System.out.println("Quality: " + qualitites.toString());

    }
}
