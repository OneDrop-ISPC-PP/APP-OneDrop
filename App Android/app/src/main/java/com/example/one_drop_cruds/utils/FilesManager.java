package com.example.one_drop_cruds.utils;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Environment;
import android.text.TextPaint;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.example.one_drop_cruds.entities.DTOReadAllRegisters;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FilesManager {
    UserSessionManager userSessionManager;
    String TABLE_GLY = "glycemia";
    String TABLE_PRESSURE = "pressure";
    String TABLE_WEIGHT = "weight";
    AdminSQLiteOpenHelper admin;
    Context contex;
    Activity activity;
    public FilesManager(Context contex, Activity activity) {
        this.contex = contex;
        this.activity = activity;
        this.userSessionManager = new UserSessionManager(contex);
        this.admin = new AdminSQLiteOpenHelper(this.contex, "bd_one_drop", null, 1); // version es para las futuras modificaciones de la estructura de la bd

    }
    private String getLoguedUsername(){
       return userSessionManager.getLoguedUsername();
    }
    private String createUniqueFileName(){
        String now = new SimpleDateFormat("dd-MM-yyyy_HHmmss").format(new Date());
        return "Reporte ONE DROP - "+getLoguedUsername()+" - "+now+".pdf";
    }

    private ArrayList<String> getRegisters(String tableName){
        ArrayList<String> reg_List = new ArrayList<String>();
        DTOReadAllRegisters results = admin.getAllRegs(tableName);

        if(results != null){
            ArrayList<Integer> reg_ids = results.getReg_ids();
            ArrayList<String> reg_dates = results.getReg_dates();
            ArrayList<Double> reg_values = results.getReg_values();
            ArrayList<String> reg_notes = results.getReg_notes();

            for (int i =0 ; i< reg_ids.size() ; i++){
                String reg = "Nº "+ reg_ids.get(i) +  " >> Fecha: " + reg_dates.get(i) + ", Valor: " + reg_values.get(i) + ", Notas: "+ reg_notes.get(i) + "\n" ;
                reg_List.add(reg);
            }
        }
        return reg_List;
    }

    public Uri exportPdfFileReport(Bitmap bitmap) {
        //Boolean exportSuccess = false;
        String docTitle = "Reportes generados con fecha: "+new SimpleDateFormat("dd/MM/yyyy").format(new Date());

        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();
        TextPaint title = new TextPaint();
        TextPaint subtitle = new TextPaint();
        TextPaint description = new TextPaint();

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(816, 1054, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        // DEBERIA VER LA FORMA DE OBTENER ESTOS DATOS DESDE ESTA CLASE Y NO ENVIANDOLOS POR PARAMETROOOOO
        // DEBERIA VER LA FORMA DE OBTENER ESTOS DATOS DESDE ESTA CLASE Y NO ENVIANDOLOS POR PARAMETROOOOO
        // Bitmap bitmap, bitmapEscala;
        // bitmap = BitmapFactory.decodeResource(AppCompactActivity.getResources(), R.drawable.logo);
        Bitmap bitmapScale = Bitmap.createScaledBitmap(bitmap, 145, 100, false);
        Canvas canvas = page.getCanvas();
        canvas.drawBitmap(bitmapScale, 650, 30, paint);


        // estilos de textos
        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        title.setTextSize(20);
        canvas.drawText(docTitle, 10, 150, title);
        subtitle.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        subtitle.setTextSize(17);
        description.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        description.setTextSize(14);


        int y = 200;
        String line = "-------------------------------------------------------------------------------------------------------------------------------------";
        // OBTENGO CADA ARRAY DE REGISTRO Y LO AGREGO AL CANVA
        ArrayList<String> glyResults = getRegisters(this.TABLE_GLY);
        if (glyResults.size()>0){
            canvas.drawText("Registros de Glucemia", 10, y, subtitle);
            y += 25;
            for (int i = 0; i < glyResults.size(); i++) {
                canvas.drawText(glyResults.get(i), 25, y, description);
                y += 20;
            }
            y += 10;
            canvas.drawText(line, 10, y, description);
            y += 20;
        }else{
            canvas.drawText("No hay registros de glucemia cargados" +
                    ".", 25, y, description);
            y += 30;
        }

        ArrayList<String> pressureResults = getRegisters(this.TABLE_PRESSURE);
        if(pressureResults.size()>0){
            canvas.drawText("Registros de Presion", 10, y, subtitle);
            y += 25;
            for (int i = 0; i < pressureResults.size(); i++) {
                canvas.drawText(pressureResults.get(i), 25, y, description);
                y += 20;
            }
            y += 10;
            canvas.drawText(line, 10, y, description);
            y += 20;
        } else{
            canvas.drawText("No hay registros de presion cargados.", 25, y, description);
            y += 30;
        }

        ArrayList<String> weightResults = getRegisters(this.TABLE_WEIGHT);
        if(weightResults.size()>0){
            canvas.drawText("Registros de Peso", 10, y, subtitle);
            y += 25;
            for (int i = 0; i < weightResults.size(); i++) {
                canvas.drawText(weightResults.get(i), 25, y, description);
                y += 20;
            }
            y += 10;
            canvas.drawText(line, 10, y, description);
            y += 20;
        } else{
            canvas.drawText("No hay registros de peso cargados.", 25, y, description);
            y += 30;
        }
        pdfDocument.finishPage(page);

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), createUniqueFileName());
        try {
            pdfDocument.writeTo(new FileOutputStream(file));
            pdfDocument.close();
            return Uri.fromFile(file);
            //return file.getPath();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
