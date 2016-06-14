package com.project.yuvraj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import com.project.yuvraj.adapters.MyCartAdapter;
import com.project.yuvraj.adapters.MyOrderAdapter;
import com.project.yuvraj.database.DatabaseHelper;
import com.project.yuvraj.myapplication.MyApplication;
import com.project.yuvraj.parsing.Cart;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ClickedNotification extends AppCompatActivity {

    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    ArrayList<Cart> cartArrayList;
    MyApplication myApplication;
    RecyclerView mRecyclerView;
    TextView usr,orderDate;
    Button total;
    DecimalFormat decimalFormat=new DecimalFormat("0");
    int sum = 0, delivery = 0, totalList = 0, deliverycharge = 9;
    String totalPay, id, p, orderId;
    double rate = 0.145, totalVat = 0, sumTotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicked_notification);


        usr = (TextView) findViewById(R.id.userName);
        orderDate = (TextView) findViewById(R.id.orderDate);
        total = (Button) findViewById(R.id.orderTotal);

        myApplication = (MyApplication) getApplication();
        id = myApplication.getSavedValue("Id");
        orderId = myApplication.getSavedValue("OrderId");
        totalPay = myApplication.getSavedValue("Total");
        String user = myApplication.getSavedValue("NameS");
        String final_user = "Hello" + " " + user;


        usr.setText(final_user);
        total.setText("Order Total : Rs "+totalPay);

        cartArrayList = databaseHelper.getMyorder(id,orderId);


        Cart mcart = cartArrayList.get(0);
        String date = mcart.getDate();
        String final_date = "Your Orders on :"+date;
        orderDate.setText(final_date);


        bindCartData();

    }

    private void bindCartData() {
        mRecyclerView = (RecyclerView) findViewById(R.id.notificationRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyOrderAdapter mMyCartAdapter = new MyOrderAdapter(this, cartArrayList, id);
        mRecyclerView.setAdapter(mMyCartAdapter);
    }


    /*private void calculate() {

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
        totalVat = (rate * sum);
        sumTotal = sum + totalVat + delivery;
        p = decimalFormat.format(sumTotal);
        totalPay = String.valueOf(p);

        total.setText("Order Total : Rs "+totalPay);
    }
*/

    @Override
    public void onBackPressed() {

        Intent i = new Intent(ClickedNotification.this, MainPageActivity.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }
}
