package com.project.yuvraj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.yuvraj.database.DatabaseHelper;
import com.project.yuvraj.myapplication.MyApplication;
import com.project.yuvraj.parsing.Address;

import java.util.ArrayList;

public class PaymentPage extends AppCompatActivity {

    ArrayList<Address> mAddress;
    MyApplication myApplication;
    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    String id, displaySum;
    String tab_address, tab_city, tab_pincode, tab_name, tab_phone;
    TextView tvAddress, tvCity, tvPin, tvName, tvPhone, tvPrice;
    ImageView edit_address;
    CheckBox cbCod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_page);

        init();

        myApplication = (MyApplication) getApplication();
        id = myApplication.getSavedValue("Id");
        displaySum = myApplication.getSavedValue("Total");

        mAddress = databaseHelper.getTableAddress(id);

        tvPrice.setText(displaySum);
        getAddress();
        setAddress();


        //On edit address clicked
        edit_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentPage.this,ShippingDetails.class);
                intent.putExtra("Status","Old");
                startActivity(intent);
            }
        });
    }


    private void init() {

        tvAddress = (TextView) findViewById(R.id.delAddress);
        tvCity = (TextView) findViewById(R.id.delCity);
        tvPin = (TextView) findViewById(R.id.delPincode);
        tvName = (TextView) findViewById(R.id.delName);
        tvPhone = (TextView) findViewById(R.id.delPhone);
        edit_address = (ImageView) findViewById(R.id.edit_address);
        tvPrice = (TextView) findViewById(R.id.displayPrice);
        cbCod = (CheckBox) findViewById(R.id.checkboxCod);

    }

    private void getAddress() {

        Address address = mAddress.get(0);
        tab_address = address.getdAddress();
        tab_city = address.getdCity();
        tab_pincode = address.getdPost();
        tab_name = address.getdName();
        tab_phone = address.getdPhone();
    }

    private void setAddress() {

        tvAddress.setText(tab_address);
        tvCity.setText(tab_city);
        tvPin.setText(tab_pincode);
        tvName.setText(tab_name);
        tvPhone.setText(tab_phone);
    }

    public void placeOrder(View view){

        if ((cbCod).isChecked()){
            Toast.makeText(getApplicationContext(),"Check Box Clicked",Toast.LENGTH_LONG).show();
            Intent i = new Intent(PaymentPage.this,OrderPlaced.class);
            startActivity(i);
            finish();
        }else {
            Toast.makeText(getApplicationContext()," Please Select a Payment Option ",Toast.LENGTH_LONG).show();
        }

    }
}
