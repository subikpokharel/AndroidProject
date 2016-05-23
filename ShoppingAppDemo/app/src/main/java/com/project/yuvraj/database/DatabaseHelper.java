package com.project.yuvraj.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.project.yuvraj.parsing.Cart;
import com.project.yuvraj.parsing.Details;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 4/15/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "famous.db";

    //Table components for Login
    private static final String TABLE_NAME = "login";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";


    //Table components for Cart
    private static final String TABLE_CART = "orders";
    private static final String COL_SL = "sl";
    private static final String COL_ID = "id";
    private static final String COL_IMG = "url";
    private static final String COL_NAME = "name";
    private static final String COL_PRICE = "price";
    private static final String COL_QUANTITY = "quantity";


    SQLiteDatabase db;


    //Create table Login
    private static final String TABLE_CREATE = "create table login (id integer primary key not null ," +
            "name text not null, email text not null, password text not null);";


    //Create table Orders
    private static final String TABLE_ORDER = "create table orders (sl integer primary key not null ,id not null ," +
            "url text not null,name text not null, price text not null, quantity text not null);";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TABLE_CREATE);
        db.execSQL(TABLE_ORDER);

    }

    //inserting Login Details into database
    public void insertDetails(Details details) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String query = "select * from login";
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();

        values.put(COLUMN_ID, count);
        values.put(COLUMN_NAME, details.getUname());
        values.put(COLUMN_EMAIL, details.getEmail());
        values.put(COLUMN_PASSWORD, details.getPassword());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    //On Login Searching Login Details from Database
    public String searchPass(String email) {

        db = this.getReadableDatabase();
        String query = "select email, password from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        String a, b;
        b = "Not Found";
        if (cursor.moveToFirst()) {

            do {
                a = cursor.getString(0);
                if (a.equals(email)) {
                    b = cursor.getString(1);
                    break;
                }
            }
            while (cursor.moveToNext());
        }
        return b;

    }

    public String searchDetails(String email) {

        db = this.getReadableDatabase();
        String query = "select email, name from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        String a, b;
        b = "Not Found";
        if (cursor.moveToFirst()) {

            do {
                a = cursor.getString(0);
                if (a.equals(email)) {
                    b = cursor.getString(1);
                    break;
                }
            }
            while (cursor.moveToNext());
        }
        return b;

    }


    public String getUserId(String email, String name) {
        db = this.getReadableDatabase();
        String query = "select id, email, name from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        String a, b, c;
        c = "Not Found";
        if (cursor.moveToFirst()) {

            do {
                a = cursor.getString(1);
                if (a.equals(email)) {
                    b = cursor.getString(2);
                    if (b.equals(name)) {
                        c = cursor.getString(0);
                    }
                    break;
                }
            }
            while (cursor.moveToNext());
        }
        return c;
    }


    public void insertDetailsToCart(Cart cart) {

        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String query = "select * from orders";
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();

        values.put(COL_SL, count);
        values.put(COL_ID, cart.getId());
        values.put(COL_IMG, cart.getUrl());
        values.put(COL_NAME, cart.getName());
        values.put(COL_PRICE, cart.getPrice());
        values.put(COL_QUANTITY, cart.getQuantity());
        db.insert(TABLE_CART, null, values);
        db.close();
    }


    public ArrayList<Cart> getOrderDetails(String id) {

        ArrayList<Cart> items_cart = new ArrayList<Cart>();

        String selectQuery = "SELECT  * FROM " + TABLE_CART;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        String tab_id;
        if (c.moveToFirst()) {
            do {
                tab_id = c.getString(1);
                if (id.equals(tab_id)) {


                    Cart cart = new Cart();
                    cart.setSl(c.getString(c.getColumnIndex(COL_SL)));
                    cart.setId(c.getString(c.getColumnIndex(COL_ID)));
                    cart.setUrl(c.getString(c.getColumnIndex(COL_IMG)));
                    cart.setName(c.getString(c.getColumnIndex(COL_NAME)));
                    cart.setPrice(c.getString(c.getColumnIndex(COL_PRICE)));
                    cart.setQuantity(c.getString(c.getColumnIndex(COL_QUANTITY)));

                    // adding to items_cart list
                    items_cart.add(cart);
                }
            } while (c.moveToNext());
        }
        return items_cart;
    }

    public boolean removeDuplicate(String id, String name, String quant, Context context) {
        boolean a = false;
        String selectQuery = "SELECT  * FROM " + TABLE_CART;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        String tab_id, tab_name, tab_quantity;
        if (c.moveToFirst()) {
            do {
                tab_id = c.getString(1);
                if (id.equals(tab_id)) {
                    tab_name = c.getString(3);
                    if (name.equals(tab_name)) {
                        tab_quantity = c.getString(5);
                        int abc = Integer.parseInt(quant);
                        int xyz = Integer.parseInt(tab_quantity);
                        int sum = abc + xyz;
                        String total = String.valueOf(sum);
                        String sl = c.getString(0);
                        SQLiteDatabase database = this.getWritableDatabase();
                        String query = "UPDATE " + TABLE_CART + " SET " + COL_QUANTITY + " = " + total + " where " + COL_SL + " = " + sl;
                        database.execSQL(query);
                        Toast.makeText(context, "New Total " + total, Toast.LENGTH_LONG).show();
                        a = true;
                        break;
                    }

                }
            } while (c.moveToNext());
        }
        return a;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String query = "DROP TABLE IF EXIXTS " + TABLE_NAME;
        String query1 = "DROP TABLE IF EXIXTS " + TABLE_ORDER;
        db.execSQL(query);
        db.execSQL(query1);
        onCreate(db);
    }
}
