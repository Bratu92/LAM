package com.example.maicol.budgettracking;

/**
 * Created by Maicol on 07/01/2015.
 */
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by Maicol on 20/11/2014.
 */
public class FutureCosts extends Activity {

    Cursor cursor;
    DatabaseHelper dbhelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbhelper = new DatabaseHelper(this);
        super.onCreate(savedInstanceState);
        cursor = dbhelper.getAllFuturePayment();
        int count = 1;

            TableLayout tableLayout = new TableLayout(this);
            tableLayout.setGravity(Gravity.TOP);
            TextView testoCella;
            TableRow tableRow = new TableRow(this);
            tableRow.setGravity(Gravity.CENTER);
            tableRow.setPadding(0, 30, 0, 0);

            for (int i = 1; i <= 4; i++) {

                switch (i) {

                    case 1:

                        testoCella = new TextView(this);
                        testoCella.setText("AMOUNT");
                        testoCella.setPadding(10, 10, 50, 10);
                        tableRow.addView(testoCella);


                        break;

                    case 2:

                        testoCella = new TextView(this);
                        testoCella.setText("DATE");
                        testoCella.setPadding(10, 10, 50, 10);
                        tableRow.addView(testoCella);

                        break;

                    case 3:

                        testoCella = new TextView(this);
                        testoCella.setText("CATEGORY");
                        testoCella.setPadding(10, 10, 50, 10);
                        tableRow.addView(testoCella);

                        break;

                    case 4:

                        testoCella = new TextView(this);
                        testoCella.setText("DESCRIPTION");
                        testoCella.setPadding(10, 10, 10, 10);
                        tableRow.addView(testoCella);

                        break;

                }

            }

            tableLayout.addView(tableRow);
            setContentView(tableLayout);

            do {

                tableRow = new TableRow(this);
                tableRow.setGravity(Gravity.CENTER);

                testoCella = new TextView(this);
                testoCella.setText(cursor.getString(1));
                testoCella.setPadding(10, 10, 50, 10);
                tableRow.addView(testoCella);

                testoCella = new TextView(this);
                testoCella.setText(cursor.getString(2));
                testoCella.setPadding(10, 10, 50, 10);
                tableRow.addView(testoCella);

                testoCella = new TextView(this);
                testoCella.setText(cursor.getString(4));
                testoCella.setPadding(10, 10, 50, 10);
                tableRow.addView(testoCella);

                testoCella = new TextView(this);
                testoCella.setText(cursor.getString(3));
                testoCella.setPadding(10, 10, 50, 10);
                tableRow.addView(testoCella);

                System.out.println(cursor.getInt(5));
                System.out.println(cursor.getInt(6));
                System.out.println(cursor.getInt(7));

                tableLayout.addView(tableRow);
                setContentView(tableLayout);

            } while (cursor.moveToNext());


        }



}


