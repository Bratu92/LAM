package com.example.maicol.budgettracking;

/**
 * Created by Maicol on 07/01/2015.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Versione
    private static final int DATABASE_VERSION = 31;

    // Database nome
    private static final String DATABASE_NAME = "db_Payment";

    // nome tabella
    private static final String TABLE_NAME1 = "payment";
    private static final String TABLE_NAME2 = "budget";
    private static final String TABLE_NAME3 = "futurecosts";

    // intestazione colonne tabella
    private static final String KEY_ID = "_id";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_DAY = "day";
    public static final String COLUMN_MONTH = "month";
    public static final String COLUMN_YEAR = "year";
    public static final String COLUMN_LAT = "lat";
    public static final String COLUMN_LONGT = "long";


    public static final String COLUMN_BUDGET = "budget";


    private static String[] COLUMN_TABLE1 = new String[]{KEY_ID,
            COLUMN_PRICE, COLUMN_DATE, COLUMN_DESCRIPTION, COLUMN_CATEGORY, COLUMN_DAY, COLUMN_MONTH, COLUMN_YEAR, COLUMN_LAT, COLUMN_LONGT};
    private static String[] COLUMN_TABLE3 = new String[]{KEY_ID,
            COLUMN_PRICE, COLUMN_DATE, COLUMN_DESCRIPTION, COLUMN_CATEGORY, COLUMN_DAY, COLUMN_MONTH, COLUMN_YEAR, COLUMN_LAT, COLUMN_LONGT};

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // creazione tabelle
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE1 = "create table " + TABLE_NAME1 + "( "
                + KEY_ID + " integer primary key autoincrement, "
                + COLUMN_PRICE + " double not null, "
                + COLUMN_DESCRIPTION + " text not null, "
                + COLUMN_DATE + " text not null, "
                + COLUMN_CATEGORY + " text not null, "
                + COLUMN_DAY + " text not null, "
                + COLUMN_MONTH + " text not null, "
                + COLUMN_YEAR + " text not null, "
                + COLUMN_LAT + " double not null, "
                + COLUMN_LONGT + " double not null);";
        String CREATE_TABLE2 = "create table " + TABLE_NAME2 + "( "
                + KEY_ID + " integer primary key autoincrement, "
                + COLUMN_BUDGET + " double not null);";
        String CREATE_TABLE3 = "create table " + TABLE_NAME3 + "( "
                + KEY_ID + " integer primary key autoincrement, "
                + COLUMN_PRICE + " double not null, "
                + COLUMN_DESCRIPTION + " text not null, "
                + COLUMN_DATE + " text not null, "
                + COLUMN_CATEGORY + " text not null, "
                + COLUMN_DAY + " text not null, "
                + COLUMN_MONTH + " text not null, "
                + COLUMN_YEAR + " text not null, "
                + COLUMN_LAT + " double not null, "
                + COLUMN_LONGT + " double not null);";
        db.execSQL(CREATE_TABLE1);
        db.execSQL(CREATE_TABLE2);
        db.execSQL(CREATE_TABLE3);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // elimina le vecchie tabelle
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3);
        //ricrea la tabella
        onCreate(db);
        addBudget(db, new Budget(1000));
    }

    //aggiungi nuovo budget
    void addBudget(SQLiteDatabase db, Budget budget){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_BUDGET, budget.getBudget());
        // inserisce una riga
        db.insert(TABLE_NAME2, null, cv);
    }

    //metodo che ritorna il saldo attuale
    Budget getActualBudget(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME2,new String[] { KEY_ID,
                COLUMN_BUDGET}, null, null,null, null, null );
        if (cursor != null) {
            cursor.moveToFirst();
            Budget budget = new Budget(cursor.getDouble(1));
            return budget;
        }
        return null;
    }

    //metodo che somma/detrae la spesa al saldo
    void changeBudget(Double pay){
        SQLiteDatabase db = this.getWritableDatabase();
        Budget budget = getActualBudget();
        double previous_budget = budget.getBudget();
        double new_budget = previous_budget - pay;
        budget = new Budget(new_budget);
        ContentValues values = new ContentValues();
        values.put(KEY_ID, 1);
        values.put(COLUMN_BUDGET, budget.getBudget());
        db.update(TABLE_NAME2, values, KEY_ID + " = ?", new String[]{String.valueOf(1)});
        db.close();
    }


    // Aggiunge una nuova spesa
    void addPayment(Payment payment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PRICE, payment.getPrice());
        cv.put(COLUMN_DATE, payment.getDate());
        cv.put(COLUMN_DESCRIPTION, payment.getDescription());
        cv.put(COLUMN_CATEGORY, payment.getCategory());
        cv.put(COLUMN_DAY, payment.getDay());
        cv.put(COLUMN_MONTH, payment.getMonth());
        cv.put(COLUMN_YEAR, payment.getYear());
        cv.put(COLUMN_LAT, payment.getLat());
        cv.put(COLUMN_LONGT, payment.getLong());
        // inserisce una riga
        db.insert(TABLE_NAME1, null, cv);
        db.close();
    }

    void addFuturePayment(Payment payment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PRICE, payment.getPrice());
        cv.put(COLUMN_DATE, payment.getDate());
        cv.put(COLUMN_DESCRIPTION, payment.getDescription());
        cv.put(COLUMN_CATEGORY, payment.getCategory());
        cv.put(COLUMN_DAY, payment.getDay());
        cv.put(COLUMN_MONTH, payment.getMonth());
        cv.put(COLUMN_YEAR, payment.getYear());
        cv.put(COLUMN_LAT, payment.getLat());
        cv.put(COLUMN_LONGT, payment.getLong());
        // inserisce una riga
        db.insert(TABLE_NAME3, null, cv);
        db.close();
    }

    // cerca tutte le spese del DB
    public Cursor getAllPayment() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME1, COLUMN_TABLE1, null, null, null, null, COLUMN_DATE +" DESC ");
        if (cursor.moveToFirst()) {
            return cursor;
        }else{
            return null;
        }
    }

    public void deleteFuturePayment(int future_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_NAME3, KEY_ID + "=" + future_id, null);
        db.close();

    }

    public Cursor getAllFuturePayment() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME3, COLUMN_TABLE1, null, null, null, null, COLUMN_DATE +" ASC ");
        if (cursor.moveToFirst()) {
            return cursor;
        }else{
            return null;
        }
    }





}
