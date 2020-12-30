/*
 *  Created by Gulzar Safar & Aghateymur Hasanzade on 12/28/2020
 */

package model;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Point {
    public ArrayList<Integer> getAttributes() {
        return attributes;
    }

    public void setAttributes(ArrayList<Integer> attributes) {
        this.attributes = attributes;
    }

    private ArrayList<Integer> attributes; // x and y coordinates
    private int nbAttributes;   // nb of attributes in the data

    public Point(int dim) {
        this.attributes   = new ArrayList<Integer>(dim);
        this.nbAttributes = dim;
    }

    public Point(ArrayList<Integer> attributes){
        this.attributes   = attributes;
        this.nbAttributes = attributes.size();
    }

    /**
     * Function to calculate Euclidean distance between this and point p
     */
    public double distanceTo(Point p) {
        double distance = 0;
//        if (this.attributes.size() != p.attributes.size())
//            throw new RuntimeException("distanceTo() : p dimensions does not match (this:"+attributes.size()+" p:"+p.attributes.size());

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
//            System.out.println(accu);
//            System.out.println(p.attributes.get(i));
            if(i > 1){
                System.out.println("smth");
            }
            accu = this.attributes.get(i) + p.attributes.get(i);
            this.attributes.set(i,Math.round(accu)) ;
//            System.out.print(accu + " ");
        }
    }

    public String toString(){
        String s = "";
        for (int f: this.attributes){
            s += f + ",";
        }
        return s;
    }

    /**
     * Getter and Setter functions
     */
    public int   getDimension() { return this.nbAttributes;}
    public int getAttribute(int index) { return this.attributes.get(index); }

    public void setAttribute(int index, int value) { this.attributes.set(index, value); }

    public boolean equals(@NotNull Point pt){
        if (this.getDimension() != pt.getDimension())
            return false;

        for (int i = 0; i < this.attributes.size(); i++) {
            if(this.getAttribute(i) != pt.getAttribute(i))
                return false;
        }
        return true;
    }


}
