package com.example.one_drop_cruds.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.one_drop_cruds.entities.user.FichaMedicaUsuario;
import com.example.one_drop_cruds.entities.user.LoguedUserDetails;
import com.google.gson.Gson;

public class SharedPrefManager {
    private Context context;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor sharedPrefEditor;

    public SharedPrefManager(Context context, String preferenceName) {
        this.context = context;
        this.sharedPref = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        this.sharedPrefEditor = this.sharedPref.edit();
    }

    public void setUserToken (String token){
        this.sharedPrefEditor.putString("token", token);
        sharedPrefEditor.apply();
    }
    public String getUserToken() {
        return this.sharedPref.getString("token", null);
    }

    public void setFichaMedicaUser(FichaMedicaUsuario ficha) {
        // serializar a json y guardarlo como cadena en shared pref..
        Gson gsonSerializer = new Gson();
        String fichaJson = gsonSerializer.toJson(ficha);
        this.sharedPrefEditor.putString("fichaMedicaUsuario", fichaJson);
        sharedPrefEditor.apply();
    }
    public FichaMedicaUsuario getFichaMedicaUser() {
        String userJson = this.sharedPref.getString("fichaMedicaUsuario", null);
        Gson gsonSerializer = new Gson();
        FichaMedicaUsuario fichaMedica = gsonSerializer.fromJson(userJson, FichaMedicaUsuario.class);
        return fichaMedica;
    }

    public void setLoguedUser(LoguedUserDetails userDetails) {
        // serializar user details a json y guardarlo como cadena en shared pref..
        Gson gsonSerializer = new Gson();
        String userJson = gsonSerializer.toJson(userDetails);
        this.sharedPrefEditor.putString("loguedUser", userJson);
        sharedPrefEditor.apply();
    }
    public LoguedUserDetails getLoguedUser() {
        String userJson = this.sharedPref.getString("loguedUser", null);
        Gson gsonSerializer = new Gson();
        LoguedUserDetails loguedUserDetails = gsonSerializer.fromJson(userJson, LoguedUserDetails.class);
        return loguedUserDetails;
    }
    public void clearLoguedUser() {
        clearFichaMedica();
        this.sharedPrefEditor.remove("loguedUser");
        sharedPrefEditor.apply();
    }
    public void clearFichaMedica() {
        this.sharedPrefEditor.remove("fichaMedicaUsuario");
        sharedPrefEditor.apply();
    }


    /*
    public void setLoguedUser(String username) {
        this.sharedPrefEditor.putString("logued_username", username);
        sharedPrefEditor.apply();
    }
     */



    // Otros métodos para guardar y recuperar datos

    public void setString(String key, String value) {
        this.sharedPrefEditor.putString(key, value);
        sharedPrefEditor.apply();
    }

    public void setInt(String key, int value) {
        this.sharedPrefEditor.putInt(key, value);
        sharedPrefEditor.apply();
    }

    public void setFloat(String key, float value) {
        this.sharedPrefEditor.putFloat(key, value);
        sharedPrefEditor.apply();
    }

    public void setBoolean(String key, boolean value) {
        this.sharedPrefEditor.putBoolean(key, value);
        sharedPrefEditor.apply();
    }

    public String getString(String key, String defaultValue) {
        return this.sharedPref.getString(key, defaultValue);
    }

    public int getInt(String key, int defaultValue) {
        return this.sharedPref.getInt(key, defaultValue);
    }

    public float getFloat(String key, float defaultValue) {
        return this.sharedPref.getFloat(key, defaultValue);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return this.sharedPref.getBoolean(key, defaultValue);
    }

    public void clearSharedPref() {
        sharedPref.edit().clear().apply();
    }
}

