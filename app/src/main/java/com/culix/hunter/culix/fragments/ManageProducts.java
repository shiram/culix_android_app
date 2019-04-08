package com.culix.hunter.culix.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.culix.hunter.culix.R;
import com.culix.hunter.culix.adapter.ProductAdapter;
import com.culix.hunter.culix.adapter.ProductManageAdapter;
import com.culix.hunter.culix.config.Config;
import com.culix.hunter.culix.config.CustomToast;
import com.culix.hunter.culix.data.ProductData;
import com.culix.hunter.culix.network.Receiver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManageProducts extends Fragment {

    private SharedPreferences sharedPreferences;
    private String data;

    AppCompatEditText product_search;
    RecyclerView product_list;

    private List<ProductData> productData;
    private  RecyclerView.Adapter adapter;
    private  RecyclerView.LayoutManager layoutManager;

    private ProductManageAdapter productAdapter;

    private CustomToast customToast;
    ProgressDialog progressDialog;


    public ManageProducts() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_manage_products, container, false);

        customToast = new CustomToast(getActivity());

        product_search = v.findViewById(R.id.product_search);
        product_list = v.findViewById(R.id.product_list);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getString(R.string.progress));
        sharedPreferences = getActivity().getSharedPreferences(Config.MYPREFERENCES, Context.MODE_PRIVATE);
        int id = sharedPreferences.getInt("session_id",0);
        progressDialog.show();
        Receiver receiver = new Receiver(getContext(), progressDialog,product_list);
        receiver.getUserProducts(id);


        product_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = product_search.getText().toString().toLowerCase(Locale.getDefault());
                productAdapter.filter(text);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return v;
    }



    private void populateList(String _data){
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
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            product_list.setLayoutManager(layoutManager);

            productAdapter = new ProductManageAdapter(getActivity(), productData);
            product_list.setAdapter(productAdapter);
        }catch (JSONException e){
            customToast.setToast(Config.NO_DATA_ERROR);
            Log.e("MSG", e.getMessage().toString());
        }
    }

}
