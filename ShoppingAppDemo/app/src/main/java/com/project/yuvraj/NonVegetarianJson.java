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
import android.widget.Toast;

import com.project.yuvraj.adapters.ItemsAdaptar;
import com.project.yuvraj.parsing.Grocery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class NonVegetarianJson extends AppCompatActivity {

    ListView listViewNonVeg;
    ArrayList<Grocery> nvegArrayList;
//    String global_pos = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_vegetarian_json);



        String jsonGrocery = loadGrocery();
        try {
            JSONObject jsonRootObj = new JSONObject(jsonGrocery);
            JSONArray nonveg = jsonRootObj.getJSONArray("nonvegetarian");
            nvegArrayList = new ArrayList<>(nonveg.length());

            for (int i = 0; i < nonveg.length(); i++) {
                JSONObject jsonObject = nonveg.getJSONObject(i);

                String title = jsonObject.optString("category").toString();
                String image = jsonObject.optString("photo").toString();
                String item = jsonObject.optString("items").toString();

                nvegArrayList.add(new Grocery(title, image,item));

                //    Toast.makeText(GroceryJson.this,  groceryArrayList+ "  Clicked Glocery", Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        bindData();

        listViewNonVeg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Grocery mitem = nvegArrayList.get (position);
                String abc = mitem.getCategory();
        //        global_pos = (Integer.toString(position));
                Intent i = new Intent(NonVegetarianJson.this, Product.class);
         //       i.putExtra("Position", global_pos);
                i.putExtra("Name","Nonvegetarian");
                i.putExtra("Title",abc);
                startActivity(i);

            }
        });

    }

    private void bindData() {

        listViewNonVeg = (ListView) findViewById(R.id.listViewNonVeg);
        ItemsAdaptar itemsAdapter = new ItemsAdaptar(this,R.layout.single_row_item,nvegArrayList);
        listViewNonVeg.setAdapter(itemsAdapter);
    }

    public String loadGrocery() {

        String json = null;
        try{
            InputStream is = getAssets().open("nonvegetarian.json");
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
            Intent i = new Intent(NonVegetarianJson.this, MainPageActivity.class);
            startActivity(i);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
