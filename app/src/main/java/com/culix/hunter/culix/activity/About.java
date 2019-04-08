package com.culix.hunter.culix.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.culix.hunter.culix.R;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle("About");
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
