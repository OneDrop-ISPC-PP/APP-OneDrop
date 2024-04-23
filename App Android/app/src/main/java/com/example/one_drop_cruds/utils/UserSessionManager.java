package com.example.one_drop_cruds.utils;


import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.one_drop_cruds.UserLoginActivity;

public class UserSessionManager {
    Context context;
    SharedPrefManager sharedPrefManager;
    public UserSessionManager(Context context) {
        this.context = context;
        this.sharedPrefManager = new SharedPrefManager(context , "oneDrop_shared_preferences");
    }
    public void validateLoguedUser(){
        if(this.getLoguedUsername() == null){
            Toast.makeText(this.context, "USER NO LOGUEADO!!!!", Toast.LENGTH_LONG).show();
            this.context.startActivity(new Intent(this.context, UserLoginActivity.class));
        }
    }
    public String getLoguedUsername(){
        return sharedPrefManager.getString("logued_username", null);
    }
}
