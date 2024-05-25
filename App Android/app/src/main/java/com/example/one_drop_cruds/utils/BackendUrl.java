package com.example.one_drop_cruds.utils;

public class BackendUrl {
    String puerto = ":8080/";
    String ipDesdeEmuladorAndroid = "http://10.0.2.2"; //  dirección IP especial para referirse a localhost desde un emulador Android
    public BackendUrl(){ }
    public String getBackendUrl(){
        return ipDesdeEmuladorAndroid+puerto;
    }
}
