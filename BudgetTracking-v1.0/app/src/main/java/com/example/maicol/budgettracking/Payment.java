package com.example.maicol.budgettracking;

/**
 * Created by Maicol on 07/01/2015.
 */
public class Payment {

    private String id;
    private double price;
    private String description;
    private String date;
    private String category;
    private int day;
    private int month;
    private int year;
    private double lat;
    private double longt;


    public Payment(){}


    public Payment(String id, String date, double price, String category, String description, int day, int month, int year, double lat, double longt){

        this.id = id;
        this.price = price;
        this.date = date;
        this.category = category;
        this.description = description;
        this.day = day;
        this.month = month;
        this.year = year;
        this.lat = lat;
        this.longt = longt;

    }

    public Payment(String date, double price, String category, String description, int day, int month, int year, double lat, double longt){

        this.price = price;
        this.date = date;
        this.category = category;
        this.description = description;
        this.day = day;
        this.month = month;
        this.year = year;
        this.lat = lat;
        this.longt = longt;

    }


    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public double getPrice() {
        return price;
    }

    public int getDay() { return day; }

    public int getMonth() { return month; }

    public int getYear() { return year; }

    public double getLat() { return lat; }

    public double getLong() { return longt; }


}
