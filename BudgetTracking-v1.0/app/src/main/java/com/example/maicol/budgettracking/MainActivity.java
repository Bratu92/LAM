package com.example.maicol.budgettracking;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Calendar;


public class MainActivity extends Activity {

    private Button buttonpersonalcosts;
    private Button buttonfuturecosts;
    private Button buttonhistorycosts;
    private Button buttonstatistics;
    DatabaseHelper dbhelper;
    Cursor cursorhistory;
    Cursor cursorfuture;
    String alert_message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbhelper = new DatabaseHelper(this);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        cursorhistory = dbhelper.getAllPayment();
        cursorfuture = dbhelper.getAllFuturePayment();

        buttonpersonalcosts = (Button) findViewById(R.id.button_personalcosts);
        buttonfuturecosts = (Button) findViewById(R.id.button_futurecosts);
        buttonhistorycosts = (Button) findViewById(R.id.button_historycosts);
        buttonstatistics = (Button) findViewById(R.id.button_statistics);


        buttonpersonalcosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, PersonalCosts.class);
                //intent.putExtra("extra_count",count);
                startActivity(intent);

            }
        });



        buttonfuturecosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Controllo per verificare che i cursori non siano null ma siano carichi
                if (cursorfuture == null) {

                    // set title
                    alertDialogBuilder.setTitle("NO FUTURE PAYMENTS");

                    // set dialog message
                    alertDialogBuilder
                            .setMessage("No future payments into the Database please put them in Personal Costs")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // if this button is clicked, close
                                    // current activity

                                }
                            });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();

                } else {

                    Intent intent = new Intent(MainActivity.this, FutureCosts.class);
                    //intent.putExtra("extra_count",count);
                    startActivity(intent);

                }
            }
        });

        buttonhistorycosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Controllo per verificare che i cursori non siano null ma siano carichi
                if (cursorhistory == null) {

                    // set title
                    alertDialogBuilder.setTitle("NO PAYMENTS");

                    // set dialog message
                    alertDialogBuilder
                            .setMessage("No payments into the Database please put them in Personal Costs")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // if this button is clicked, close
                                    // current activity

                                }
                            });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();

                } else {

                    Intent intent = new Intent(MainActivity.this, HistoryCosts.class);
                    //intent.putExtra("extra_count",count);
                    startActivity(intent);

                }
            }
        });

        buttonstatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, Statistics.class);
                //intent.putExtra("extra_count",count);
                startActivity(intent);

            }
        });

        final Calendar r = Calendar.getInstance();
        int currentyear = r.get(Calendar.YEAR);
        int currentmonth = r.get(Calendar.MONTH);
        int currentday = r.get(Calendar.DAY_OF_MONTH);

        if (cursorfuture != null) {
            do {

                int future_id = cursorfuture.getInt(0);
                double future_price = cursorfuture.getDouble(1);
                String future_date =  cursorfuture.getString(2);
                String future_category =  cursorfuture.getString(3);
                String future_description =  cursorfuture.getString(4);
                int day = cursorfuture.getInt(5);
                int month = cursorfuture.getInt(6);
                int year = cursorfuture.getInt(7);
                double lat = cursorfuture.getDouble(8);
                double longt = cursorfuture.getDouble(9);
                Payment future_payment = new Payment(future_date, future_price, future_category, future_description, year, month, day, lat, longt);
                int count1 = 0;
                int count2 = 0;
                int count3 = 0;


                //Notifiche costi futuri
               if (year == currentyear) {
                  if (month == currentmonth+1) {

                       int remaining_day = day - currentday;
                      System.out.println(remaining_day);

                        if(remaining_day == 1){

                            if(count1 == 0) {
                                alert_message = " Tomorrow Payment/s: \n";
                                alert_message += "\n";
                                count1++;
                            }

                            alert_message += future_price+"€ "+future_date+" "+future_category+" "+"\n";

                        }

                         if(remaining_day == 2){

                             if(count2 == 0) {
                                 alert_message += "\n";
                                 alert_message += " Payment/s to be made in 2 days: \n";
                                 alert_message += "\n";
                                 count2++;
                             }

                          alert_message += future_price+"€ "+future_date+" "+future_category+" "+"\n";

                         }

                      if(remaining_day == 3){

                          if(count3 == 0) {
                              alert_message += "\n";
                              alert_message += " Payment/s to be made in 3 days: \n";
                              alert_message += "\n";
                              count3++;
                          }

                          alert_message += future_price+"€ "+future_date+" "+future_category+" "+"\n";

                      }

                  }
               }

                //Gestione costi futuri
                if (year <= currentyear) {
                    if (month <= currentmonth+1) {
                        if (day <= currentday) {

                            Double pay = cursorfuture.getDouble(1);
                            dbhelper.changeBudget(pay);
                            Budget budget = dbhelper.getActualBudget();
                            dbhelper.deleteFuturePayment(future_id);
                            dbhelper.addPayment(future_payment);
                            final TextView displaybudget = (TextView) findViewById(R.id.display_budget);
                            displaybudget.setText(Double.toString(budget.getBudget()) + "€");
                            cursorfuture = dbhelper.getAllFuturePayment();

                        } else {

                            Budget budget = dbhelper.getActualBudget();
                            final TextView displaybudget = (TextView) findViewById(R.id.display_budget);
                            displaybudget.setText(Double.toString(budget.getBudget()) + "€");

                        }
                    } else {

                        Budget budget = dbhelper.getActualBudget();
                        final TextView displaybudget = (TextView) findViewById(R.id.display_budget);
                        displaybudget.setText(Double.toString(budget.getBudget()) + "€");

                    }
                } else {

                    Budget budget = dbhelper.getActualBudget();
                    final TextView displaybudget = (TextView) findViewById(R.id.display_budget);
                    displaybudget.setText(Double.toString(budget.getBudget()) + "€");

                }

            } while (cursorfuture.moveToNext());

            // set title
            alertDialogBuilder.setTitle("ATTENTION !");

            // set dialog message
            alertDialogBuilder
                    .setMessage(alert_message)
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // if this button is clicked, close
                            // current activity

                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();

        }else{

            Budget budget = dbhelper.getActualBudget();
            final TextView displaybudget = (TextView) findViewById(R.id.display_budget);
            displaybudget.setText(Double.toString(budget.getBudget()) + "€");



        }
    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
