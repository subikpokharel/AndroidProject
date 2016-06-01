package com.project.yuvraj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.project.yuvraj.adapters.MyCartAdapter;
import com.project.yuvraj.database.DatabaseHelper;
import com.project.yuvraj.myapplication.MyApplication;
import com.project.yuvraj.parsing.Cart;

import java.util.ArrayList;

public class ActivityCart extends AppCompatActivity {


    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    ArrayList<Cart> cartArrayList;
    MyApplication myApplication;
    RecyclerView mRecyclerView;
    TextView usr, itemcount, totaldisplay;
    int sum = 0, delivery = 100, totalList = 0;
    String totalPay, id;
    double rate = 0.145, totalVat = 0, sumTotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_cart);


        usr = (TextView) findViewById(R.id.userName);
      /*  itemcount = (TextView) findViewById(R.id.itemCount);
        totaldisplay = (TextView) findViewById(R.id.totalDisplay);*/

        myApplication = (MyApplication) getApplication();
        id = myApplication.getSavedValue("Id");
        String user = myApplication.getSavedValue("NameS");
        String final_user = "Hello" + " " + user;
        usr.setText(final_user);

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

        //     itemcount.setText("Items (" + totalList + ")");


        for (int i = 0; i < totalList; i++) {
            Cart mcart = cartArrayList.get(i);
            String abc = mcart.getPrice();
            String quant = mcart.getQuantity();
            int quan = Integer.parseInt(quant);
            String remove = "Rs ";
            String pr = abc.replace(remove, "");
            int money = Integer.parseInt(pr);
            int total = quan * money;
            sum = sum + total;
        }

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
}
