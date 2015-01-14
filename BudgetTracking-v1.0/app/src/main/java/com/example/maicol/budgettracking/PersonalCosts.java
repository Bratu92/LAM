package com.example.maicol.budgettracking;

/**
 * Created by Maicol on 07/01/2015.
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.UUID;

import static java.lang.Integer.parseInt;

public class PersonalCosts extends Activity implements OnClickListener {
    /**
     * Called when the activity is first created.
     */

    protected TextView edit_Date;
    protected Button pick_Date;
    protected int year;
    protected int month;
    protected int day;
    Context context;
    public static DatabaseHelper dbhelper;
    private Spinner spinner1;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_costs);
        final Button button = (Button) findViewById(R.id.form_button);
       // final Button buttoncategory = (Button) findViewById(R.id.pickCategory);
        button.setOnClickListener(this);
        dbhelper = new DatabaseHelper(this);

        edit_Date = (TextView) findViewById(R.id.edit_date);
        pick_Date = (Button) findViewById(R.id.pickDate);
        //Nella activity vengono letti i componenti tramite gli id, viene associato un listener al bottone che apre un dialog e viene impostata come data la data odierna.
        pick_Date.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(0);
            }
        });

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        updateDisplay();


    }


    //La funzione updateDisplay() legge i valori di anno, mese e giorno e scrive nella TextView la data nel formato deciso
    protected void updateDisplay() {

        if (month == 0 || month == 1 || month == 2 || month == 3 || month == 4 || month == 5 || month == 6 || month == 7 || month == 8 || month == 9) {
            if (day == 1 || day == 2 || day == 3 || day == 4 || day == 5 || day == 6 || day == 7 || day == 8 || day == 9) {
                edit_Date.setText(
                        new StringBuilder().append("0")
                                .append(day).append("/0")
                                .append(month + 1).append("/")
                                .append(year).append(" "));
            } else {
                edit_Date.setText(
                        new StringBuilder()
                                .append(day).append("/0")
                                .append(month + 1).append("/")
                                .append(year).append(" "));
            }
        } else {
            if (day == 1 || day == 2 || day == 3 || day == 4 || day == 5 || day == 6 || day == 7 || day == 8 || day == 9) {
                edit_Date.setText(
                        new StringBuilder().append("0")
                                .append(day).append("/")
                                .append(month + 1).append("/")
                                .append(year).append(" "));
            } else {
                edit_Date.setText(
                        new StringBuilder()
                                .append(day).append("/")
                                .append(month + 1).append("/")
                                .append(year).append(" "));
            }
        }
    }

    //Il listener imposta la data quando viene selezionata dall'utente
    protected DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int myear, int monthOfYear, int dayOfMonth) {
            year = myear;
            month = monthOfYear;
            day = dayOfMonth;
            updateDisplay();
        }
    };

    //Il dialog crea un oggetto DatePicker associato al listener appena creato.
    // La funzione showDialog() triggera l'esecuzione dell'handler onCreateDialog().
    // Il parametro id puo' essere eventualmente utilizzato per aprire popup con un contenuto diverso.
    @Override
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this,
                dateSetListener,
                year, month, day);
    }

    @Override
    public void onClick(View v) {

        getValues();

    }

    public void getValues() {

        final TextView edit_date = (TextView) findViewById(R.id.edit_date);
        final EditText edit_amount = (EditText) findViewById(R.id.edit_amount);
        final EditText edit_description = (EditText) findViewById(R.id.edit_description);
        final EditText edit_lat = (EditText) findViewById(R.id.edit_lat);
        final EditText edit_long = (EditText) findViewById(R.id.edit_long);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        String date = edit_date.getText().toString();
        String amount_temp = edit_amount.getText().toString();
        Double amount = 0.0;
        if(!amount_temp.equals("")) {

            amount = Double.parseDouble(amount_temp);

        }

        String category = spinner1.getSelectedItem().toString();
        String description = edit_description.getText().toString();
        String lat_temp = edit_lat.getText().toString();
        Double lat = 0.0;
        Double longt = 0.0;
        if(!lat_temp.equals("")) {

            lat = Double.parseDouble(lat_temp);

        }
        String long_temp = edit_long.getText().toString();
        if(!long_temp.equals("")){

            longt = Double.parseDouble(long_temp);

        }

        final Calendar r = Calendar.getInstance();
        int currentyear = r.get(Calendar.YEAR);
        int currentmonth = r.get(Calendar.MONTH);
        int currentday = r.get(Calendar.DAY_OF_MONTH);



        Payment payment = new Payment(date, amount, category, description, day, month + 1, year, lat, longt);

        if (year == currentyear) {
            if (month == currentmonth) {
                if (day == currentday) {

                    if(amount == 0.0){

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                        // set title
                        alertDialogBuilder.setTitle("INSERT PAYMENT FAILED");

                        // set dialog message
                        alertDialogBuilder
                                .setMessage("You must insert the AMOUNT !")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        //Do nothing

                                    }
                                });

                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();

                    }else {
                        //Se le condizioni sono soddisfatte vuol dire che si tratta di un pagamento immediato
                        //quindi lo mettero subito nello storico
                        dbhelper.addPayment(payment);
                        Double pay = payment.getPrice();
                        dbhelper.changeBudget(pay);
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                        // set title
                        alertDialogBuilder.setTitle("INSERT PAYMENT SUCCESS");

                        // set dialog message
                        alertDialogBuilder
                                .setMessage("Your payment is was saved")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        Intent intent = new Intent(PersonalCosts.this, MainActivity.class);
                                        //intent.putExtra("extra_count",count);
                                        startActivity(intent);

                                    }
                                });

                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();
                    }

                }else{

                    //Se le condizioni non sono soddisfatte vuol dire che si tratta di un pagamento futuro
                    //quindi lo mettero subito nei pagamenti futuri
                    if(day > currentday){

                        if(amount == 0.0){

                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                            // set title
                            alertDialogBuilder.setTitle("INSERT PAYMENT FAILED");

                            // set dialog message
                            alertDialogBuilder
                                    .setMessage("You must insert the AMOUNT !")
                                    .setCancelable(false)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            //Do nothing

                                        }
                                    });

                            // create alert dialog
                            AlertDialog alertDialog = alertDialogBuilder.create();

                            // show it
                            alertDialog.show();

                        }else {

                            dbhelper.addFuturePayment(payment);
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                            // set title
                            alertDialogBuilder.setTitle("INSERT FUTURE PAYMENT SUCCESS");

                            // set dialog message
                            alertDialogBuilder
                                    .setMessage("Your payment is was saved")
                                    .setCancelable(false)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            Intent intent = new Intent(PersonalCosts.this, MainActivity.class);
                                            //intent.putExtra("extra_count",count);
                                            startActivity(intent);

                                        }
                                    });

                            // create alert dialog
                            AlertDialog alertDialog = alertDialogBuilder.create();

                            // show it
                            alertDialog.show();
                        }
                    }

                }

            }else{

                if(month > currentmonth) {

                    if(amount == 0.0){

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                        // set title
                        alertDialogBuilder.setTitle("INSERT PAYMENT FAILED");

                        // set dialog message
                        alertDialogBuilder
                                .setMessage("You must insert the AMOUNT !")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        //Do nothing

                                    }
                                });

                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();

                    }else {

                        dbhelper.addFuturePayment(payment);
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                        // set title
                        alertDialogBuilder.setTitle("INSERT FUTURE PAYMENT SUCCESS");

                        // set dialog message
                        alertDialogBuilder
                                .setMessage("Your payment is was saved")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        Intent intent = new Intent(PersonalCosts.this, MainActivity.class);
                                        //intent.putExtra("extra_count",count);
                                        startActivity(intent);

                                    }
                                });

                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();
                    }
                }

            }

        }else{

            if(year > currentyear){

                if(amount == 0.0){

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                    // set title
                    alertDialogBuilder.setTitle("INSERT PAYMENT FAILED");

                    // set dialog message
                    alertDialogBuilder
                            .setMessage("You must insert the AMOUNT !")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    //Do nothing

                                }
                            });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();

                }else {

                    dbhelper.addFuturePayment(payment);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                    // set title
                    alertDialogBuilder.setTitle("INSERT FUTURE PAYMENT SUCCESS");

                    // set dialog message
                    alertDialogBuilder
                            .setMessage("Your payment is was saved")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    Intent intent = new Intent(PersonalCosts.this, MainActivity.class);
                                    //intent.putExtra("extra_count",count);
                                    startActivity(intent);

                                }
                            });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
                }
            }

        }

    }

}




