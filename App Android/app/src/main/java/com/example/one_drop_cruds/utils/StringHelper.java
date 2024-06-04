package com.example.one_drop_cruds.utils;

public class StringHelper {
    public static String escapeBlankSpacesInComentario(String objectString){
        // por problemas para escapar espacios en blanco al serializar los comentarios, es que primero obtengo el string en guardado en comentario, reemplazo los espacios en blacos por **, luego serializo a clase Record y por ultimo elimino los **
        String onlyComentario =  objectString.split("comentario=")[1].replace(" ", "**");
        return objectString.split("comentario")[0]+"comentario="+ onlyComentario; //junto el string antes de serializar
    }
    public static String restoreBlankSpacesInComentario(String string){
        return string.replace("**", " ");
    }
}
