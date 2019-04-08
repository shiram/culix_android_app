package com.culix.hunter.culix.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.culix.hunter.culix.R;
import com.culix.hunter.culix.network.Uploader;

public class Register extends AppCompatActivity {

    EditText user_firstname, user_lastname, user_email, user_password;
    AppCompatButton register;
    String firstname, lastname, email, password;

    AwesomeValidation awesomeValidation;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Register");
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        user_firstname = findViewById(R.id.user_firstname);
        user_lastname = findViewById(R.id.user_lastname);
        user_email = findViewById(R.id.user_email);
        user_password = findViewById(R.id.user_password);
        register= findViewById(R.id.register);

        register.setOnClickListener(mRegister);

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getString(R.string.progress));

        awesomeValidation  = new AwesomeValidation(ValidationStyle.BASIC);
    }

    private void InitGUI(){
        firstname = user_firstname.getText().toString().trim();
        lastname = user_lastname.getText().toString().trim();
        email = user_email.getText().toString().trim();
        password = user_password.getText().toString().trim();
        AddValidation();
    }

    private void AddValidation(){
        awesomeValidation.addValidation(user_firstname, RegexTemplate.NOT_EMPTY, getString(R.string.firstname_err));
        awesomeValidation.addValidation(user_lastname, RegexTemplate.NOT_EMPTY, getString(R.string.lastname_err));
        awesomeValidation.addValidation(user_email, Patterns.EMAIL_ADDRESS, getString(R.string.auth_email_err));
        awesomeValidation.addValidation(user_password, RegexTemplate.NOT_EMPTY, getString(R.string.auth_password_err));
    }

    private View.OnClickListener mRegister = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            InitGUI();
            if(awesomeValidation.validate()){
               //To The Server
                progressDialog.show();
                Uploader uploader = new Uploader(Register.this, progressDialog);
                uploader.register(firstname, lastname, email,password);
            }
        }
    };
}
