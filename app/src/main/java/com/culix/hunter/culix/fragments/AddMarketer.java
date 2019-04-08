package com.culix.hunter.culix.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.culix.hunter.culix.R;
import com.culix.hunter.culix.adapter.AccountsAdapter;
import com.culix.hunter.culix.adapter.ProductManageAdapter;
import com.culix.hunter.culix.config.Config;
import com.culix.hunter.culix.config.CustomToast;
import com.culix.hunter.culix.data.Accounts;
import com.culix.hunter.culix.data.ProductData;
import com.culix.hunter.culix.network.Receiver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddMarketer extends Fragment {

    private SharedPreferences sharedPreferences;
    private String data;

    RecyclerView account_list;

    private List<Accounts> accounts;
    private  RecyclerView.Adapter adapter;
    private  RecyclerView.LayoutManager layoutManager;

    private AccountsAdapter accountsAdapter;

    private CustomToast customToast;
    ProgressDialog progressDialog;


    public AddMarketer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_add_marketer, container, false);

        customToast = new CustomToast(getActivity());

        account_list = v.findViewById(R.id.account_list);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getString(R.string.progress));

        Receiver receiver = new Receiver(getContext(), progressDialog);
        progressDialog.show();
        receiver.getRequests();

        sharedPreferences = getContext().getSharedPreferences(Config.MYPREFERENCES, Context.MODE_PRIVATE);
        data = sharedPreferences.getString("REQUEST_DATA", null);
        if(!(data == null || data.equals("")) ){
            //display data
            Log.e("REQUEST_DATA", data);
            populateList(data);

        }else {
            //Data not available..show feedback
            customToast.setToast(Config.NO_DATA_ERROR);
            Log.e("MSG", "No data Error in if");
        }

        return v;
    }


    private void populateList(String _data){
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
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            account_list.setLayoutManager(layoutManager);

            accountsAdapter = new AccountsAdapter(getActivity(), accounts);
            account_list.setAdapter(accountsAdapter);
        }catch (JSONException e){
            customToast.setToast(Config.NO_DATA_ERROR);
            Log.e("MSG", e.getMessage().toString());
        }
    }

}
