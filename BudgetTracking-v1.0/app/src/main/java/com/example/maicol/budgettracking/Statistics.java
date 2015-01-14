package com.example.maicol.budgettracking;

/**
 * Created by Maicol on 07/01/2015.
 */
import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

/**
 * Created by Maicol on 20/11/2014.
 */
public class Statistics extends Activity {

    DatabaseHelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);
        dbhelper = new DatabaseHelper(this);
        double sumcloth = 0;
        double sumsport = 0;
        double sumcar = 0;
        double sumvideogame = 0;
        double sumfinance = 0;
        double sumfood = 0;
        double sumcomputerscience = 0;
        double sumhome = 0;


       GraphView graph = (GraphView) findViewById(R.id.graph);
        for ( int i = 0; i <= 7;i++){

            if(i == 0){
                Cursor cursor = dbhelper.getAllPayment();
                if(cursor == null) {


                }else{

                    do {

                        if (cursor.getString(4).equals("Clothing"))
                            sumcloth += cursor.getDouble(1);

                    } while (cursor.moveToNext());
                    System.out.println(sumcloth);
                }
            }
            if(i == 1){
                Cursor cursor = dbhelper.getAllPayment();
                if(cursor == null) {


                }else{

                    do {

                        if (cursor.getString(4).equals("Sports"))
                            sumsport += cursor.getDouble(1);

                    } while (cursor.moveToNext());
                    System.out.println(sumsport);
                }
            }
            if(i == 2){
                Cursor cursor = dbhelper.getAllPayment();
                if(cursor == null) {


                }else{

                    do {

                        if (cursor.getString(4).equals("Cars"))
                            sumcar += cursor.getDouble(1);

                    } while (cursor.moveToNext());
                    System.out.println(sumcar);
                }
            }
            if(i == 3){
                Cursor cursor = dbhelper.getAllPayment();
                if(cursor == null) {


                }else{

                    do {

                        if (cursor.getString(4).equals("Videogames"))
                            sumvideogame += cursor.getDouble(1);

                    } while (cursor.moveToNext());
                    System.out.println(sumvideogame);
                }
            }
            if(i == 4){
                Cursor cursor = dbhelper.getAllPayment();
                if(cursor == null) {


                }else{

                    do {

                        if (cursor.getString(4).equals("Finances"))
                            sumfinance += cursor.getDouble(1);

                    } while (cursor.moveToNext());
                    System.out.println(sumfinance);
                }
            }
            if(i == 5){
                Cursor cursor = dbhelper.getAllPayment();
                if(cursor == null) {


                }else{

                    do {

                        if (cursor.getString(4).equals("Food"))
                            sumfood += cursor.getDouble(1);

                    } while (cursor.moveToNext());
                    System.out.println(sumfood);
                }
            }
            if(i == 6){
                Cursor cursor = dbhelper.getAllPayment();
                if(cursor == null) {


                }else{

                    do {

                        if (cursor.getString(4).equals("ComputerScience"))
                            sumcomputerscience += cursor.getDouble(1);

                    } while (cursor.moveToNext());
                    System.out.println(sumcomputerscience);
                }
            }
            if(i == 7){
                Cursor cursor = dbhelper.getAllPayment();
                if(cursor == null) {


                }else{

                    do {

                        if (cursor.getString(4).equals("Home"))
                            sumhome += cursor.getDouble(1);

                    } while (cursor.moveToNext());
                    System.out.println(sumhome);
                }
            }


        }

        BarGraphSeries<DataPoint> series = new BarGraphSeries<DataPoint>(new DataPoint[] {


                new DataPoint(1, sumcloth),

                new DataPoint(3, sumsport),

                new DataPoint(5, sumcar),

                new DataPoint(7, sumvideogame),

                new DataPoint(9, sumfinance),

                new DataPoint(11, sumfood),

                new DataPoint(13, sumcomputerscience),

                new DataPoint(15, sumhome),
        });
        graph.addSeries(series);




        graph.setTitle("Statistics by Category");




        // styling
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX() * 255 / 4, (int) Math.abs(data.getY() * 255 / 6), 100);

            }
        });

        series.setSpacing(50);

        // draw values on top
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.RED);

        //series.setValuesOnTopSize(50);




    }


}