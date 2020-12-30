/*
 *  Created by Gulzar Safar & Aghateymur Hasanzade on 12/28/2020
 */

package model;


import util.Utility;
import java.util.ArrayList;

public class Cluster {

    private ArrayList<Point> points;
    private Point center;

    // index of cluster
    private int   index;

    /**
     * Constructor of class
     */
    public Cluster(){
        this.points = new ArrayList<Point>();
    }

    /**
     * Method to find distance between center and given point
     */
    public double distanceTo(Point p){
        return this.center.distanceTo(p);
    }


    /**
     * Method to clear points of the cluster
     */
    public void resetPoints(){ this.points = new ArrayList<Point>(); }

    /**
     * Method to add new point to the cluster
     */
    public void addPoint(Point p) {this.points.add(p); }


    /**
     * Method to calculate centroid of the cluster
     */
    public void updateCenter() {

        if (this.points.size() == 0) {
            this.center = new Point(this.center.getDimension());
            return;
        }

        int nbPoints = this.points.size();
        Point pAccu  = new Point(this.points.get(0).getDimension());

        // 1. Accumulate the components of all points in the cluster

        // for each p in the set of points -> add p to pAccu

        // initialising pAccu
        for (int i = 0; i < pAccu.getDimension() ; i++) {
            pAccu.getAttributes().add(0);
        }

        points.forEach(point -> pAccu.addTo(point));

        // 2. Now average each component

        // for each dimension i of pAccu do
        for (int i = 0; i < pAccu.getDimension(); i++) {

            // attribute i <- attribute i / nbPoints
            int attr = (int) (Math.round((pAccu.getAttribute(i) / nbPoints)));
            pAccu.setAttribute(i, attr);

        }

        this.center = pAccu;
    }


    /**
     *  Function to calculate the density of the cluster
     */
    public float getDensity(){
        int topY = getTopY();
        int bottomY = getBottomY();
        int leftX = getLeftX();
        int rightX = getRightX();

        int area = (bottomY - topY) * (rightX - leftX);

        return (float) area/points.size();
    }

    /**
     * Function to get the point's y attribute which is in the top
     */
    private int getTopY(){

        if (points.size() < 1)
            return 0;

        Point resPt = points.get(0);
        int minY = points.get(0).getAttribute(1);

        for (Point pt :
                points) {
            if (pt.getAttribute(1) < minY){
                resPt = pt;
                minY = pt.getAttribute(1);
            }
        }

        return minY;
    }


    /**
     * Function to get the point's y attribute which is in the bottom
     */
    private int getBottomY(){

        if (points.size() < 1)
            return 0;

        Point resPt = points.get(0);
        int maxY = points.get(0).getAttribute(1);

        for (Point pt :
                points) {
            if (pt.getAttribute(1) > maxY){
                resPt = pt;
                maxY = pt.getAttribute(1);
            }
        }

        return maxY;
    }


    /**
     * Function to get the point's x attribute which is in the left
     */
    private int getLeftX(){

        if (points.size() < 1)
            return 0;

        Point resPt = points.get(0);
        int minX = points.get(0).getAttribute(0);

        for (Point pt :
                points) {
            if (pt.getAttribute(0) < minX){
                resPt = pt;
                minX = pt.getAttribute(1);
            }
        }

        return minX;
    }


    /**
     * Function to get the point's x attribute which is in the right
     */
    private int getRightX(){

        if (points.size() < 1)
            return 0;

        Point resPt = points.get(0);
        int maxX = points.get(0).getAttribute(0);

        for (Point pt :
                points) {
            if (pt.getAttribute(0) > maxX){
                resPt = pt;
                maxX = pt.getAttribute(1);
            }
        }

        return maxX;
    }

    /**
     * Function to calculate the scattering of the cluster
     */
    public float getScattering(){
        ArrayList<Integer> sameIndexes = new ArrayList<>();
        ArrayList<Float> probs = new ArrayList<>();

        for (int i = 0; i < this.points.size(); i++) {
            if (sameIndexes.contains(i))
                continue;

            int count = 0;
            for (int j = i; j < this.points.size(); j++) {

                if(this.points.get(i).equals(this.points.get(j))){
                    count++;
                    sameIndexes.add(j);
                }
            }
            probs.add((float) count/this.points.size());
        }

        return Utility.getEntropy(probs);
    }

    /**
     * Function to get the size of the cluster
     */
    public int getSize(){
        return this.points.size();
    }


    /**
     * Format output for CSV (with space separator)
     */
    public String exportToString() {
        String s = "";
        for (Point p: this.points)
            s += p.toString() + this.index + "\n";
        return s;
    }

    /**
     * Setter and Getter Methods
     */
    public void setCenter(Point c){ this.center = c; }
    public void setIndex(int i)   { this.index = i; }

    public Point getCenter() { return this.center; }
    public int   getIndex()  { return this.index; }


    public String toString(){
        String s = "";
        s = "=> Cluster "+this.index+" -- Center: "+ this.center +"   Nb points: "+this.points.size();
        return s;
    }

}
