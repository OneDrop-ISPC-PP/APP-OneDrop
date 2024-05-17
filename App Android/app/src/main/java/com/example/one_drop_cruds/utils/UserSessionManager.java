package com.example.one_drop_cruds.utils;


import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.one_drop_cruds.UserLoginActivity;
import com.example.one_drop_cruds.entities.user.FichaMedicaUsuario;
import com.example.one_drop_cruds.entities.user.LoguedUserDetails;

public class UserSessionManager {
    Context context;
    SharedPrefManager sharedPrefManager;
    public UserSessionManager(Context context) {
        this.context = context;
        this.sharedPrefManager = new SharedPrefManager(context , "oneDrop_shared_preferences");
    }
    public LoguedUserDetails getLoguedUserDetails (){
        LoguedUserDetails user = sharedPrefManager.getLoguedUser();
        if(user == null ){
            Toast.makeText(this.context, "Debes estar logueado!", Toast.LENGTH_LONG).show();
            this.context.startActivity(new Intent(this.context, UserLoginActivity.class));
        }
        return user;
    }
    public String getLoguedUsername(){
        return sharedPrefManager.getString("logued_username", null);
    }
}
