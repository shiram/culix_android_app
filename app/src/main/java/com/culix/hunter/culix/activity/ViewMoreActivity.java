package com.culix.hunter.culix.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.widget.Toast;

import com.culix.hunter.culix.R;
import com.culix.hunter.culix.config.Config;
import com.squareup.picasso.Picasso;

public class ViewMoreActivity extends AppCompatActivity {

    AppCompatTextView product_name, product_price, product_category, product_post_date, product_address, product_description;
    AppCompatImageView product_image;
    String name, price, category, post_date, location, description, image;
    int product_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_more);

        try{
            name = getIntent().getExtras().getString("name");
            price = getIntent().getExtras().getString("price");
            category = getIntent().getExtras().getString("category");
            post_date = getIntent().getExtras().getString("created_at");
            location = getIntent().getExtras().getString("location");
            description = getIntent().getExtras().getString("description");
            image = getIntent().getExtras().getString("image");
            product_id = getIntent().getExtras().getInt("product_id");
        }catch (NullPointerException e){
            Toast.makeText(ViewMoreActivity.this, "App Error",Toast.LENGTH_SHORT).show();
        }

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle("View Product");
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        product_image = findViewById(R.id.product_image);
        product_name = findViewById(R.id.product_name);
        product_price = findViewById(R.id.product_price);
        product_category = findViewById(R.id.product_category);
        product_post_date = findViewById(R.id.product_post_date);
        product_address = findViewById(R.id.product_address);
        product_description = findViewById(R.id.product_description);

        Picasso.with(ViewMoreActivity.this).load(Config.url+"/"+image).into(product_image);
        product_name.setText("Name: "+name);
        product_price.setText("Price "+price+"Shs.");
        product_category.setText("Category: "+category);
        product_address.setText("Location: "+location);
        product_description.setText(description);
        product_post_date.setText("Uploaded: "+post_date);
    }
}
