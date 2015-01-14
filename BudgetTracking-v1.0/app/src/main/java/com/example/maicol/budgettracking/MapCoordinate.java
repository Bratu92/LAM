package com.example.maicol.budgettracking;

/**
 * Created by Maicol on 13/01/2015.
 */
public class MapCoordinate {

    String category;
    double lat;
    double longt;

    public MapCoordinate() {
    }

    public MapCoordinate(String category, double lat, double longt) {

        this.category = category;
        this.lat = lat;
        this.longt = longt;

    }

    public String getCategory(){return category;}
    public double getLat(){return lat;}
    public double getLong(){return longt;};
}
