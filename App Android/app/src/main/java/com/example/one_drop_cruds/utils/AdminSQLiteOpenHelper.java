package com.example.one_drop_cruds.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.one_drop_cruds.entities.DTORegister;
import com.example.one_drop_cruds.entities.DTOReadAllRegisters;
import com.example.one_drop_cruds.entities.DTOmedicalRecord;
import com.example.one_drop_cruds.utils.PasswordEncoder;

import java.util.ArrayList;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    PasswordEncoder passwordEncoder = new PasswordEncoder();

    // Definir la constante COLUMN_IMAGE_URI
    private static final String COLUMN_IMAGE_URI = "image_uri";
    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        createTables();
    }
    public void createTables(){
        SQLiteDatabase db = this.getWritableDatabase();// abre la bd
        //CREACION DE TABLAS se ejecuta una vez
        // REG GLUCEMIA
        db.execSQL("CREATE TABLE IF NOT EXISTS glycemia (\n"+
                " id_reg_glycemia INTEGER PRIMARY KEY AUTOINCREMENT, \n"+
                " date DATETIME NOT NULL, \n"+
                " value REAL, \n"+
                " notes TEXT\t\n"+
                ")");
        //USERS
        db.execSQL("CREATE TABLE IF NOT EXISTS users (\n"+
                " email TEXT PRIMARY KEY, \n"+
                " password TEXT NOT NULL\t\n"+
                ")");
        //PRESSURE
        db.execSQL("CREATE TABLE IF NOT EXISTS pressure (\n"+
                " id_reg_pressure INTEGER PRIMARY KEY AUTOINCREMENT, \n"+
                " date DATETIME NOT NULL, \n"+
                " value REAL, \n"+
                " notes TEXT\t\n"+
                ")");
        //WEIGHT
        db.execSQL("CREATE TABLE IF NOT EXISTS weight (\n"+
                " id_reg_weight INTEGER PRIMARY KEY AUTOINCREMENT, \n"+
                " date DATETIME NOT NULL, \n"+
                " value REAL, \n"+
                " notes TEXT\t\n"+
                ")");

        //FICHA MEDICA
        db.execSQL("CREATE TABLE IF NOT EXISTS medical_record (\n"+
                " id_medical_record INTEGER PRIMARY KEY AUTOINCREMENT, \n"+
                " name TEXT, \n"+
                " lastName TEXT, \n"+
                " age INTEGER, \n"+
                " birth DATE, \n"+
                " weight REAL, \n"+
                " db_type TEXT, \n"+
                " username TEXT, \n"+
                " db_therapy TEXT\t\n"+
                ")");
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // REG GLUCEMIA
        db.execSQL("CREATE TABLE IF NOT EXISTS glycemia (\n"+
                " id_reg_glycemia INTEGER PRIMARY KEY AUTOINCREMENT, \n"+
                " date DATETIME NOT NULL, \n"+
                " value REAL, \n"+
                " notes TEXT\t\n"+
                ")");
        //USERS
        db.execSQL("CREATE TABLE IF NOT EXISTS users (\n"+
                " email TEXT PRIMARY KEY, \n"+
                " password TEXT NOT NULL\t\n"+
                ")");
        //PRESSURE
        db.execSQL("CREATE TABLE IF NOT EXISTS pressure (\n"+
                " id_reg_pressure INTEGER PRIMARY KEY AUTOINCREMENT, \n"+
                " date DATETIME NOT NULL, \n"+
                " value REAL, \n"+
                " notes TEXT\t\n"+
                ")");
        //WEIGHT
        db.execSQL("CREATE TABLE IF NOT EXISTS weight (\n"+
                " id_reg_weight INTEGER PRIMARY KEY AUTOINCREMENT, \n"+
                " date DATETIME NOT NULL, \n"+
                " value REAL, \n"+
                " notes TEXT\t\n"+
                ")");

        //FICHA MEDICA

        db.execSQL("CREATE TABLE IF NOT EXISTS medical_record (\n"+
                " id_medical_record INTEGER PRIMARY KEY AUTOINCREMENT, \n"+
                " name TEXT, \n"+
                " lastName TEXT, \n"+
                " age INTEGER, \n"+
                " birth DATE, \n"+
                " weight REAL, \n"+
                " db_type TEXT, \n"+
                " username TEXT UNIQUE, \n"+
                " db_therapy TEXT\t\n"+
                ")");

    }

    public boolean createMedicalRecord(DTOmedicalRecord dtomedicalRecord){
        SQLiteDatabase bd = this.getWritableDatabase();// abre la bd
        ContentValues new_reg = new ContentValues(); // crea un objeto que luego sera un nuevo registro en la bd

        new_reg.put("username", dtomedicalRecord.getUsername());
        new_reg.put("name", dtomedicalRecord.getName());
        new_reg.put("lastName", dtomedicalRecord.getLast_name());
        new_reg.put("age", dtomedicalRecord.getAge());
        new_reg.put("birth", dtomedicalRecord.getBirth());
        new_reg.put("weight", dtomedicalRecord.getWeight());
        new_reg.put("db_type", dtomedicalRecord.getDbType());
        new_reg.put("db_therapy", dtomedicalRecord.getDbTherapy());

        Long insertResult = bd.insert("medical_record", null, new_reg);// inserta en tabla

        bd.close();// cierro conexion bd
        return insertResult == -1? false : true; // devuelve el id si logra insertar, sino un -1
    }


    public DTOmedicalRecord getMedicalRecord(String username){
        DTOmedicalRecord medicalRecord = new DTOmedicalRecord();
        SQLiteDatabase bd = this.getWritableDatabase(); // abre la bd
        Cursor bdResults = bd.rawQuery("SELECT * FROM medical_record WHERE username = '"+username +"'", null); // este obj ejecuta la consulta a bd

        if (bdResults.moveToFirst()){
            medicalRecord.setName(bdResults.getString(1));
            medicalRecord.setLast_name(bdResults.getString(2));
            medicalRecord.setAge(bdResults.getInt(3));
            medicalRecord.setBirth(bdResults.getString(4));
            medicalRecord.setWeight(bdResults.getDouble(5));
            medicalRecord.setDbType(bdResults.getString(6));
            medicalRecord.setUsername(bdResults.getString(7));
            medicalRecord.setDbTherapy(bdResults.getString(8));
        } else{
            medicalRecord = null;
        }
        bd.close(); // cierro conexion bd
        return  medicalRecord;
    }

    public boolean updateMedicalRecord(DTOmedicalRecord dtomedicalRecord){
        SQLiteDatabase bd = this.getWritableDatabase();
        ContentValues editedMR = new ContentValues(); // crea un objeto que luego actualizara
        editedMR.put("username", dtomedicalRecord.getUsername());// agrego datos al objeto registro
        editedMR.put("name", dtomedicalRecord.getName());
        editedMR.put("lastName", dtomedicalRecord.getLast_name());
        editedMR.put("age", dtomedicalRecord.getAge());
        editedMR.put("birth", dtomedicalRecord.getBirth());
        editedMR.put("weight", dtomedicalRecord.getWeight());
        editedMR.put("db_type", dtomedicalRecord.getDbType());
        editedMR.put("db_therapy", dtomedicalRecord.getDbTherapy());

        int editedRows = bd.update("medical_record", editedMR, "username = '"+dtomedicalRecord.getUsername()+"'", null);
        return editedRows == 1? true : false;
    }

    public DTOReadAllRegisters getAllRegs(String tableName){
        DTOReadAllRegisters result = new DTOReadAllRegisters();
        ArrayList<Integer> reg_ids = new ArrayList<Integer>();
        ArrayList<String> reg_dates = new ArrayList<String>();
        ArrayList<Double> reg_values = new ArrayList<Double>();
        ArrayList<String> reg_notes = new ArrayList<String>();

        SQLiteDatabase bd = this.getWritableDatabase(); // abre la bd
        Cursor bdResults = bd.rawQuery("SELECT * FROM " + tableName, null); // este obj ejecuta la consulta a bd

        if (bdResults.moveToFirst()){
            do {
                reg_ids.add(Integer.valueOf(bdResults.getInt(0)));
                reg_dates.add(bdResults.getString(1));
                reg_values.add(bdResults.getDouble(2));
                reg_notes.add(bdResults.getString(3));
            }
            while (bdResults.moveToNext());

            result.setReg_ids(reg_ids);
            result.setReg_values(reg_values);
            result.setReg_dates(reg_dates);
            result.setReg_notes(reg_notes);

        } else{
            result = null;
        }
        bd.close(); // cierro conexion bd
        return result;
    }
    public boolean addReg(String tableName, DTORegister newReg){
        SQLiteDatabase bd = this.getWritableDatabase();// abre la bd
        ContentValues new_reg = new ContentValues(); // crea un objeto que luego sera un nuevo registro en la bd

        new_reg.put("date", newReg.getDate());
        new_reg.put("value", newReg.getValue());
        new_reg.put("notes", newReg.getNotes());
        Long insertResult = bd.insert(tableName, null, new_reg);// inserta en tabla

        bd.close();// cierro conexion bd
        return insertResult == -1? false : true; // devuelve el id si logra insertar, sino un -1
    }
    public boolean deleteReg (String tableName, int id){
        SQLiteDatabase bd = this.getWritableDatabase();
        int deletedRow = bd.delete(tableName, "id_reg_"+tableName+" = "+id, null);
        bd.close(); // cierro conexion bd
        return deletedRow == 1? true : false;
    }
    public DTORegister getRegById(String tableName , int id){
        DTORegister regDTO = new DTORegister();
        SQLiteDatabase bd = this.getWritableDatabase(); // abre la bd
        Cursor regById = bd.rawQuery("SELECT * FROM "+tableName+" WHERE id_reg_" + tableName + " = " +id, null); // Busco el registro por id
        if(regById.moveToFirst()) {
            regDTO.setDate(regById.getString(1));
            regDTO.setValue(regById.getDouble(2));
            regDTO.setNotes(regById.getString(3));
        } else {
            return null;
        }
        return regDTO;
    }
    public boolean updateRegById(String tableName, int id , DTORegister dtoUpdated){
        SQLiteDatabase bd = this.getWritableDatabase();
        ContentValues editedReg = new ContentValues(); // crea un objeto que luego actualizara
        editedReg.put("date", dtoUpdated.getDate());// agrego datos al objeto registro
        editedReg.put("value", dtoUpdated.getValue());
        editedReg.put("notes", dtoUpdated.getNotes());
        int editedRows = bd.update(tableName, editedReg, "id_reg_" + tableName +" = "+id, null);
        return editedRows == 1? true : false;
    }
    public Boolean createUser(String email, String password){
        SQLiteDatabase bd = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", passwordEncoder.encodePassword(password));
        long result = bd.insert("users", null, contentValues);
        return result == -1? false : true;
    }
    public Boolean checkEmail(String email){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT * FROM users WHERE email = ?", new String[]{email});
        if(cursor.getCount() > 0) {
            return true;
        }else {
            return false;
        }
    }

    // Método para actualizar la URI de la imagen en la base de datos
    public boolean updateImageUri(String username, String imageUri) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IMAGE_URI, imageUri);

        int updatedRows = db.update("medical_record", values, "username = ?", new String[] { username });
        db.close();

        return updatedRows == 1;

    }
    public Boolean checkEmailPassword(String email, String password){
        SQLiteDatabase bd = this.getWritableDatabase();
        Cursor regByEmail= bd.rawQuery("SELECT * FROM users WHERE email = ?", new String[]{email});// Busco el registro por EMAIL
        if (regByEmail.moveToFirst()){
            bd.close();
            return passwordEncoder.verifyPassword(password, regByEmail.getString(1));
        }
        bd.close();
        return false;
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // cuando se va modificando la app
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS users");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS glycemia");
    }

}
