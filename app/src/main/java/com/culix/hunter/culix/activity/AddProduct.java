package com.culix.hunter.culix.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Debug;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.culix.hunter.culix.R;
import com.culix.hunter.culix.config.Config;
import com.culix.hunter.culix.network.LocationPicker;
import com.culix.hunter.culix.network.Uploader;

import java.io.ByteArrayOutputStream;

public class AddProduct extends AppCompatActivity {

    EditText item_name, item_price, item_description, business_country, business_city, business_address;
    AppCompatImageView item_image;
    AppCompatSpinner item_category;
    AppCompatButton add_products, get_location;
    AppCompatTextView location_lat, location_lng;

    String name, price,description,country,city,address;
    double lat,lng;

    AwesomeValidation awesomeValidation;

    private Bitmap item_image_bitmap;

    String item_img_loc, item_img_en, image_file, img_Decodable_Str, category;
    ProgressDialog progressDialog;
    LocationPicker locationPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Add Product Here");
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        item_name = findViewById(R.id.item_name);
        item_price = findViewById(R.id.item_price);
        item_description = findViewById(R.id.item_description);
        business_country = findViewById(R.id.business_country);
        business_city = findViewById(R.id.business_city);
        business_address = findViewById(R.id.business_address);
        item_image = findViewById(R.id.item_image);
        item_category = findViewById(R.id.item_category);
        add_products = findViewById(R.id.add_products);
        location_lat = findViewById(R.id.loctaion_lat);
        location_lng = findViewById(R.id.location_lng);
        get_location = findViewById(R.id.get_location);

        get_location.setOnClickListener(mLocationPicker);

        add_products.setOnClickListener(mAddProducts);
        item_image.setOnClickListener(mAddImage);

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(AddProduct.this, R.array.category_list, android.R.layout.simple_spinner_dropdown_item);
        item_category.setAdapter(arrayAdapter);
        item_category.setOnItemSelectedListener(mCategory);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getString(R.string.progress));


    }


    private void InitGUI(){
        name = item_name.getText().toString().trim();
        price = item_price.getText().toString().trim();
        description = item_description.getText().toString().trim();
        country = business_country.getText().toString().trim();
        city = business_city.getText().toString().trim();
        address = business_address.getText().toString().trim();
        if(!(img_Decodable_Str.isEmpty() || img_Decodable_Str.equals(""))){
            item_img_en = getStringImage(item_image_bitmap);
            Log.d("IMAGE EN: ", item_img_en);
        }
        if(lat == 0.00 && lng == 0.00){
            lng = -122.084;
            lat = 37.422;
        }

        AddValidation();
    }

    private void AddValidation(){
        awesomeValidation.addValidation(item_name, RegexTemplate.NOT_EMPTY, getString(R.string.item_name_err));
        awesomeValidation.addValidation(item_price, RegexTemplate.NOT_EMPTY, getString(R.string.item_price_err));
        awesomeValidation.addValidation(item_description, RegexTemplate.NOT_EMPTY, getString(R.string.item_description_err));
        awesomeValidation.addValidation(business_country, RegexTemplate.NOT_EMPTY, getString(R.string.country_err));
        awesomeValidation.addValidation(business_city, RegexTemplate.NOT_EMPTY, getString(R.string.city_err));
        awesomeValidation.addValidation(business_address, RegexTemplate.NOT_EMPTY, getString(R.string.address_err));
    }


    private View.OnClickListener mLocationPicker = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getLocation();
            Log.d("Longitude: ", Double.toString(lng));
            Log.d("Latitude: ", Double.toString(lat));
        }
    };

    private View.OnClickListener mAddProducts = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            InitGUI();
            if(awesomeValidation.validate()){
                progressDialog.show();
                Uploader uploader = new Uploader(AddProduct.this, progressDialog);
                uploader.addProducts(name,price,description,category,country,city,address,Double.toString(lat),Double.toString(lng),item_img_en,image_file);
                item_name.setText("");
                item_price.setText("");
                item_description.setText("");
                business_country.setText("");
                business_city.setText("");
                business_address.setText("");

            }




        }
    };

    private View.OnClickListener mAddImage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(AddProduct.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + getPackageName()));
                finish();
                startActivity(intent);
                return;

            }else{
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, Config.RESULT_LOAD);
            }

        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == Config.RESULT_LOAD && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                item_img_loc = cursor.getString(columnIndex);
                String fileNameSegments[] = item_img_loc.split("/");
                image_file = fileNameSegments[fileNameSegments.length - 1];
                Log.d("IMAGE FILE: ", image_file);
                img_Decodable_Str = cursor.getString(columnIndex);
                cursor.close();
                // Set the Image in ImageView after decoding the String

                item_image.setImageBitmap(BitmapFactory.decodeFile(img_Decodable_Str));
                item_image_bitmap = BitmapFactory.decodeFile(img_Decodable_Str);

            } else {
                Toast.makeText(this, "No Image Picked",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error Occured, try again.", Toast.LENGTH_LONG)
                    .show();
        }
    }


    private String getStringImage(Bitmap bitmap) {

        BitmapFactory.Options options = null;
        options = new BitmapFactory.Options();

        options.inSampleSize = 3;
        bitmap = BitmapFactory.decodeFile(item_img_loc, options);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        byte[] byte_arr = stream.toByteArray();

        String image_en = Base64.encodeToString(byte_arr, 0);

        return image_en;

    }

    private AdapterView.OnItemSelectedListener mCategory = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            category = adapterView.getItemAtPosition(i).toString();
           // Toast.makeText(AddProduct.this, category, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    private void getLocation(){
        locationPicker = new LocationPicker(this);
        if(locationPicker.canGetLocation()){
            double latitude = locationPicker.getLatitude();
            double longitude = locationPicker.getLongitude();
            location_lat.setText("Latitude: "+Double.toString(latitude));
            location_lng.setText("Longitude: "+Double.toString(longitude));
            lat = latitude;
            lng = longitude;
        }else {
            locationPicker.showSettingsAlert();
        }
    }

}
