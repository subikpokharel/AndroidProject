package com.project.yuvraj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.project.yuvraj.adapters.MyOrderAdapter;
import com.project.yuvraj.database.DatabaseHelper;
import com.project.yuvraj.parsing.Cart;

import java.util.ArrayList;

public class MyOrdersDetails extends AppCompatActivity {

    String user_id, order_id, date, amount;
    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    ArrayList<Cart> cartArrayList;
    RecyclerView mRecyclerView;
    TextView orderId, orderDate, orderAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders_details);
        user_id = getIntent().getStringExtra("user_id");
        order_id = getIntent().getStringExtra("order_id");
        amount = getIntent().getStringExtra("amount");

        cartArrayList = databaseHelper.getMyorder(user_id, order_id);

        Cart mcart = cartArrayList.get(0);
        date = mcart.getDate();

        orderId = (TextView) findViewById(R.id.order_id);
        orderDate = (TextView) findViewById(R.id.order_date);
        orderAmount = (TextView) findViewById(R.id.order_amount);

        orderId.setText("Order Id : "+order_id);
        orderDate.setText("Order Date : "+date);
        orderAmount.setText("Total Amount : Rs "+amount);



        mRecyclerView = (RecyclerView) findViewById(R.id.ordersRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyOrderAdapter mMyCartAdapter = new MyOrderAdapter(this, cartArrayList, user_id);
        mRecyclerView.setAdapter(mMyCartAdapter);
    }
}
