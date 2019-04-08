package com.culix.hunter.culix.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.culix.hunter.culix.R;
import com.culix.hunter.culix.config.Config;
import com.culix.hunter.culix.network.Receiver;
import com.culix.hunter.culix.network.Uploader;

public class MainActivity extends AppCompatActivity {

    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(MainActivity.this, Home.class);
                startActivity(intent);

                finish();

            }
        }, Config.SPLASH_DURATION);
*/
progressDialog  = new ProgressDialog(this);
progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
progressDialog.setMessage(getString(R.string.progress));
progressDialog.show();
        Receiver receiver = new Receiver(MainActivity.this, progressDialog);
        receiver.getProducts();
    }
}
