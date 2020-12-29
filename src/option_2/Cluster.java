/*
 *  Created by Gulzar Safar on 12/28/2020
 */

package option_2;


import java.util.ArrayList;

public class Cluster {
    private ArrayList<Point> points;
    private Point center;
    private int   index;
//    private float entropy;

    public Cluster(){
        this.points = new ArrayList<Point>();
    }

    /**
     * Function to find distance between center and given point
     */
    public double distanceTo(Point p){
        return this.center.distanceTo(p);
    }

    /**
     * Setter and Getter functions
     */
    public void setCenter(Point c){ this.center = c; }
    public void setIndex(int i)   { this.index = i; }

    public Point getCenter() { return this.center; }
    public int   getIndex()  { return this.index; }

    /**
     * Function to clear points of the cluster
     */
    public void resetPoints(){ this.points = new ArrayList<Point>(); }

    /**
     * Function to add new point to the cluster
     */
    public void addPoint(Point p) {this.points.add(p); }


    /**
     * Function to calculate centroid of the cluster
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


    public String toString(){
        String s = "";
        s = "=> Cluster "+this.index+" -- Center: "+ this.center +"   Nb points: "+this.points.size();
        return s;
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
}
