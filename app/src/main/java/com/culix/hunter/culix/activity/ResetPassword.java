package com.culix.hunter.culix.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.culix.hunter.culix.R;

public class ResetPassword extends AppCompatActivity {

    EditText password_reset_email;
    AppCompatButton reset_password;
    String email;

    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Reset Password");
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        password_reset_email = findViewById(R.id.password_reset_email);
        reset_password = findViewById(R.id.reset_password);
        reset_password.setOnClickListener(mResetPassword);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
    }

    private void InitGUI(){
        email = password_reset_email.getText().toString().trim();
        AddValidation();
    }

    private void AddValidation(){
        awesomeValidation.addValidation(password_reset_email, Patterns.EMAIL_ADDRESS, getString(R.string.auth_email_err));
    }

    private View.OnClickListener mResetPassword = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            InitGUI();
            if(awesomeValidation.validate()){
                Toast.makeText(ResetPassword.this, "Validation Correct", Toast.LENGTH_LONG).show();
            }
        }
    };
}
