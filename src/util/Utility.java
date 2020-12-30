/*
 *  Created by Gulzar Safar & Aghateymur Hasanzade on 12/28/2020
 */

package util;


import model.Point;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Utility {

    private static Random rand = new Random();


    /**
     * Method to create new csv file and write all data into it
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
     * Method to create Data for classification
     * @param dataSize      number of instances in data
     * @param kCenter       randomly generated center points
     * @return data         created data
     */
    public static ArrayList<Point> createData(int dataSize, ArrayList<Point> kCenter){

        // data for classification
        ArrayList<Point> data = new ArrayList<>(dataSize);

        // declaring attributes for points
        int x, y;

        // index for selecting random center point
        int index;

        // creating 100 new point andding them to data
        for (int i = 0; i < 100 ; i++) {

            // new point for data
            Point point = new Point(2);

            // Select a random center c between 0 and k-1
            index = rand.nextInt(kCenter.size());

            // get center point attributes
            int center_x = kCenter.get(index).getAttribute(0);
            int center_y = kCenter.get(index).getAttribute(1);
            int var_x =  kCenter.get(index).getAttribute(2);
            int var_y =  kCenter.get(index).getAttribute(3);

            // x and y values for new point
            x = (int) Utility.nextGaussian(var_x, center_x);
            y = (int) Utility.nextGaussian(var_y, center_y);

            // adding new x and y to point
            point.getAttributes().add(x);
            point.getAttributes().add(y);

            // adding new point to data
            data.add(point);

        }
        // adding new centers to points
        kCenter.forEach(center -> {
            Point point = new Point(2);
            point.getAttributes().add(center.getAttribute(0));
            point.getAttributes().add(center.getAttribute(1));
            data.add(point);
        });

        return data;
    }



    /**
     * Method to create random center points
     * @param k             number of new center points
     * @return kCenters     created centers
     */
    public static ArrayList<Point> createKCenters(int k){

        ArrayList<Point> kCenters = new ArrayList<>(k);

        // declaring attributes for new center points
        int x, y;

        for (int i = 0; i < k ; i++) {

            Point point = new Point(4);

            // x and y values for new center point
            x = rand.nextInt(1000);
            y = rand.nextInt(400);

            // adding new x and y to center point
            point.getAttributes().add(x);
            point.getAttributes().add(y);

            // adding variance for x and y value to center point
            point.getAttributes().add(rand.nextInt((80 - 60) + 1) + 60);
            point.getAttributes().add(rand.nextInt((80 - 60) + 1) + 60);

            // adding center point to list
            kCenters.add(point);
        }


        return kCenters;
    }

    /**
     * Method to compute entropy of list
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
     *  Method to get random gaussian value
     * @param deviation
     * @param center
     * @return
     */
    public static double nextGaussian(float deviation, float center) {
        return Math.abs(rand.nextGaussian())*deviation + center;
    }


    /** Method  to find logarithm base 2
     *
     * @param x     number which we want to find logarithm
     * @return      computed logarithm
     */
    public static double log2(double x){
        if (x < 0.000001) return 0;
        return Math.log(x)/Math.log(2);
    }
}
