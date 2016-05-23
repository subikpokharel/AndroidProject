package com.project.yuvraj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;

public class PricingDetails extends AppCompatActivity {

    int cartTotal,delivery, list;
    double vat,totalPay;
    String v,p;
    TextView textViewCart,textViewVat,textViewdelivery,textViewfinal,textViewitems,textViewtotal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pricing_details);

        cartTotal = getIntent().getIntExtra("CartTotal", 0);
        vat = getIntent().getDoubleExtra("Vat", 0);
        delivery = getIntent().getIntExtra("Delivery", 0);
        totalPay = getIntent().getDoubleExtra("Total", 0);
        list = getIntent().getIntExtra("Items", 0);
        init();
        DecimalFormat df=new DecimalFormat("0");
        v = df.format(vat);
        p = df.format(totalPay);
        setPrice();
    }

    private void setPrice() {

        textViewitems.setText("Items (" + list + ")");
        textViewtotal.setText("Amount : Rs " + p);
        textViewCart.setText("Rs: " + cartTotal);
        textViewVat.setText("Rs: "+v);
        textViewdelivery.setText("Rs: "+delivery);
        textViewfinal.setText("Rs: " + p);
    }

    private void init() {

        textViewitems = (TextView) findViewById(R.id.itemCount);
        textViewtotal = (TextView) findViewById(R.id.totalDisplay);
        textViewCart = (TextView) findViewById(R.id.cartPriceTotal);
        textViewVat = (TextView) findViewById(R.id.vatPriceTotal);
        textViewdelivery = (TextView) findViewById(R.id.deliveryPriceTotal);
        textViewfinal = (TextView) findViewById(R.id.finalPriceTotal);
    }

    public void checkout(View v){

        Intent intent = new Intent(PricingDetails.this,ShippingDetails.class);
        startActivity(intent);


    }
}
