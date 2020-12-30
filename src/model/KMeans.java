/*
 *  Created by Gulzar Safar & Aghateymur Hasanzade on 12/28/2020
 */

package model;


import java.util.ArrayList;

public class KMeans {

    // number of clusters
    private int k;

    private ArrayList<Point>   allPoints;
    private ArrayList<Cluster> clusters;


    /**
     * Constructor of class
     *
     * Parameter: nbAttributes number of coordinates ( 2 => x and y )
     */

    public KMeans(ArrayList<Point> data, int k) {
        this.k = k;
        this.allPoints = data;
        this.clusters = new ArrayList<Cluster>();

    }

    /**
     * Method to initialize clusters
     */
    public void initClusters(){
        int rand_index = 0;

        for (int i = 0; i < k ; i++) {

            // create new cluster c
            Cluster c = new Cluster();
            // choose a random point index in [0;150[
            rand_index = (int) (Math.random()*(this.allPoints.size()));
            // get point rand_index in the set of points and assign to new point p
            Point p = allPoints.get(rand_index);
            // assign p as the center of c
            c.setCenter(p);
            c.setIndex(i);
            // add c to the collection of clusters
            clusters.add(c);

        }
    }

    /**
     * Method to find nearest cluster
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
     *  Method to perform k-means steps
     */
    public void kMeansStep(){

        // For each cluster c -> reset all points in c
        this.clusters.forEach( Cluster::resetPoints);

        // index of chosen center
        final int[] index = new int[1];


        // For each point p in the dataset
        allPoints.forEach( point -> {

            // find the nearest cluster c to p
            int nearest_c_index = this.findNearestCluster(point);
            // add the point p to c
            this.clusters.get(nearest_c_index).addPoint(point);

        });

        // For each cluster c -> compute the centroid point of c
        this.clusters.forEach( Cluster::updateCenter);

    }


    /**
     * Method to print Clusters
     */
    public void printClusters(){ for (Cluster c: clusters) System.out.println(c);  }


    /**
     * Getter Methods
     */

    public Cluster getCluster(int index){return this.clusters.get(index); }


    public String toString() {
        String s = "";
        for (Cluster c: this.clusters)
            s += c.exportToString();
        return s;
    }
}
