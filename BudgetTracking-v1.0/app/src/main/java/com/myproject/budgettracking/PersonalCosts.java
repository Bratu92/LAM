package com.myproject.budgettracking;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class PersonalCosts extends Activity implements OnClickListener {
    /** Called when the activity is first created. */

    protected TextView edit_Date;
    protected Button pick_Date;
    protected int year;
    protected int month;
    protected int day;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_costs);
        final Button button = (Button) findViewById(R.id.form_button);
        button.setOnClickListener(this);

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
        edit_Date.setText(
                new StringBuilder()
                        .append(day).append("-")
                        .append(month + 1).append("-")
                        .append(year).append(" "));
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
        // TODO Auto-generated method stub
        switch ( v.getId() ) {
            case R.id.form_button:
                final TextView edit_date = (TextView)findViewById(R.id.edit_date);
                final EditText edit_amount = (EditText)findViewById(R.id.edit_amount);
                final EditText edit_category = (EditText)findViewById(R.id.edit_category);
                final EditText edit_description = (EditText)findViewById(R.id.edit_description);
                Bundle bundle = new Bundle();
                bundle.putString("date", edit_date.getText().toString());
                bundle.putString("amount", edit_amount.getText().toString());
                bundle.putString("category",edit_category.getText().toString());
                bundle.putString("description", edit_description.getText().toString());
                Intent form_intent = new Intent(getApplicationContext(), Form.class);
                form_intent.putExtras(bundle);
                startActivity(form_intent);
                break;
        }
    }
}