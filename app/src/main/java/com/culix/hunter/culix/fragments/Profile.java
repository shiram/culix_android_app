package com.culix.hunter.culix.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.culix.hunter.culix.R;
import com.culix.hunter.culix.config.Config;
import com.culix.hunter.culix.network.Uploader;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {

    private SharedPreferences sharedPreferences;
    private String firstname, lastname, user_email;

    TextView names, email;
    AppCompatButton edit_profile, apply_here;
    ProgressDialog  progressDialog;


    public Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_profile, container, false);
        sharedPreferences = getContext().getSharedPreferences(Config.MYPREFERENCES, Context.MODE_PRIVATE);
        firstname = sharedPreferences.getString("session_firstname", null);
        lastname  = sharedPreferences.getString("session_lastname", null);
        user_email = sharedPreferences.getString("session_email", null);

        names = v.findViewById(R.id.names);
        email = v.findViewById(R.id.email);

        edit_profile = v.findViewById(R.id.edit_profile);
        apply_here = v.findViewById(R.id.apply_here);

        edit_profile.setOnClickListener(mEditProfile);
        apply_here.setOnClickListener(mApply);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getString(R.string.progress));




        return v;
    }

    private View.OnClickListener mEditProfile = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    private View.OnClickListener mApply = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Uploader uploader = new Uploader(getContext(), progressDialog);
            progressDialog.show();
            uploader.requestRights();
        }
    };

}
