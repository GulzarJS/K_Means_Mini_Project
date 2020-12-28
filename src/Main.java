/*
 *  Created by Gulzar Safar on 12/28/2020
 */

import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        Random rand = new Random();

        ArrayList<ArrayList<Integer>> points = new ArrayList<>(100);
        ArrayList<ArrayList<Integer>> kCenters = new ArrayList<>(4);

        int x, y;

        for (int i = 0; i < 100 ; i++) {

            ArrayList<Integer> point = new ArrayList<>(2);

            x = rand.nextInt(1000);
            y = rand.nextInt(400);


            point.add(x);
            point.add(y);

            points.add(point);
        }

        points.forEach(point -> {
            System.out.println(point.toString());
        });

    }

}
