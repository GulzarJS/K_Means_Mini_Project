/*
 *  Created by Gulzar Safar on 12/28/2020
 */

package option_1;


import util.Utility;
import java.util.Random;

import java.util.ArrayList;

public class KMeans {

    private int k;

    public ArrayList<Point> getAllPoints() {
        return allPoints;
    }


    private ArrayList<Point>   allPoints;
    private ArrayList<Cluster> clusters;
    private ArrayList<Point>   centers;

    private static Random rand = new Random();


    /**
     * Parameter: nbAttributes (assumes that the first 4 attributes are float)
     */

    public KMeans(ArrayList<ArrayList<Integer>> data, ArrayList<ArrayList<Integer>> kCenters, int k, int nbAttributes) {
        this.k = k;
        this.allPoints = new ArrayList<Point>();
        this.clusters = new ArrayList<Cluster>();
        this.centers = new ArrayList<Point>();

        // read all datas and create all points with attributes
        for (ArrayList<Integer> instance : data) {
            ArrayList<Integer> attributes = new ArrayList<Integer>(nbAttributes);
            for (int i = 0; i < nbAttributes; i++) attributes.add(instance.get(i));
            this.allPoints.add(new Point(attributes));
        }

        // read all center points and create centers with attributes
        for (ArrayList<Integer> instance : kCenters) {
            ArrayList<Integer> centerAttributes = new ArrayList<Integer>(kCenters.get(0).size());
            for (int i = 0; i < kCenters.get(0).size(); i++) centerAttributes.add(instance.get(i));

            System.out.println(this.centers.toString());
            this.centers.add(new Point(centerAttributes));
        }
    }

    /**
     * Function to initialize clusters
     */

    public void initClusters(){
        int rand_index = 0;

        for (int i = 0; i <k ; i++) {

            // create new cluster c
            Cluster c = new Cluster();

            // get point rand_index in the set of points and assign to new point p
            Point p = centers.get(i);
            // assign p as the center of c
            c.setCenter(p);
            c.setIndex(i);
            // add c to the collection of clusters
            clusters.add(c);

        }
    }

    /**
     * Function to find nearest cluster
     */

    private int findNearestCluster(Point p){
        int   target_cluster = -1;
        float dMin = Float.MAX_VALUE;
        float dist ;

        // for each cluster c in clusters do
        for (Cluster c : clusters) {

            // distance between p and the center of c
            dist = ((float) c.distanceTo(p));
            // if d < dMin : dMin is now d and the target cluster is c
            if(dist < dMin){
                dMin = dist;
                target_cluster = c.getIndex();
            }
        }

        if (target_cluster == -1 ) throw new RuntimeException("Target cluster index is negative");
        return target_cluster;
    }

    /**
     *  Function to perform k-means steps
     */
    public void kMeansStep(){

        // For each cluster c -> reset all points in c
        this.clusters.forEach( Cluster::resetPoints);

        // index of chosen center
        final int[] index = new int[1];


        // For each point p in the dataset
        allPoints.forEach( point -> {

            // coordinates
            int x, y;

            // Select a random center c between 0 and k-1
            index[0] = rand.nextInt(k-1);

            int center_x = centers.get(index[0]).getAttribute(0);
            int center_y = centers.get(index[0]).getAttribute(1);

            x = center_x + Utility.getGaussianValue(point.getAttribute(0), Utility.getMean(allPoints,0), Utility.getVariance(allPoints, 0));

            y = center_y + Utility.getGaussianValue(point.getAttribute(1), Utility.getMean(allPoints,1), Utility.getVariance(allPoints, 1));

            point.setAttribute(0, x);
            point.setAttribute(1, y);
            // find the nearest cluster c to p
            int nearest_c_index = this.findNearestCluster(point);
            // add the point p to c
            this.clusters.get(nearest_c_index).addPoint(point);

        });

        // For each cluster c -> compute the centroid point of c
        this.clusters.forEach( Cluster::updateCenter);

    }


    /**
     * Function to print Clusters
     */
    public void printClusters(){ for (Cluster c: clusters) System.out.println(c);  }

    /**
     * Getter functions
     */
    public int getK() { return this.k; }
    public Cluster getCluster(int index){return this.clusters.get(index); }


    public String toString() {
        String s = "";
        for (Cluster c: this.clusters)
            s += c.exportToString();
        return s;
    }
}
