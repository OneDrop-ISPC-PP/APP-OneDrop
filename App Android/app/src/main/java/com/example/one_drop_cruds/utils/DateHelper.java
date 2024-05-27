package com.example.one_drop_cruds.utils;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class DateHelper {

    public String getStringShortDateForGraphs(Long dateInMilli){
        Instant instant = Instant.ofEpochMilli(dateInMilli); // Crear un objeto Instant a partir de los milisegundos en Long
        ZonedDateTime fechaHoraZona = instant.atZone(ZoneId.systemDefault()); // Convertir el Instant a una fecha y hora en la zona horaria predeterminada del sistema
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EE dd '-' HH:mm'hs'", Locale.ENGLISH); //Formatear la fecha y hora según el estilo // todo VERIFICAR IDIOMA DEL GRAFICO!!!
        return fechaHoraZona.format(dateFormatter);// Obtener la fecha y hora formateada como una cadena para el grafico
    }
    public ArrayList<String> getStringShortDateForGraphs(ArrayList<Long> reg_dates){
        //Edito los datos de fecha al formato corto, solo para graficos estilo Sun 23 - 06:40hs
        ArrayList<String> shortDates = new ArrayList<>();
        reg_dates.forEach(date-> shortDates.add(getStringShortDateForGraphs(date)));
        return shortDates;
    }
    public String getFullDateForBackend(Long date){
        Instant instant = Instant.ofEpochMilli(date);
        ZonedDateTime zonedDateTime = instant.atZone(ZoneOffset.UTC);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        return zonedDateTime.format(formatter);
    }
    public String dateNowInBackendFormat(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime now = LocalDateTime.now();
        return now.format(formatter);
    }


}
