package com.example.one_drop_cruds.utils;

public class BackendUrl {
    String puerto = ":8080";
    String wifiDpto = "http://192.168.18.3";
    String wifiCel = "http://192.168.6.144";

    public BackendUrl(){ }
    public String getBackendUrl(){
        return wifiDpto+puerto;
    }
}
