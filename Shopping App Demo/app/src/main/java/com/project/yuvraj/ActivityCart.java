package com.project.yuvraj;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.project.yuvraj.adapters.MyCartAdapter;
import com.project.yuvraj.database.DatabaseHelper;
import com.project.yuvraj.myapplication.MyApplication;
import com.project.yuvraj.parsing.Cart;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ActivityCart extends AppCompatActivity implements MyCartAdapter.OnPriceChangeListner,EditDialog.Communicator{


    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    ArrayList<Cart> cartArrayList;
    MyApplication myApplication;
    RecyclerView mRecyclerView;
    TextView usr, itemcount, totaldisplay;
    int sum = 0, delivery = 0, totalList = 0, deliverycharge = 9;
    String totalPay, id;
    double rate = 0.145, totalVat = 0, sumTotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_cart);


        usr = (TextView) findViewById(R.id.userName);

        myApplication = (MyApplication) getApplication();
        id = myApplication.getSavedValue("Id");
        String user = myApplication.getSavedValue("NameS");
        String final_user = "Hello" + " " + user;
        usr.setText(final_user);

        /*Calendar c = Calendar.getInstance();

        Toast.makeText(getApplicationContext(),"Time "+c.getTime(),Toast.LENGTH_SHORT).show();*/

       /* Date d = new Date();
        CharSequence s = DateFormat.format("hh:mm:ss", d.getTime());
        Toast.makeText(getApplicationContext(),"Time "+s,Toast.LENGTH_LONG).show();

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());

        Toast.makeText(getApplicationContext(),"Date "+formattedDate,Toast.LENGTH_LONG).show();*/

        calculate();

        bindCartData();

    }

    private void bindCartData() {
        mRecyclerView = (RecyclerView) findViewById(R.id.cartRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyCartAdapter mMyCartAdapter = new MyCartAdapter(this, cartArrayList, id);
        mRecyclerView.setAdapter(mMyCartAdapter);
    }

    private void calculate() {

        cartArrayList = databaseHelper.getOrderDetails(id);
        totalList = cartArrayList.size();

        sum = 0;
        int totalItems = 0;
        for (int i = 0; i < totalList; i++) {


            Cart mcart = cartArrayList.get(i);
            String abc = mcart.getPrice();
            String quant = mcart.getQuantity();
            int quan = Integer.parseInt(quant);

            totalItems = totalItems + quan;

            String remove = "Rs ";
            String pr = abc.replace(remove, "");

            int money = Integer.parseInt(pr);
            int total = quan * money;
            sum = sum + total;
        }
        delivery = deliverycharge * totalItems;
       /* Date d = new Date();
        CharSequence s = DateFormat.format("yyyy-MM-dd hh:mm:ss", d.getTime());
        Toast.makeText(getApplicationContext(),"Date "+s,Toast.LENGTH_LONG).show();*/

        totalVat = (rate * sum);
        sumTotal = sum + totalVat + delivery;
        totalPay = String.valueOf(sumTotal);
        //      totaldisplay.setText("Cart Amount : Rs " + sum);


    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent(ActivityCart.this, MainPageActivity.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }

    public void showPricing(View v) {

        calculate();
        if (totalList == 0) {

            Intent i = new Intent(getApplicationContext(), EmptyCart.class);
            startActivity(i);
            finish();
        } else {

            Intent i = new Intent(ActivityCart.this, PricingDetails.class);
            i.putExtra("CartTotal", sum);
            i.putExtra("Vat", totalVat);
            i.putExtra("Delivery", delivery);
            i.putExtra("Total", sumTotal);
            i.putExtra("Items", totalList);
            startActivity(i);
        }

    }



    @Override
    public void showEditDialog(Cart mCart, int position) {
        //dialog
        //get updated cart
        //refresh adapter using new function
        String name = mCart.getName();
        String quantity = mCart.getQuantity();
       // showDialog(name,quantity);
        //code for dialog pop up
        FragmentManager manager = getFragmentManager();
        EditDialog myDialog = new EditDialog();
        myDialog.getValues(name,quantity,String.valueOf(position));
        myDialog.show(manager, "My Dialog");
     //   Toast.makeText(getApplicationContext(),String.valueOf(position),Toast.LENGTH_LONG).show();

    }


    @Override
    public void onDialogMessage(String title,String quantity, String position) {

        databaseHelper.editItem(id, title, quantity);
        calculate();
        bindCartData();
    }

    @Override
    public void onListChnaged() {
        //calculate items and amount
    }

   /* public void showDialog(String name,String quantity){


        FragmentManager manager = getFragmentManager();
        EditDialog myDialog = new EditDialog();
        myDialog.getValues(name,quantity);
        myDialog.show(manager, "My Dialog");
    }*/





}
