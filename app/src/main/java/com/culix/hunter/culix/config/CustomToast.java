package com.culix.hunter.culix.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import com.culix.hunter.culix.R;

public class CustomToast {
    private AppCompatTextView message;
    private AppCompatImageView icon;
    private Context context;

    private SharedPreferences sharedPreferences;

    public CustomToast(Context context) {
        this.context = context;
    }

    public void setToast(String _message){
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View customToast = layoutInflater.inflate(R.layout.custom_toast, null);

        message = customToast.findViewById(R.id.message);
        icon = customToast.findViewById(R.id.icon);

        message.setText(_message);

        Toast toast = new Toast(context);
        toast.setView(customToast);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public void setPrefs(int _id, String _firstname, String _lastname, String _email, int _access){

        sharedPreferences = context.getSharedPreferences(Config.MYPREFERENCES, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("session_id", _id);
        editor.putString("session_firstname", _firstname);
        editor.putString("session_lastname", _lastname);
        editor.putString("session_email", _email);
        editor.putInt("access_level", _access);
        editor.apply();

    }

    public void deletePrefs(){
        sharedPreferences = context.getSharedPreferences(Config.MYPREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
    }
}
