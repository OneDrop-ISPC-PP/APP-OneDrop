package com.example.one_drop_cruds.utils;

public class BackendUrl {
    String puerto = ":8080";
    String wifiDpto = "http://192.168.18.3"; // direccion IPv4 de cada red por la cual se conectan a internet
    String wifiCel = "http://192.168.6.144"; // direccion IPv4 de cada red por la cual se conectan a internet
    String wifiDaniel = "http://192.168.1.4";
    String ipDesdeEmuladorAndroid = "http://10.0.2.2:8080/";
    public BackendUrl(){ }
    public String getBackendUrl(){
        return ipDesdeEmuladorAndroid;
    }
}
