/*
 *  Created by Gulzar Safar on 12/28/2020
 */

package util;


import model.Point;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Utility {

    private static Random rand = new Random();


    /**
     * Function to create new csv file and write all data into it
     */
    public static void exportDataToCSV(String filename, String data) {
        try {
            Writer out = new BufferedWriter(new FileWriter(filename));
            out.write(data);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function to find mean of population (number => (x or y))
     * @param points             ArrayList of coordinates
     * @param attributeIndex     Index of attribute, which we want to find mean
     * @return mean              result of calculation
     */
    public static int getMean(ArrayList<Point> points,int attributeIndex){

        int mean = 0;

        // Mean of population
        mean = points.stream().mapToInt(point -> point.getAttribute(attributeIndex)).sum();
        mean = mean / points.size();

        return mean;

    }

    /**
     * FUnction to find variance of population  (number => (x or y))
     * @param points            ArrayList of coordinates
     * @param attributeIndex    Index of attribute, which we want to find variance
     * @return variance         result of calculation
     */
    public static int getVariance(ArrayList<Point> points, int attributeIndex){

        double variance = 0;
        double sumVariance = 0;

        // Mean of population
        int mean = getMean(points, attributeIndex);


        // Variance of population
        for (int i = 0; i < points.size() ; i++) {

            ArrayList<Integer> point = new ArrayList<>(2);

            sumVariance = Math.pow((points.get(i).getAttribute(attributeIndex) - mean), 2);

        }

        variance = Math.sqrt(sumVariance / points.size());

        return (int)variance;

    }

    /**
     * Function to get Gaussian Value of variance (sigma_x or sigma_y)
     * @param coordinate
     * @param mean
     * @param variance
     * @return              Calculated Gaussian Value
     */
    public static int getGaussianValue( int coordinate , int mean, int variance ){

        double gaussian = Math.exp(-0.5 * Math.pow((coordinate - mean),2) / Math.pow(variance, 2)) * (1/(variance*Math.sqrt(2*Math.PI)));

        return (int) gaussian;
    }


    /**
     * Function to create Data for classification
     * @param   dataSize  number of instances in data
     * @return  data      created data
     */
    public static ArrayList<ArrayList<Integer>> createData(int dataSize){


        ArrayList<ArrayList<Integer>> data = new ArrayList<>(dataSize);

        int x, y;

        for (int i = 0; i < 100 ; i++) {

            ArrayList<Integer> point = new ArrayList<>(2);

            x = rand.nextInt(1000);
            y = rand.nextInt(400);

            point.add(x);
            point.add(y);

            data.add(point);
        }

        return data;
    }


    /**
     * Function to create Data for classification
     * @param   k         number of centers (also clusters)
     * @return  data      created data
     */
    public static ArrayList<ArrayList<Integer>> createKCenters(int k){


        ArrayList<ArrayList<Integer>> kCenters = new ArrayList<>(k);

        int x, y, xVariance, yVariance;

        for (int i = 0; i < k ; i++) {

            ArrayList<Integer> point = new ArrayList<>(4);

            x = rand.nextInt(1000);
            y = rand.nextInt(400);

            point.add(x);
            point.add(y);

            kCenters.add(point);
        }

        for (int i = 0; i < k ; i++) {

            ArrayList<Integer> point = new ArrayList<>(4);

            x = rand.nextInt(1000);
            y = rand.nextInt(400);

            point.add(x);
            point.add(y);

            kCenters.add(point);
        }

        return kCenters;
    }

    /**
     * Function to compute entropy of list
     * @param p     input list which we compute its entropy
     * @return      computed entropy
     */
    public static float getEntropy(ArrayList<Float> p){
        float res = 0;

        for (float el:
                p) {
            res += el * el;
        }

        return 1 - res;
    }


    /**
     * Function to get entropy of 2D ArrayList
     */
    public static ArrayList<Float> getEntropyOfList(ArrayList<ArrayList<Point>> centerOccur){


        ArrayList<Integer> sameIndexes = new ArrayList<>();
        ArrayList<Float> probs = new ArrayList<>();
        ArrayList<Float> entropyList = new ArrayList<Float>(centerOccur.get(0).size());



        for (int i = 0; i < centerOccur.get(0).size(); i++) {

            for (int j = 0; j < centerOccur.size(); j++) {

                if (sameIndexes.contains(i))
                    continue;

                int count = 0;
                for (int k = j; k < centerOccur.size(); k++) {
                    if(centerOccur.get(j).get(i).equals(centerOccur.get(k).get(i)))
                        count++;
                        sameIndexes.add(k);
                    }

            probs.add((float) count/centerOccur.size());

            }
            entropyList.add(Utility.getEntropy(probs));
            probs.clear();
        }

        return entropyList;
    }


    /**
     * Function to convert List of Points object To List of ArrayList
     */
    public  static ArrayList<ArrayList<Integer>> convertPointToList(ArrayList<Point> points){

        ArrayList<ArrayList<Integer>> pointList = Utility.createData(points.size());


        for (int i = 0; i < 100 ; i++) {

            int coordinate;

            ArrayList<Integer> point = new ArrayList<Integer>(points.get(0).getDimension());

            for (int j = 0; j < points.get(0).getDimension(); j++) {

                coordinate = points.get(i).getAttribute(j);
                point.add(coordinate);
            }

            pointList.add(point);
        }

        return pointList;

    }

    /**
     *  Function to get random gaussian value
     * @param deviation
     * @param center
     * @return
     */
    public static double nextGaussian(float deviation, float center) {
        return Math.abs(rand.nextGaussian())*deviation + center;
    }

}
