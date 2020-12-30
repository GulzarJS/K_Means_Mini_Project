/*
 *  Created by Gulzar Safar & Aghateymur Hasanzade on 12/28/2020
 */

package model;

import java.util.ArrayList;

public class Point {

    private ArrayList<Integer> attributes; // x and y coordinates
    private int nbAttributes;   // nb of attributes in the data


    /**
     * Constructor of class
     * @param dim       dimension of point
     */
    public Point(int dim) {
        this.attributes   = new ArrayList<Integer>(dim);
        this.nbAttributes = dim;
    }



    /**
     * Method to calculate Euclidean distance between this and point p
     */
    public double distanceTo(Point p) {
        double distance = 0;
        if (this.attributes.size() != p.attributes.size())
            throw new RuntimeException("distanceTo() : p dimensions does not match (this:"+attributes.size()+" p:"+p.attributes.size());

        for (int i = 0; i < this.attributes.size(); i++){
            double di = p.attributes.get(i) - this.attributes.get(i);
            distance += di*di;
        }

        return Math.sqrt(distance);
    }

    /**
     * Add p to this component-wise
     */
    public void addTo(Point p){
        Point point = new Point(this.nbAttributes);
        float accu  = 0;

        for (int i = 0; i < p.attributes.size(); i++){
            accu = this.attributes.get(i) + p.attributes.get(i);
            this.attributes.set(i,Math.round(accu)) ;
//            System.out.print(accu + " ");
        }
    }


    /**
     * Method to compare attributes of points
     * @param pt    point which we want to compare with this object
     * @return      boolean value (different => false, same => true
     */
    public boolean Equals(Point pt){
        if (this.getDimension() != pt.getDimension())
            return false;

        for (int i = 0; i < this.attributes.size(); i++) {
            if(this.getAttribute(i) != pt.getAttribute(i))
                return false;
        }
        return true;
    }


    /**
     * Getter and Setter Methods
     */
    public int   getDimension() { return this.nbAttributes;}

    public int getAttribute(int index) { return this.attributes.get(index); }


    public void setAttribute(int index, int value) { this.attributes.set(index, value); }

    public ArrayList<Integer> getAttributes() {
        return attributes;
    }


    public String toString(){
        String s = "";
        for (int f: this.attributes){
            s += f + ",";
        }
        return s;
    }



}
