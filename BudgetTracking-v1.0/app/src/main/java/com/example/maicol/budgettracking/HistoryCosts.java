package com.example.maicol.budgettracking;

/**
 * Created by Maicol on 07/01/2015.
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by Maicol on 20/11/2014.
 */
public class HistoryCosts extends Activity {

    DatabaseHelper dbhelper;
    Cursor cursor;
    String namecolumn = "";
    String valuecolumn = "";

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        dbhelper = new DatabaseHelper(this);
        super.onCreate(savedInstanceState);
        final Button exportPDF = new Button(this);
        final Button map = new Button(this);
        exportPDF.setText("ExportPDF");
        map.setText("View on Map");
        cursor = dbhelper.getAllPayment();

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
                        //testoCella.setPadding(200, 10, 10, 10);
                        testoCella.setMaxWidth(5);
                        tableRow.addView(testoCella);


                        break;

                    case 2:

                        testoCella = new TextView(this);
                        testoCella.setText("DATE");
                        //testoCella.setPadding(0, 10, 0, 10);
                        testoCella.setMaxWidth(5);
                        tableRow.addView(testoCella);

                        break;

                    case 3:

                        testoCella = new TextView(this);
                        testoCella.setText("CATEGORY");
                        //testoCella.setPadding(0, 10, 0, 10);
                        testoCella.setMaxWidth(5);
                        tableRow.addView(testoCella);

                        break;

                    case 4:

                        testoCella = new TextView(this);
                        testoCella.setText("DESCRIPTION");
                        //testoCella.setPadding(10, 10, 200, 10);
                        testoCella.setMaxWidth(5);
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
                //testoCella.setPadding(200, 10, 0, 10);
                testoCella.setMaxWidth(5);
                tableRow.addView(testoCella);

                testoCella = new TextView(this);
                testoCella.setText(cursor.getString(2));
                //testoCella.setPadding(0, 10, 0, 10);
                testoCella.setMaxWidth(5);
                tableRow.addView(testoCella);

                testoCella = new TextView(this);
                testoCella.setText(cursor.getString(4));
                //testoCella.setPadding(0, 10, 0, 10);
                testoCella.setMaxWidth(200);
                tableRow.addView(testoCella);

                testoCella = new TextView(this);
                testoCella.setText(cursor.getString(3));
                //testoCella.setPadding(0, 10, 200, 10);
                testoCella.setMaxWidth(220);
                tableRow.addView(testoCella);


                System.out.println(cursor.getInt(5));
                System.out.println(cursor.getInt(6));
                System.out.println(cursor.getInt(7));

                tableLayout.addView(tableRow);

            } while (cursor.moveToNext());

        tableRow = new TableRow(this);
        tableRow.setGravity(Gravity.CENTER);
        tableRow.addView(exportPDF);
        tableRow.addView(map);
        tableLayout.addView(tableRow);
        setContentView(tableLayout);


        //Creazione PDF
        cursor.moveToFirst();

        exportPDF.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String file = "";
                Document document = new Document();
                file = Environment.getExternalStorageDirectory().getPath() + "/HistoryCost.pdf";
                try {
                    PdfWriter.getInstance(document, new FileOutputStream(file));
                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                document.open();

                for (int i = 1; i <= 4; i++) {


                    switch (i) {

                        case 1:

                            namecolumn = "AMOUNT";

                            break;

                        case 2:

                            namecolumn += " DATE";

                            break;

                        case 3:

                            namecolumn += " CATEGORY";

                            break;

                        case 4:

                            namecolumn += " DESCRIPTION \n";
                            namecolumn += "\n";

                            break;

                    }

                }

                Paragraph p = new Paragraph(namecolumn);
                try {
                    document.add(p);
                } catch (DocumentException e) {
                    e.printStackTrace();
                }

                do {

                    valuecolumn += cursor.getString(1);

                    valuecolumn += " "+cursor.getString(2);

                    valuecolumn += " "+cursor.getString(4);

                    valuecolumn += " "+cursor.getString(3)+"\n";

                } while (cursor.moveToNext());

                p = new Paragraph(valuecolumn);
                try {
                    document.add(p);
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
                document.close();

            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HistoryCosts.this, Map.class);
                //intent.putExtra("extra_count",count);
                startActivity(intent);

            }
        });

        }



}



