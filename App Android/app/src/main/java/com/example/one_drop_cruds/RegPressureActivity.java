package com.example.one_drop_cruds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.one_drop_cruds.entities.DTOReadAllRegisters;
import com.example.one_drop_cruds.entities.DTORegister;
import com.example.one_drop_cruds.utils.AdminSQLiteOpenHelper;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RegPressureActivity extends AppCompatActivity implements View.OnClickListener{

    AdminSQLiteOpenHelper admin;
    String TABLE_NAME = "pressure";
    EditText add_value_pressure, add_notes_pressure, add_date_pressure;
    EditText edit_value_pressure, edit_notes_pressure, edit_date_pressure;
    // Button btn_add_reg_pressure;
    FloatingActionButton float_btn_add_reg_pressure;

    // RECICLER VIEW
    RecyclerView rv1;
    RegPressureActivity.AdapterRegPressure adapterRegPressure;

    //DATA
    ArrayList<Integer> reg_pressure_ids = new ArrayList<Integer>();
    ArrayList<String> reg_pressure_dates = new ArrayList<String>();
    ArrayList<Double> reg_pressure_values = new ArrayList<Double>();
    ArrayList<String> reg_pressure_notes = new ArrayList<String>();

    // GRAPHS
    LineChart lineChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_pressure);

        admin = new AdminSQLiteOpenHelper(this, "bd_one_drop", null, 1); // version es para las futuras modificaciones de la estructura de la bd

        this.refreshRegs(); // CARGAR ARRAYS CON DATA

        // btn float add
        float_btn_add_reg_pressure = findViewById(R.id.float_btn_add_reg_pressure);

        lineChart = findViewById(R.id.pressureLineChart);
        this.updateChartRegPressure(); // sobreescribe chart

        // RECICLER VIEW
        rv1 = findViewById(R.id.recyclerView_reg_pressure);
        LinearLayoutManager linearLayoutManager_reg_pressure = new LinearLayoutManager(this);
        rv1.setLayoutManager(linearLayoutManager_reg_pressure);
        adapterRegPressure = new AdapterRegPressure();
        rv1.setAdapter(adapterRegPressure);
    }


    public void toHome(View v){
        Intent home = new Intent(this, Home.class);
        startActivity(home);
    }
    private void updateChartRegPressure(){
        LineDataSet lineDataSet = new LineDataSet(createLineChartDataSet(), "Presion");
        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(lineDataSet);

        //Edito los datos de fecha al formato corto
        ArrayList<String> formatedDates = new ArrayList<String>();
        reg_pressure_dates.forEach(date ->{
            formatedDates.add(formatDate(date));
        });
        //Seteo el formateador para leyendas del eje X
        LineData lineData = new LineData(iLineDataSets);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new RegPressureActivity.DateAxisValueFormatter(formatedDates));

        lineChart.setData(lineData);
        lineChart.invalidate();
        // PERSONALIZACION
        lineDataSet.setColor(R.color.pinkonedrop2); // COLOR LINEA
        lineDataSet.setCircleColor(R.color.pinkonedrop); // COLOR PUNTOS?
        lineDataSet.setDrawCircles(true); // HABILITA QUE SE MUESTRE LOS PUNTOS
        // lineDataSet.setDrawCircleHole(true); // LOS PUNTOS LOS MUESTRA COMO ARANDELAS
        lineDataSet.setLineWidth(4); // GROSOR LINEA
        lineDataSet.setCircleRadius(5); // diametro ext de punto
        lineDataSet.setCircleHoleRadius(1); // diam interno punto
        lineDataSet.setValueTextSize(2); // tamaño texxto valot
        lineDataSet.setValueTextColor(Color.BLACK); // COLOR TEXTO
        lineChart.setBackgroundColor(getResources().getColor(R.color.celeste_fondo)); // COLOR FONDO OPCION
        lineChart.setNoDataText("Aun no hay registros guardados.."); // TEXTO SI NO HAY INFO
        lineChart.setNoDataTextColor(R.color.pinkonedrop); // TEXTO SI NO HAY INFO
        lineChart.setTouchEnabled(true); // permite tactil
        lineChart.setPinchZoom(true); // permite zoom tactil
    }
    public class DateAxisValueFormatter extends IndexAxisValueFormatter {
        private List<String> mValues;
        public DateAxisValueFormatter(List<String> values){
            this.mValues = values;
        }
        @Override
        public String getFormattedValue(float value) {
            int index = (int) value;
            if(index <0 || index>= mValues.size()){
                return "";
            }
            return mValues.get(index);
        }

    }
    private ArrayList<Entry> createLineChartDataSet(){
        ArrayList<Entry> dataSet = new ArrayList<Entry>();
        reg_pressure_dates.forEach(date ->{
            Double value = reg_pressure_values.get(reg_pressure_dates.indexOf(date));
            int index = reg_pressure_dates.indexOf(date);
            dataSet.add(new Entry (index, Float.valueOf(String.valueOf(value))));
        });
        return dataSet;
    }

    public String formatDate (String inputDate){
        Date date = null;
        try {
            // Creo un formateador que reciba un string del fomrato "E MMM dd HH:mm:ss z yyyy" y lo cree un obj date
            SimpleDateFormat inputPatternFormatter = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
            date = inputPatternFormatter.parse(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // Creo un formateador que reciba un date y lo pase al formato deseado "E dd '-' HH:mm'hs'"
        SimpleDateFormat outputPatternFormatter = new SimpleDateFormat("E dd '-' HH:mm'hs'", Locale.ENGLISH);
        return outputPatternFormatter.format(date);
    }
    public void openPopupBtnEdit(int id_reg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Editar registro de presion");
        View popupEditReg = getLayoutInflater().inflate(R.layout.popup_form_edit_reg_pressure, null);
        builder.setView(popupEditReg); // ESTO ES PARA QUE PUEDA OBTENER LAS REFERENCIAS DESDE popupEditReg Y PODER OBTENER EL CONTROL DE LOS ELEMENTOS
        edit_value_pressure = popupEditReg.findViewById(R.id.edit_value_pressure);
        edit_notes_pressure = popupEditReg.findViewById(R.id.edit_notes_pressure);
        edit_date_pressure = popupEditReg.findViewById(R.id.edit_date_pressure);

        setTextEditRegPopup(id_reg); // esto es para setear los campos del popup con la info de la bd
        builder.setPositiveButton("¡Editar!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                updateEditedReg(id_reg); // toma los campos modificados y actualiza la bd
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    public void openPopupBtnDel(int id_reg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Eliminar este registro?");
        builder.setPositiveButton("¡Eliminar!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteReg(id_reg);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    public void openPopupAddReg(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Agregar registro de presion");
        View popupAddReg = getLayoutInflater().inflate(R.layout.popup_form_add_reg_pressure, null);
        builder.setView(popupAddReg); // ESTO ES PARA QUE PUEDA OBTENER LAS REFERENCIAS DESDE popupAddReg Y PODER OBTENER EL CONTROL DE LOS ELEMENTOS
        add_value_pressure = popupAddReg.findViewById(R.id.add_value_pressure);
        add_notes_pressure = popupAddReg.findViewById(R.id.add_notes_pressure);

        add_date_pressure = popupAddReg.findViewById(R.id.add_date_pressure);
        // add_date_pressure = popupAddReg.findViewById(R.id.edit_date_pressure_pickdater); // ESTE ES PARA EL PICKDATER
        // ESTA FORMA AGREGA A ESTA MISMA CLASE COMO LISTENER Y LUEGO EN UN SWITCH SE ELIJE EL EVENTO SEGUN SU ID..
        add_date_pressure.setOnClickListener(this);

        builder.setPositiveButton("¡Agregar!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                addNewReg();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }
    @Override
    public void onClick(View view) {

    }

    // RECICLER VIEW
    // RECICLER VIEW Clase que se encargara de CREAR todos los elementos de lista
    private class AdapterRegPressure extends RecyclerView.Adapter<RegPressureActivity.AdapterRegPressure.AdapterRegPressureHolder> {
        @NonNull
        @Override
        public RegPressureActivity.AdapterRegPressure.AdapterRegPressureHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new RegPressureActivity.AdapterRegPressure.AdapterRegPressureHolder(getLayoutInflater().inflate(R.layout.recicler_view_reg_pressure,parent,false));
        }
        @Override
        public void onBindViewHolder(@NonNull RegPressureActivity.AdapterRegPressure.AdapterRegPressureHolder holder, int position) {
            try {
                holder.printItem(position);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        @Override
        public int getItemCount() {
            return reg_pressure_dates.size(); // debe retornar la cantidad de registros..
        }
        // Clase que se encargara de IMPRIMIR todos los elementos
        private class AdapterRegPressureHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            TextView reg_date;
            TextView reg_value;
            TextView reg_note;
            Button btn_edit_reg_Pressure, btn_delete_reg_Pressure;
            public AdapterRegPressureHolder(@NonNull View itemView) {
                super(itemView);
                reg_date = itemView.findViewById(R.id.recycler_reg_pressure_date);
                reg_value = itemView.findViewById(R.id.recycler_reg_pressure_value);
                reg_note = itemView.findViewById(R.id.recycler_reg_pressure_note);
                btn_edit_reg_Pressure = itemView.findViewById(R.id.recycler_btn_edit_reg_pressure);
                btn_delete_reg_Pressure = itemView.findViewById(R.id.recycler_btn_delete_reg_pressure);
                itemView.setOnClickListener(this);
            }
            public void printItem(int position) throws ParseException {
                reg_date.setText(formatDate(reg_pressure_dates.get(position)));
                reg_value.setText(String.valueOf(reg_pressure_values.get(position)));
                reg_note.setText(reg_pressure_notes.get(position));

                btn_edit_reg_Pressure.setOnClickListener(view -> {
                    btnEditOnClick(reg_pressure_ids.get(getLayoutPosition()));
                });
                btn_delete_reg_Pressure.setOnClickListener(view -> {
                    btnDeleteOnClick(reg_pressure_ids.get(getLayoutPosition()));
                });
            }
            @Override
            public void onClick(View v) {
                Toast.makeText(RegPressureActivity.this, String.valueOf(reg_pressure_ids.get(getLayoutPosition())),Toast.LENGTH_SHORT).show();
            }
            public void btnEditOnClick(int id){
                openPopupBtnEdit(id);
            }
            public void btnDeleteOnClick(int id){
                openPopupBtnDel(id);
            }
        }
    }

    //USO DE BD -- USO DE BD -- USO DE BD -- USO DE BD
    public void refreshRegs(){
        DTOReadAllRegisters results = admin.getAllRegs(this.TABLE_NAME);

        if (results != null){
            // limpio arrays para recibir los datos nuevos..
            reg_pressure_ids.clear();
            reg_pressure_dates.clear();
            reg_pressure_values.clear();
            reg_pressure_notes.clear();

            // SETEO A LOS VALORES RECIBIDOS POR BD
            reg_pressure_ids = results.getReg_ids();
            reg_pressure_dates = results.getReg_dates();
            reg_pressure_values = results.getReg_values();
            reg_pressure_notes = results.getReg_notes();
        } else{
            Toast.makeText(this, "Aun no hay registros guardados..", Toast.LENGTH_LONG).show();
        }
    }
    public void addNewReg(){
        String newDate;
        if(add_date_pressure.getText().toString().equals("") ){
            System.out.println("CREO NUEVA FECHA NOW PORQUE VINO VACIA");
            newDate = new Date().toString();
        } else {
            newDate = add_date_pressure.getText().toString();
        }
        DTORegister newReg = new DTORegister(newDate ,Double.valueOf(add_value_pressure.getText().toString()) , add_notes_pressure.getText().toString());

        Boolean insertResult = admin.addReg(this.TABLE_NAME, newReg);

        if(insertResult){
            add_value_pressure.setText("");// limpio pantalla
            add_notes_pressure.setText("");
            add_date_pressure.setText(""); this.refreshRegs();
            this.updateChartRegPressure(); // sobreescribe chart
            adapterRegPressure.notifyDataSetChanged(); // refresca pantalla del recycler
            rv1.smoothScrollToPosition(reg_pressure_ids.size()-1); // mueve la vista al ultimo elemento agregado
            Toast.makeText(this,"Se agrego registro de presion", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,"Error agregando registro!", Toast.LENGTH_SHORT).show();
        }
    }
    public void deleteReg(int id){
        Boolean deleteResult = admin.deleteReg(this.TABLE_NAME , id);
        if(deleteResult){
            this.refreshRegs(); // actualiza array de reg
            this.updateChartRegPressure(); // sobreescribe chart
            adapterRegPressure.notifyDataSetChanged(); // refresca pantalla del recycler
            Toast.makeText(this, "Registro eliminado correctamente", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Error eliminando registro", Toast.LENGTH_LONG).show();
        }
    }
    public void setTextEditRegPopup(int id){
        DTORegister regById = admin.getRegById(this.TABLE_NAME, id);
        if (regById != null){
            edit_date_pressure.setText(regById.getDate());
            edit_value_pressure.setText(String.valueOf(regById.getValue()));
            edit_notes_pressure.setText(regById.getNotes());
        }else{
            Toast.makeText(this,"Error editando registro", Toast.LENGTH_SHORT).show();
        }
    }
    public void updateEditedReg(int id){
        DTORegister dtoUpdated = new DTORegister(edit_date_pressure.getText().toString(), Double.valueOf(edit_value_pressure.getText().toString()) ,edit_notes_pressure.getText().toString());
        boolean updateResult = admin.updateRegById(this.TABLE_NAME,id,dtoUpdated);
        if (updateResult){
            edit_value_pressure.setText("");// limpio pantalla
            edit_notes_pressure.setText("");
            edit_date_pressure.setText("");
            this.refreshRegs(); // actualiza array de reg
            this.updateChartRegPressure(); // actualiza chart
            adapterRegPressure.notifyDataSetChanged(); // refresca pantalla del recycler
            Toast.makeText(this, "Registro actualizado!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Error al editar registro", Toast.LENGTH_LONG).show();
        }
    }


}