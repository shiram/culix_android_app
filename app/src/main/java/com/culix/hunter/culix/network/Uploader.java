package com.culix.hunter.culix.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.culix.hunter.culix.activity.Home;
import com.culix.hunter.culix.config.Config;
import com.culix.hunter.culix.config.CustomToast;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import com.culix.hunter.culix.fragments.Login;

public class Uploader {

    Context context;
    ProgressDialog progressDialog;
    private CustomToast customToast;

    private String server_msg;
    private int id;
    private int user_id;
    private SharedPreferences sharedPreferences;

    public Uploader(Context context, ProgressDialog progressDialog) {
        this.context = context;
        this.progressDialog = progressDialog;
        customToast = new CustomToast(context);
    }


    public void register(final String firstname, final String lastname, final String user_email, final String password) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject result = new JSONObject(response);
                    server_msg = result.getString("success");
                    id = result.getInt("user_id");

                }catch (JSONException e){
                    progressDialog.dismiss();
                    customToast.setToast(Config.NO_SERVER_RESPONSE);
                    Log.d("REGISTER ERROR: ",e.getMessage());
                }
                progressDialog.dismiss();
                if(id > 0) {
                    customToast.setToast(server_msg);
                    Intent intent = new Intent(context, Home.class);
                    context.startActivity(intent);
                }else {
                    customToast.setToast(server_msg);
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        customToast.setToast(Config.NO_SERVER_RESPONSE);
                        Log.d("SERVER ERROR: ",error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(Config.FIRSTNAME, firstname);
                params.put(Config.LASTNAME, lastname);
                params.put(Config.EMAIL, user_email);
                params.put(Config.PASSWORD, password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }


    public void addProducts(final String item_name, final String item_price, final String item_desc, final String item_cat, final String country, final String city, final String address, final String lat, final String lng,  final String item_image_en, final String image_file ) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.ADD_PRODUCT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject result = new JSONObject(response);
                    server_msg = result.getString("success");

                }catch (JSONException e){
                    progressDialog.dismiss();
                    customToast.setToast(Config.NO_SERVER_RESPONSE);
                    Log.d("JSON ERROR: ",e.getMessage());

                }
                progressDialog.dismiss();
                customToast.setToast(server_msg);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        customToast.setToast(Config.NO_SERVER_RESPONSE);
//                        Log.d("SERVER ERROR: ",error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Config.USER_ID, Integer.toString(getUserId()));
                params.put(Config.ITEM_NAME, item_name);
                params.put(Config.ITEM_PRICE, item_price);
                params.put(Config.ITEM_DESC, item_desc);
                params.put(Config.ITEM_CAT, item_cat);
                params.put(Config.BUSINESS_COUNTRY, country);
                params.put(Config.BUSINESS_CITY, city);
                params.put(Config.BUSINESS_ADDRESS, address);
                params.put(Config.LAT, lat);
                params.put(Config.LNG, lng);
                params.put(Config.ITEM_IMAGE_FILE, image_file);
                params.put(Config.ITEM_IMAGE_ENCODED, item_image_en);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }



    public void makeMaketer(final int id) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.ADD_MARKETER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject result = new JSONObject(response);
                    server_msg = result.getString("success");

                }catch (JSONException e){
                    progressDialog.dismiss();
                    customToast.setToast(Config.NO_SERVER_RESPONSE);
                    Log.d("JSON ERROR: ",e.getMessage());

                }
                progressDialog.dismiss();
                customToast.setToast(server_msg);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        customToast.setToast(Config.NO_SERVER_RESPONSE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(Config.USER_ID, Integer.toString(id));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }


    public void requestRights() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.REQUEST_RIGHTS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject result = new JSONObject(response);
                    server_msg = result.getString("success");

                }catch (JSONException e){
                    progressDialog.dismiss();
                    customToast.setToast(Config.NO_SERVER_RESPONSE);
                    Log.d("JSON ERROR: ",e.getMessage());

                }
                progressDialog.dismiss();
                customToast.setToast(server_msg);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        customToast.setToast(Config.NO_SERVER_RESPONSE);
//                        Log.d("SERVER ERROR: ",error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Config.USER_ID, Integer.toString(getUserId()));

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

 public int getUserId(){

     sharedPreferences = context.getSharedPreferences(Config.MYPREFERENCES, context.MODE_PRIVATE);
      user_id = sharedPreferences.getInt("session_id", 0);

      return user_id;
 }

}
