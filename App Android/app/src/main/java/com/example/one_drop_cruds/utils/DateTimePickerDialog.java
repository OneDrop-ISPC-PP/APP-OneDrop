package com.example.one_drop_cruds.utils;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;


import java.util.Calendar;

public class DateTimePickerDialog {
    final Calendar c = Calendar.getInstance(); // Obtener la instancia actual del calendario
    Context context;

    public DateTimePickerDialog(Context context) {
        this.context = context;
    }

    public void showDatePickerDialogWithTime(TextView editTextToSet) {
        // Primero muestro fecha, y despues que seleciona fecha, muestra horario
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Configurar el TimePickerDialog
                        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        // Formatear la fecha
                                        String day = String.valueOf(dayOfMonth);
                                        String month = String.valueOf(monthOfYear + 1);
                                        if (dayOfMonth<10) day="0"+day;
                                        if (monthOfYear<10) month="0"+month;
                                        // Formatear la hora
                                        String hour = String.format("%02d", hourOfDay);
                                        String minuteStr = String.format("%02d", minute);
                                        String time = hour + ":" + minuteStr;
                                        String dateTime = year + "-" + month + "-" + day + "T" + time;
                                        editTextToSet.setText(dateTime); // Mostrar la fecha y hora
                                    }
                                }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false);
                        timePickerDialog.show();// Mostrar el hora después de seleccionar la fecha
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show(); // Mostrar el fecha inicialmente
    }
}
