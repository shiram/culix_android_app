package com.culix.hunter.culix.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.culix.hunter.culix.R;
import com.culix.hunter.culix.activity.Register;
import com.culix.hunter.culix.activity.ResetPassword;
import com.culix.hunter.culix.network.Receiver;

/**
 * A simple {@link Fragment} subclass.
 */
public class Login extends Fragment {
    AppCompatTextView register, forgot_password_page;
    EditText user_email, user_password;
    AppCompatButton login;
    String email, password;
    ProgressDialog progressDialog;

    private AwesomeValidation awesomeValidation;


    public Login() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_login, container, false);

        user_email = v.findViewById(R.id.user_email);
        user_password = v.findViewById(R.id.user_password);

        register = v.findViewById(R.id.register_page);
        register.setOnClickListener(mRegister);

        forgot_password_page = v.findViewById(R.id.forgot_password_page);
        forgot_password_page.setOnClickListener(mPasswordRequest);

        login = v.findViewById(R.id.login);
        login.setOnClickListener(mLogin);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getString(R.string.progress));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        return  v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void InitGUI(){
        email = user_email.getText().toString().trim();
        password = user_password.getText().toString().trim();
        AddValidation();
    }

    private void AddValidation(){
        awesomeValidation.addValidation(user_email, Patterns.EMAIL_ADDRESS, getString(R.string.auth_email_err));
        awesomeValidation.addValidation(user_password, RegexTemplate.NOT_EMPTY, getString(R.string.auth_password_err));
    }

    private View.OnClickListener mRegister = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), Register.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener mPasswordRequest = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), ResetPassword.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener mLogin = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            InitGUI();
            if(awesomeValidation.validate()){
                //To server
                progressDialog.show();
                Receiver receiver = new Receiver(getActivity(), progressDialog);
                receiver.userLogin(email,password);
            }
        }
    };





}
