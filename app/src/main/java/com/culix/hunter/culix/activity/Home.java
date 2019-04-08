package com.culix.hunter.culix.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

import com.culix.hunter.culix.R;
import com.culix.hunter.culix.config.Config;
import com.culix.hunter.culix.config.CustomToast;
import com.culix.hunter.culix.fragments.AddMarketer;
import com.culix.hunter.culix.fragments.Login;
import com.culix.hunter.culix.fragments.Main;
import com.culix.hunter.culix.fragments.ManageProducts;
import com.culix.hunter.culix.fragments.Profile;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private SharedPreferences sharedPreferences;
    public static int user_id, access_level;
    private String email, firstname, lastname;
    TextView names, user_email;
    CustomToast customToast;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        sharedPreferences = getSharedPreferences(Config.MYPREFERENCES, Context.MODE_PRIVATE);
        user_id = sharedPreferences.getInt("session_id", 0);
        email = sharedPreferences.getString("session_email", null);
        firstname = sharedPreferences.getString("session_firstname", null);
        lastname  = sharedPreferences.getString("session_lastname", null);
        access_level = sharedPreferences.getInt("access_level", 0);

        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().findItem(R.id.nav_manage_marketers).setVisible(true);

        fragment = new Main();
        loadFragment(fragment);

        if(user_id > 0){
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_profile).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_auth).setVisible(false);
        }else {
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_profile).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_auth).setVisible(true);
        }

        if(access_level == 1){
            navigationView.getMenu().findItem(R.id.marketers).setVisible(true);
        }else {
            navigationView.getMenu().findItem(R.id.marketers).setVisible(false);
        }

        if(access_level < 1){
            navigationView.getMenu().findItem(R.id.products).setVisible(false);
        }else {
            navigationView.getMenu().findItem(R.id.products).setVisible(true);
        }

        View headerView = navigationView.getHeaderView(0);
        names = headerView.findViewById(R.id.names);
        user_email = headerView.findViewById(R.id.email);

        names.setText(firstname + " "+ lastname);
        user_email.setText(email);

        customToast = new CustomToast(Home.this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            fragment = new Main();
            loadFragment(fragment);
        } else if (id == R.id.nav_about_us) {

            Intent intent = new Intent(Home.this, About.class);
            startActivity(intent);

        } else if (id == R.id.nav_add_products) {

            Intent intent = new Intent(Home.this, AddProduct.class);
            startActivity(intent);

        } else if (id == R.id.nav_manage_products) {

            fragment = new ManageProducts();
            loadFragment(fragment);

        } else if (id == R.id.nav_add_marketers) {

            fragment = new AddMarketer();
            loadFragment(fragment);

        }else if (id == R.id.nav_manage_marketers) {

        }else if (id == R.id.nav_auth) {

            fragment = new Login();
            loadFragment(fragment);

        }else if (id == R.id.nav_profile) {

            fragment = new Profile();
            loadFragment(fragment);

         }else if (id == R.id.nav_logout) {

            customToast.deletePrefs();
            finishAffinity();
            System.exit(0);
    }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
