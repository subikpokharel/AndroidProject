package com.project.yuvraj.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.project.yuvraj.parsing.Address;
import com.project.yuvraj.parsing.Cart;
import com.project.yuvraj.parsing.Details;
import com.project.yuvraj.parsing.OrderParshing;

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


    //Table components for Placed Orders
    private static final String TABLE_PLACED = "placedorders";
    private static final String COL_OSL = "sl";
    private static final String COL_OID = "id";
    private static final String COL_OIMG = "url";
    private static final String COL_ONAME = "name";
    private static final String COL_OPRICE = "price";
    private static final String COL_OQUANTITY = "quantity";
    private static final String COL_DATE = "orderdate";
    private static final String COL_ORDERID = "orderid";


    //Table for Address
    private static final String TABLE_NAME_ADDRESS = "tbaddress";
    private static final String COL_ASL = "sl";
    private static final String COL_AID = "id";
    private static final String COL_ANAME = "name";
    private static final String COL_PIN = "pincode";
    private static final String COL_ADDRESS = "address";
    private static final String COL_LANDMARK = "landmark";
    private static final String COL_CITY = "city";
    private static final String COL_STATE = "state";
    private static final String COL_PHONE = "phone";
    private static final String COL_EMAIL = "email";

    //Table for User Id
    private static final String TABLE_USERORDER = "userorder";
    private static final String COL_USL = "sl";
    private static final String COL_UID = "id";
    private static final String COL_UORDERID = "orderid";
    private static final String COL_UDATE = "date";
    private static final String COL_TOTAL = "total";
    private static final String COL_ITEMS = "items";


    SQLiteDatabase db;


    //Create table Login
    private static final String TABLE_CREATE = "create table login (id integer primary key not null ," +
            "name text not null, email text not null, password text not null);";


    //Create table Orders
    private static final String TABLE_ORDER = "create table orders (sl integer primary key not null ,id not null ," +
            "url text not null, name text not null, price text not null, quantity text not null);";


    //Create table address
    private static final String TABLE_ADDRESS = "create table tbaddress (sl integer primary key not null ,id not null ," +
            "name text not null, pincode text not null, address text not null, landmark text not null, city text not null, " +
            "state text not null, phone text not null, email text not null);";

    //Create table Placed Order
    private static final String TABLE_ORDERMY = "create table placedorders (sl integer primary key not null ,id not null ," +
            "url text not null, name text not null, price text not null, quantity text not null, orderdate text not null,"+
            "orderid text not null);";

    //Create table user order
    private static final String TABLE_USEROID = "create table userorder (sl integer primary key not null ,id not null ," +
            "orderid text not null, date text not null, total text not null, items text not null);";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TABLE_CREATE);
        db.execSQL(TABLE_ORDER);
        db.execSQL(TABLE_ADDRESS);
        db.execSQL(TABLE_ORDERMY);
        db.execSQL(TABLE_USEROID);

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


    //Functions for Table Orders
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
                        a = true;
                        break;
                    }

                }
            } while (c.moveToNext());
        }
        return a;
    }

    public void removeItem(String id, String name, Context context){

        String selectQuery = "SELECT  * FROM " + TABLE_CART;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        String tab_id, tab_name, sl;
        if (c.moveToFirst()){
            do {
                tab_id = c.getString(1);
                if (id.equals(tab_id)){
                    tab_name = c.getString(3);
                    if (name.equals(tab_name)){
                        sl = c.getString(0);
                        SQLiteDatabase database = this.getWritableDatabase();
                        database.delete(TABLE_CART,COL_SL+" = " +sl,null);
                        break;
                    }
                }
            }while (c.moveToNext());
        }
    }

    public void editItem(String id, String name, String quantity){

        String selectQuery = "SELECT  * FROM " + TABLE_CART;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        String tab_id, tab_name, sl;
        if (c.moveToFirst()){
            do {
                tab_id = c.getString(1);
                if (id.equals(tab_id)){
                    tab_name = c.getString(3);
                    if (name.equals(tab_name)){
                        sl = c.getString(0);
                        SQLiteDatabase database = this.getWritableDatabase();
                        String query = "UPDATE " + TABLE_CART + " SET " + COL_QUANTITY + " = " + quantity + " where " + COL_SL + " = " + sl;
                        database.execSQL(query);
                        break;
                    }
                }
            }while (c.moveToNext());
        }

    }

    public void orderPlaced(String id){

        String selectQuery = "SELECT  * FROM " + TABLE_CART;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        String tab_id, tab_name, sl;
        if (c.moveToFirst()){
            do {
                tab_id = c.getString(1);
                if (id.equals(tab_id)){
                        sl = c.getString(0);
                        SQLiteDatabase database = this.getWritableDatabase();
                        database.delete(TABLE_CART,COL_SL+" = " +sl,null);
                }
            }while (c.moveToNext());
        }
    }




    //Functions for Table PLACED ORDERS
    public void insertDetailsMyOrders(Cart mcart) {

        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String query = "select * from " +TABLE_PLACED;
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();

        values.put(COL_OSL, count);
        values.put(COL_OID, mcart.getId());
        values.put(COL_OIMG, mcart.getUrl());
        values.put(COL_ONAME, mcart.getName());
        values.put(COL_OPRICE, mcart.getPrice());
        values.put(COL_OQUANTITY, mcart.getQuantity());
        values.put(COL_DATE, mcart.getDate());
        values.put(COL_ORDERID, mcart.getOrderid());
        db.insert(TABLE_PLACED, null, values);
        db.close();
    }

    public ArrayList<Cart> getMyorder(String id, String orderId) {

        ArrayList<Cart> items_cart = new ArrayList<Cart>();

        String selectQuery = "SELECT  * FROM " + TABLE_PLACED+ " WHERE orderid = "+orderId;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        String tab_id;
        if (c.moveToFirst()) {
            do {
                tab_id = c.getString(1);
                if (id.equals(tab_id)) {


                    Cart cart = new Cart();
                    cart.setSl(c.getString(c.getColumnIndex(COL_OSL)));
                    cart.setId(c.getString(c.getColumnIndex(COL_OID)));
                    cart.setUrl(c.getString(c.getColumnIndex(COL_OIMG)));
                    cart.setName(c.getString(c.getColumnIndex(COL_ONAME)));
                    cart.setPrice(c.getString(c.getColumnIndex(COL_OPRICE)));
                    cart.setQuantity(c.getString(c.getColumnIndex(COL_OQUANTITY)));
                    cart.setDate(c.getString(c.getColumnIndex(COL_DATE)));
                    // adding to items_cart list
                    items_cart.add(cart);
                }
            } while (c.moveToNext());
        }
        return items_cart;
    }


    //Functions for Table Address

    public void insertAddress(Address mAddress) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String query = "select * from " + TABLE_NAME_ADDRESS;
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();

        values.put(COL_ASL, count);
        values.put(COL_AID, mAddress.getId());
        values.put(COL_ANAME, mAddress.getdName());
        values.put(COL_PIN, mAddress.getdPost());
        values.put(COL_ADDRESS, mAddress.getdAddress());
        values.put(COL_LANDMARK, mAddress.getdLand());
        values.put(COL_CITY, mAddress.getdCity());
        values.put(COL_STATE, mAddress.getdCity());
        values.put(COL_PHONE, mAddress.getdPhone());
        values.put(COL_EMAIL, mAddress.getdEmail());
        db.insert(TABLE_NAME_ADDRESS, null, values);
        db.close();
    }

    public void editAddress(Address mAddress,String sl) {


        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from " + TABLE_NAME_ADDRESS;
        ContentValues values = new ContentValues();
        Cursor c = db.rawQuery(query, null);
        String tab_sl;
        if (c.moveToFirst()) {
            do {
                tab_sl = c.getString(0);
                if (sl.equals(tab_sl)) {

                    values.put(COL_ASL, sl);
                    values.put(COL_AID, mAddress.getId());
                    values.put(COL_ANAME, mAddress.getdName());
                    values.put(COL_PIN, mAddress.getdPost());
                    values.put(COL_ADDRESS, mAddress.getdAddress());
                    values.put(COL_LANDMARK, mAddress.getdLand());
                    values.put(COL_CITY, mAddress.getdCity());
                    values.put(COL_STATE, mAddress.getdCity());
                    values.put(COL_PHONE, mAddress.getdPhone());
                    values.put(COL_EMAIL, mAddress.getdEmail());

                    db.update(TABLE_NAME_ADDRESS,values,COL_ASL+" = " +sl,null);
                    break;


                }
            } while (c.moveToNext());
        }
    }


    public String searchAddress(String id) {

        db = this.getReadableDatabase();
        String query = "select * from " + TABLE_NAME_ADDRESS;
        Cursor cursor = db.rawQuery(query, null);

        String a, b;
        b = "Not Found";
        if (cursor.moveToFirst()) {

            do {
                a = cursor.getString(1);
                if (a.equals(id)) {
                    b = "Address Found";
                    break;
                }
            }
            while (cursor.moveToNext());
        }
        return b;
    }


    public ArrayList<Address> getTableAddress(String id) {


        ArrayList<Address> items_table = new ArrayList<Address>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + TABLE_NAME_ADDRESS;
        Cursor c = db.rawQuery(query, null);
        String tab_id;
        if (c.moveToFirst()) {
            do {
                tab_id = c.getString(1);
                if (id.equals(tab_id)) {

                    Address mAddress = new Address();
                    mAddress.setCol_Sl(c.getString(c.getColumnIndex(COL_ASL)));
                    mAddress.setId(c.getString(c.getColumnIndex(COL_AID)));
                    mAddress.setdName(c.getString(c.getColumnIndex(COL_ANAME)));
                    mAddress.setdPost(c.getString(c.getColumnIndex(COL_PIN)));
                    mAddress.setdAddress(c.getString(c.getColumnIndex(COL_ADDRESS)));
                    mAddress.setdLand(c.getString(c.getColumnIndex(COL_LANDMARK)));
                    mAddress.setdCity(c.getString(c.getColumnIndex(COL_CITY)));
                    mAddress.setdState(c.getString(c.getColumnIndex(COL_STATE)));
                    mAddress.setdPhone(c.getString(c.getColumnIndex(COL_PHONE)));
                    mAddress.setdEmail(c.getString(c.getColumnIndex(COL_EMAIL)));

                    // adding to items_cart list
                    items_table.add(mAddress);
                    break;
                }
            } while (c.moveToNext());
        }
        return items_table;
    }



    //Functions for USER ORDER ID TABLE

    public void insertorderId(String id, String orderId,String date, String total, String items) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String query = "select * from " + TABLE_USERORDER;
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();

        values.put(COL_USL, count);
        values.put(COL_UID, id);
        values.put(COL_UORDERID, orderId);
        values.put(COL_UDATE, date);
        values.put(COL_TOTAL, total);
        values.put(COL_ITEMS,items);
        db.insert(TABLE_USERORDER, null, values);
        db.close();
    }

    public ArrayList<OrderParshing> OrderDetails(String id){

        ArrayList<OrderParshing> items_table = new ArrayList<OrderParshing>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + TABLE_USERORDER;
        Cursor c = db.rawQuery(query, null);
        String tab_id;
        if (c.moveToFirst()) {
            do {
                tab_id = c.getString(1);
                if (id.equals(tab_id)) {

                    OrderParshing orderParshing = new OrderParshing();
                    orderParshing.setUser_id(c.getString(c.getColumnIndex(COL_UID)));
                    orderParshing.setOrder_id(c.getString(c.getColumnIndex(COL_UORDERID)));
                    orderParshing.setOrder_date(c.getString(c.getColumnIndex(COL_UDATE)));
                    orderParshing.setOrder_amount(c.getString(c.getColumnIndex(COL_TOTAL)));
                    orderParshing.setCount(c.getString(c.getColumnIndex(COL_ITEMS)));

                    // adding to items_cart list
                    items_table.add(orderParshing);
                }
            } while (c.moveToNext());
        }
        return items_table;

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (newVersion > oldVersion) {
            String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
            String query1 = "DROP TABLE IF EXISTS " + TABLE_ORDER;
            String query2 = "DROP TABLE IF EXISTS " + TABLE_NAME_ADDRESS;
            String query3 = "DROP TABLE IF EXISTS " + TABLE_PLACED;
            String query4 = "DROP TABLE IF EXISTS " + TABLE_USERORDER;

            db.execSQL(query);
            db.execSQL(query1);
            db.execSQL(query2);
            db.execSQL(query3);
            db.execSQL(query4);
            onCreate(db);
        }

    }
}
