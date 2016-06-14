package com.project.yuvraj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.project.yuvraj.adapters.MyCartAdapter;
import com.project.yuvraj.adapters.ViewOrderAdapter;
import com.project.yuvraj.database.DatabaseHelper;
import com.project.yuvraj.myapplication.MyApplication;
import com.project.yuvraj.parsing.Cart;
import com.project.yuvraj.parsing.OrderParshing;

import java.util.ArrayList;

public class RecentOrders extends AppCompatActivity implements ViewOrderAdapter.onOrderClickedListner{

    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    ArrayList<OrderParshing> orderArrayList;
    MyApplication myApplication;
    RecyclerView mRecyclerView;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_orders);

        myApplication = (MyApplication) getApplication();
        id = myApplication.getSavedValue("Id");

        orderArrayList = databaseHelper.OrderDetails(id);
        bindData();

    }



    public void bindData(){
        mRecyclerView = (RecyclerView) findViewById(R.id.recentOrderRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ViewOrderAdapter viewOrderAdapter = new ViewOrderAdapter(this, orderArrayList);
        mRecyclerView.setAdapter(viewOrderAdapter);
    }



    @Override
    public void onBackPressed() {
        Intent i = new Intent(RecentOrders.this,MainPageActivity.class);
        finish();
        startActivity(i);
        super.onBackPressed();
    }


    @Override
    public void showClickedOrder(String order_id, String user_id, String amount) {

        Intent intent = new Intent(getApplicationContext(),MyOrdersDetails.class);
        intent.putExtra("order_id",order_id);
        intent.putExtra("user_id", user_id);
        intent.putExtra("amount", amount);
        startActivity(intent);
        //Toast.makeText(getApplicationContext(),order_id+" "+user_id,Toast.LENGTH_LONG).show();

    }
}
