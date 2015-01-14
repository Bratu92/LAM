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

import java.util.List;

/**
 * Created by Maicol on 20/11/2014.
 */
public class Form extends Activity {
    /** Called when the activity is first created. */
    DatabaseHelper dbhelper;
    Cursor cursor;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        dbhelper = new DatabaseHelper(this);
        super.onCreate(savedInstanceState);
        cursor= dbhelper.getAllPayment();


        do {




        } while (cursor.moveToNext());


    }
}

