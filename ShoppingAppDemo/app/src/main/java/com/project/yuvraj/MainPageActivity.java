package com.project.yuvraj;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.project.yuvraj.myapplication.MyApplication;

public class MainPageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    String email, name;
    MyApplication myApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //Initial fragment
        if (savedInstanceState == null) {


            MainFragment mainFragment = new MainFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, mainFragment);
            fragmentTransaction.commit();
            navigationView.getMenu().getItem(0).setChecked(true);
        }


//Set user name and email in the navigation drawer

        myApplication = (MyApplication) getApplication();
        String email = myApplication.getSavedValue("EmailS");
        String name = myApplication.getSavedValue("NameS");
    //    String id = myApplication.getSavedValue("Id");

     //   Toast.makeText(getBaseContext(), name+ "   "+ email+"   "+id, Toast.LENGTH_LONG).show();

        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main_page);
        TextView nav_user = (TextView) headerView.findViewById(R.id.NVusername);
        TextView nav_email = (TextView) headerView.findViewById(R.id.NVemail);
        nav_email.setText(email);
        nav_user.setText(name);

        //       Toast.makeText(getBaseContext(), name+ "   "+ email, Toast.LENGTH_LONG).show();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(),"Settings Clicked",Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (id == R.id.cart){
            Toast toast = Toast.makeText(getApplicationContext(),"Cart Clicked",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            Intent i = new Intent(getApplicationContext(), ActivityCart.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        final int id = item.getItemId();

        if (id == R.id.nav_home) {
            MainFragment mainFragment = new MainFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, mainFragment);
            fragmentTransaction.commit();
        }
        if (id == R.id.nav_grocery) {
            Intent i = new Intent(getApplicationContext(), GroceryJson.class);
            startActivity(i);
        } else if (id == R.id.nav_food) {
            Intent i = new Intent(getApplicationContext(), FoodItems.class);
            startActivity(i);

        } else if (id == R.id.nav_cart) {

            Intent i = new Intent(getApplicationContext(), ActivityCart.class);
            startActivity(i);

        } else if (id == R.id.nav_logout) {

            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(MainPageActivity.this);

            // Setting Dialog Title
            alertDialog2.setTitle("Confirm Logout...");

            // Setting Dialog Message
            alertDialog2.setMessage("Are you sure you want to Logout?");

            // Setting Icon to Dialog
            alertDialog2.setIcon(R.drawable.logout);

            // Setting Positive "Yes" Btn
            alertDialog2.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            final ProgressDialog progressDialog = new ProgressDialog(MainPageActivity.this, R.style.AppTheme_Dark_Dialog);
                            progressDialog.setIndeterminate(true);
                            progressDialog.setMessage("Logging Out...");
                            progressDialog.show();
                            new android.os.Handler().postDelayed(new Runnable() {
                                public void run() {

                                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(i);
                                    finish();
                                    progressDialog.dismiss();
                                }
                            }, 1000);

                        }
                    });

        // Setting Negative "NO" Btn
            alertDialog2.setNegativeButton("Discard",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to execute after dialog
                          /*  Toast toast = new Toast(getApplicationContext());
                            toast.setGravity(Gravity.TOP|Gravity.LEFT,0,0);*/
                            Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
                    });

        // Showing Alert Dialog
            alertDialog2.show();


        } else if (id == R.id.nav_share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);

            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "http://play.google.com/store/apps/details?id=" + getPackageName());
            startActivity(Intent.createChooser(sharingIntent, "Share using"));
            return true;

        } else if (id == R.id.nav_contact) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
