package com.culix.hunter.culix.config;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.culix.hunter.culix.adapter.AccountsAdapter;
import com.culix.hunter.culix.adapter.ProductManageAdapter;
import com.culix.hunter.culix.data.Accounts;
import com.culix.hunter.culix.data.ProductData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdapterConnector {

    private RecyclerView recyclerView;
    private List<Accounts> accounts;
    private  RecyclerView.Adapter adapter;
    private  RecyclerView.LayoutManager layoutManager;
    private String data;


    private List<ProductData> productData;

    private ProductManageAdapter productAdapter;

    public static AccountsAdapter accountsAdapter;
    private Context context;
    CustomToast customToast;

    public AdapterConnector(RecyclerView recyclerView,Context context) {
        this.recyclerView = recyclerView;
        this.data = data;
        this.context = context;

        customToast = new CustomToast(context);
    }

    public void populateUserList(String _data){
        try{
            accounts = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(_data);
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int user_id = jsonObject.getInt("id");
                String firstname = jsonObject.getString("firstname");
                String lastname = jsonObject.getString("lastname");
                String email = jsonObject.getString("email");

                accounts.add(new Accounts(user_id,firstname,lastname,email));
            }
            layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);

            accountsAdapter = new AccountsAdapter(context, accounts);
            recyclerView.setAdapter(accountsAdapter);
        }catch (JSONException e){
            customToast.setToast(Config.NO_DATA_ERROR);
            Log.e("MSG", e.getMessage().toString());
        }
    }

    public void populateList(String _data){
        try{
            productData = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(_data);
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int product_id = jsonObject.getInt("product_id");
                int user_id = jsonObject.getInt("id");
                String product_name = jsonObject.getString("product_name");
                String product_price = jsonObject.getString("product_price");
                String product_category = jsonObject.getString("product_category");
                String product_description = jsonObject.getString("product_description");
                String product_image = jsonObject.getString("product_image");
                String business_country = jsonObject.getString("business_country");
                String business_city = jsonObject.getString("business_city");
                String business_address = jsonObject.getString("business_address");
                String location_lat = jsonObject.getString("location_lat");
                String location_lgt = jsonObject.getString("location_lgt");
                String created_at = jsonObject.getString("created_at");

                productData.add(new ProductData(product_id,user_id,product_name,product_price,product_description,product_category,product_image,business_country,business_city,business_address,location_lat,location_lgt,created_at));
            }
            layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);

            productAdapter = new ProductManageAdapter(context, productData);
            recyclerView.setAdapter(productAdapter);
        }catch (JSONException e){
            customToast.setToast(Config.NO_DATA_ERROR);
            Log.e("MSG", e.getMessage().toString());
        }
    }
}
