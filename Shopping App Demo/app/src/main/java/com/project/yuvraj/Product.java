package com.project.yuvraj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.yuvraj.adapters.ProductAdapter;
import com.project.yuvraj.parsing.ParProduct;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Product extends AppCompatActivity {


    String jsonName = null,title, image, item = null, mingredients = null;
    String loadPro,Tname,name;
    ListView listView;
    TextView tv;
    ArrayList<ParProduct> ArrayListPro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        name = getIntent().getStringExtra("Name");
        Tname = getIntent().getStringExtra("Title");

        productDetails(name,Tname);
        loadPro = loadProduct();
        loadAdapter();
        bindData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ParProduct mitem = ArrayListPro.get(position);
                String mProduct = mitem.getCategory();
                String mLink = mitem.getPhoto();
                String mprice = mitem.getItems();
                String mingrediets = mitem.getIngredients();

                Intent i = new Intent(Product.this, FinalProduct.class);
                i.putExtra("Title", mProduct);
                i.putExtra("Image", mLink);
                i.putExtra("Price", mprice);
                i.putExtra("Ingredients", mingrediets);
                startActivity(i);
            }
        });

    }

    private void bindData() {

        listView = (ListView) findViewById(R.id.listViewProduct);
        tv = (TextView) findViewById(R.id.textView);
        tv.setText(Tname);

        ProductAdapter mproductAdapter = new ProductAdapter(this,R.layout.single_row_item,ArrayListPro);
        listView.setAdapter(mproductAdapter);
    }



    public String loadProduct() {

        String json = null;
        try{
            InputStream is = getAssets().open("product.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer);
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        return json;
    }

    public void loadAdapter() {

        try {
            JSONObject jsonRootObj = new JSONObject(loadPro);
            JSONArray product = jsonRootObj.getJSONArray(jsonName);
            ArrayListPro = new ArrayList<>(product.length());

            for (int i = 0; i < product.length(); i++) {
                JSONObject jsonObject = product.getJSONObject(i);

                 title = jsonObject.optString("category").toString();
                 image = jsonObject.optString("photo").toString();
                 item = jsonObject.optString("items").toString();
                 mingredients = jsonObject.optString("ingredients").toString();

                ArrayListPro.add(new ParProduct(title, image,item,mingredients));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void productDetails(String name, String mtitle) {

        if (name.equals("Grocery")){

            if (mtitle.equals("Bread/Baked")){
                Toast.makeText(Product.this, "You have clicked Bread/Baked", Toast.LENGTH_LONG).show();
                jsonName = "gbakery";
            }
            else if (mtitle.equals("Dairy")){
                Toast.makeText(Product.this, "You have clicked Dairy", Toast.LENGTH_LONG).show();
                jsonName = "gdairy";
            }
            else if (mtitle.equals("Baking Goods")){
                Toast.makeText(Product.this, "You have clicked Baking Goods", Toast.LENGTH_LONG).show();
                jsonName = "gbaking";
            }
            else if (mtitle.equals("Meat")){
                Toast.makeText(Product.this, "You have clicked Meat", Toast.LENGTH_LONG).show();
                jsonName = "gmeat";
            }
            else if (mtitle.equals("Produce")){
                Toast.makeText(Product.this, "You have clicked Produce", Toast.LENGTH_LONG).show();
                jsonName = "produce";
            }
            else if (mtitle.equals("Paper Goods")){
                Toast.makeText(Product.this, "You have clicked Paper Goods", Toast.LENGTH_LONG).show();
                jsonName = "pgoods";
            }
            else if (mtitle.equals("Personal Care")){
                Toast.makeText(Product.this, "You have clicked Personal Care", Toast.LENGTH_LONG).show();
                jsonName = "pcare";
            }
        }
        else if (name.equals("Vegetarian")){

            if (mtitle.equals("Snacks")){
                Toast.makeText(Product.this, "You have clicked Snacks", Toast.LENGTH_LONG).show();
                jsonName = "vsnacks";
            }
            else if (mtitle.equals("Momo & Noodles")){
                Toast.makeText(Product.this, "You have clicked MOMO", Toast.LENGTH_LONG).show();
                jsonName = "vmomo";
            }
            else if (mtitle.equals("Salad")){
                Toast.makeText(Product.this, "You have clicked Salad", Toast.LENGTH_LONG).show();
                jsonName = "salad";
            }
            else if (mtitle.equals("Bread & Rice")){
                Toast.makeText(Product.this, "You have clicked Bread & Rice", Toast.LENGTH_LONG).show();
                jsonName = "vrice";
            }
            else if (mtitle.equals("Indian Corner")){
                Toast.makeText(Product.this, "You have clicked Indian Corner", Toast.LENGTH_LONG).show();
                jsonName = "vindian";
            }
            else if (mtitle.equals("Tawa & Tandoor Se")){
                Toast.makeText(Product.this, "You have clicked Tawa & Tandoor Se", Toast.LENGTH_LONG).show();
                jsonName = "vtawa";
            }
        }

        else if (name.equals("Nonvegetarian")){

            if (mtitle.equals("Snacks")){
                Toast.makeText(Product.this, "You have clicked Snacks", Toast.LENGTH_LONG).show();
                jsonName = "nsnacks";
            }
            else if (mtitle.equals("Momo & Noodles")){
                Toast.makeText(Product.this, "You have clicked MOMO", Toast.LENGTH_LONG).show();
                jsonName = "nmomo";
            }
            else if (mtitle.equals("Bread & Rice")){
                Toast.makeText(Product.this, "You have clicked Bread & Rice", Toast.LENGTH_LONG).show();
                jsonName = "nrice";
            }
            else if (mtitle.equals("Indian Corner")){
                Toast.makeText(Product.this, "You have clicked Indian Corner", Toast.LENGTH_LONG).show();
                jsonName = "nindian";
            }
            else if (mtitle.equals("Tawa & Tandoor Se")){
                Toast.makeText(Product.this, "You have clicked Tawa & Tandoor Se", Toast.LENGTH_LONG).show();
                jsonName = "ntawa";
            }
        }
    //    return;
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    //For top right return to Home button

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.home){
            Intent i = new Intent(Product.this, MainPageActivity.class);
            startActivity(i);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
