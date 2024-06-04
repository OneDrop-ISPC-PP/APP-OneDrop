package com.example.one_drop_cruds.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastHelper {
    Context context;

    public ToastHelper(Context context) {
        this.context = context;
    }

    public void showShort(String msg){
        Toast.makeText(context,msg, Toast.LENGTH_SHORT).show();
    }
    public void showLong(String msg){
        Toast.makeText(context,msg, Toast.LENGTH_LONG).show();
    }
}
