package com.myproject.budgettracking;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Maicol on 20/11/2014.
 */
public class Form extends Activity {
        /** Called when the activity is first created. */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.form);
            final TextView text_date = (TextView) findViewById(R.id.view_date);
            final TextView text_amount = (TextView) findViewById(R.id.view_amount);
            final TextView text_category = (TextView) findViewById(R.id.view_category);
            final TextView text_description = (TextView) findViewById(R.id.view_description);
            Bundle bundle = this.getIntent().getExtras();
            text_date.setText(bundle.getString("date"));
            text_amount.setText(bundle.getString("amount"));
            text_category.setText(bundle.getString("category"));
            text_description.setText(bundle.getString("description"));
        }
}


