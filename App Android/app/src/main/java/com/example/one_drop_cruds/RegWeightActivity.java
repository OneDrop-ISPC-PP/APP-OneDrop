package com.example.one_drop_cruds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.one_drop_cruds.entities.DTORegister;
import com.example.one_drop_cruds.entities.DTOReadAllRegisters;
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

public class RegWeightActivity extends AppCompatActivity implements View.OnClickListener{
    AdminSQLiteOpenHelper admin;
    String TABLE_NAME = "weight";
    EditText add_value_weight, add_notes_weight, add_date_weight;
    EditText edit_value_weight, edit_notes_weight, edit_date_weight;
    FloatingActionButton float_btn_add_reg_weight;

    // RECICLER VIEW
    RecyclerView rv1;
    AdapterRegWeight adapterRegWeight;

    //DATA
    ArrayList<Integer> reg_weight_ids = new ArrayList<Integer>();
    ArrayList<String> reg_weight_dates = new ArrayList<String>();
    ArrayList<Double> reg_weight_values = new ArrayList<Double>();
    ArrayList<String> reg_weight_notes = new ArrayList<String>();

    // GRAPHS
    LineChart weightLineChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_weight);

        admin = new AdminSQLiteOpenHelper(this, "bd_one_drop", null, 1); // version es para las futuras modificaciones de la estructura de la bd

        this.refreshRegs(); // CARGAR ARRAYS CON DATA

        // btn float add

        float_btn_add_reg_weight = findViewById(R.id.float_btn_add_reg_weight);

        weightLineChart = findViewById(R.id.weightLineChart);
        this.updateChartRegWeight(); // sobreescribe chart

        // RECICLER VIEW
        rv1 = findViewById(R.id.recyclerView_reg_weight);
        LinearLayoutManager linearLayoutManager_reg_weight = new LinearLayoutManager(this);
        rv1.setLayoutManager(linearLayoutManager_reg_weight);
        adapterRegWeight= new AdapterRegWeight();
        rv1.setAdapter(adapterRegWeight);
    }


    public void toHome(View v){
        Intent home = new Intent(this, Home.class);
        startActivity(home);
    }
    private void updateChartRegWeight(){
        LineDataSet lineDataSet = new LineDataSet(createLineChartDataSet(), "Weight");
        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(lineDataSet);

        //Edito los datos de fecha al formato corto
        ArrayList<String> formatedDates = new ArrayList<String>();
        reg_weight_dates.forEach(date ->{
            formatedDates.add(formatDate(date));
        });
        //Seteo el formateador para leyendas del eje X
        LineData lineData = new LineData(iLineDataSets);
        XAxis xAxis = weightLineChart.getXAxis();
        xAxis.setValueFormatter(new DateAxisValueFormatter(formatedDates));

        weightLineChart.setData(lineData);
        weightLineChart.invalidate();
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
        weightLineChart.setBackgroundColor(getResources().getColor(R.color.celeste_fondo)); // COLOR FONDO OPCION
        weightLineChart.setNoDataText("Aun no hay registros guardados.."); // TEXTO SI NO HAY INFO
        weightLineChart.setNoDataTextColor(R.color.pinkonedrop); // TEXTO SI NO HAY INFO
        weightLineChart.setTouchEnabled(true); // permite tactil
        weightLineChart.setPinchZoom(true); // permite zoom tactil
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
        reg_weight_dates.forEach(date ->{
            Double value = reg_weight_values.get(reg_weight_dates.indexOf(date));
            int index = reg_weight_dates.indexOf(date);
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
        builder.setMessage("Editar registro de peso");
        View popupEditReg = getLayoutInflater().inflate(R.layout.popup_form_edit_reg_weight, null);
        builder.setView(popupEditReg); // ESTO ES PARA QUE PUEDA OBTENER LAS REFERENCIAS DESDE popupEditReg Y PODER OBTENER EL CONTROL DE LOS ELEMENTOS
        edit_value_weight = popupEditReg.findViewById(R.id.edit_value_weight);
        edit_notes_weight = popupEditReg.findViewById(R.id.edit_notes_weight);
        edit_date_weight = popupEditReg.findViewById(R.id.edit_date_weight);

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
        builder.setMessage("Agregar registro de peso");
        View popupAddReg = getLayoutInflater().inflate(R.layout.popup_form_add_reg_weight, null);
        builder.setView(popupAddReg); // ESTO ES PARA QUE PUEDA OBTENER LAS REFERENCIAS DESDE popupAddReg Y PODER OBTENER EL CONTROL DE LOS ELEMENTOS
        add_value_weight = popupAddReg.findViewById(R.id.add_value_weight);
        add_notes_weight = popupAddReg.findViewById(R.id.add_notes_weight);

        add_date_weight = popupAddReg.findViewById(R.id.add_date_weight);

        // ESTA FORMA AGREGA A ESTA MISMA CLASE COMO LISTENER Y LUEGO EN UN SWITCH SE ELIJE EL EVENTO SEGUN SU ID..
        add_date_weight.setOnClickListener(this);

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
    private class AdapterRegWeight extends RecyclerView.Adapter<AdapterRegWeight.AdapterRegWeightHolder> {
        @NonNull
        @Override
        public AdapterRegWeight.AdapterRegWeightHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new AdapterRegWeight.AdapterRegWeightHolder(getLayoutInflater().inflate(R.layout.recicler_view_reg_weight,parent,false));
        }
        @Override
        public void onBindViewHolder(@NonNull AdapterRegWeight.AdapterRegWeightHolder holder, int position) {
            try {
                holder.printItem(position);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        @Override
        public int getItemCount() {
            return reg_weight_dates.size(); // debe retornar la cantidad de registros..
        }
        // Clase que se encargara de IMPRIMIR todos los elementos
        private class AdapterRegWeightHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            TextView reg_date;
            TextView reg_value;
            TextView reg_note;
            Button btn_edit_reg_weight, btn_delete_reg_weight;
            public AdapterRegWeightHolder(@NonNull View itemView) {
                super(itemView);
                reg_date = itemView.findViewById(R.id.recycler_reg_weight_date);
                reg_value = itemView.findViewById(R.id.recycler_reg_weight_value);
                reg_note = itemView.findViewById(R.id.recycler_reg_weight_note);
                btn_edit_reg_weight = itemView.findViewById(R.id.recycler_btn_edit_reg_weight);
                btn_delete_reg_weight = itemView.findViewById(R.id.recycler_btn_delete_reg_weight);
                itemView.setOnClickListener(this);
            }
            public void printItem(int position) throws ParseException {
                reg_date.setText(formatDate(reg_weight_dates.get(position)));
                reg_value.setText(String.valueOf(reg_weight_values.get(position)));
                reg_note.setText(reg_weight_notes.get(position));

                btn_edit_reg_weight.setOnClickListener(view -> {
                    btnEditOnClick(reg_weight_ids.get(getLayoutPosition()));
                });
                btn_delete_reg_weight.setOnClickListener(view -> {
                    btnDeleteOnClick(reg_weight_ids.get(getLayoutPosition()));
                });
            }
            @Override
            public void onClick(View v) {
                Toast.makeText(RegWeightActivity.this, String.valueOf(reg_weight_ids.get(getLayoutPosition())),Toast.LENGTH_SHORT).show();
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
            reg_weight_ids.clear();
            reg_weight_dates.clear();
            reg_weight_values.clear();
            reg_weight_notes.clear();

            // SETEO A LOS VALORES RECIBIDOS POR BD
            reg_weight_ids = results.getReg_ids();
            reg_weight_dates = results.getReg_dates();
            reg_weight_values = results.getReg_values();
            reg_weight_notes = results.getReg_notes();
        } else{
            Toast.makeText(this, "Aun no hay registros guardados..", Toast.LENGTH_LONG).show();
        }
    }
    public void addNewReg(){
        String newDate;
        if(add_date_weight.getText().toString().equals("") ){
            System.out.println("CREO NUEVA FECHA NOW PORQUE VINO VACIA");
            newDate = new Date().toString();
        } else {
            newDate = add_date_weight.getText().toString();
        }
        DTORegister newReg = new DTORegister(newDate ,Double.valueOf(add_value_weight.getText().toString()) , add_notes_weight.getText().toString());

        Boolean insertResult = admin.addReg(this.TABLE_NAME, newReg);

        if(insertResult){
            add_value_weight.setText("");// limpio pantalla
            add_notes_weight.setText("");
            add_date_weight.setText(""); this.refreshRegs();
            this.updateChartRegWeight(); // sobreescribe chart
            adapterRegWeight.notifyDataSetChanged(); // refresca pantalla del recycler
            rv1.smoothScrollToPosition(reg_weight_ids.size()-1); // mueve la vista al ultimo elemento agregado
            Toast.makeText(this,"Se agrego registro de peso", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,"Error agregando registro!", Toast.LENGTH_SHORT).show();
        }
    }
    public void deleteReg(int id){

        // DEBO SEPARAR EN RESPONSABILIDADES con admin sqlite helper ANTES DE AGREGAR LOS OTROS CRUDS, NO VA A SER ESCALABLE
        // DEBO SEPARAR EN RESPONSABILIDADES con admin sqlite helper ANTES DE AGREGAR LOS OTROS CRUDS, NO VA A SER ESCALABLE
        // DEBO SEPARAR EN RESPONSABILIDADES con admin sqlite helper ANTES DE AGREGAR LOS OTROS CRUDS, NO VA A SER ESCALABLE
        // DEBO SEPARAR EN RESPONSABILIDADES con admin sqlite helper ANTES DE AGREGAR LOS OTROS CRUDS, NO VA A SER ESCALABLE

        SQLiteDatabase bd = admin.getWritableDatabase();
        int deletedRow = bd.delete("weight", "id_reg_weight = "+id, null);
        bd.close(); // cierro conexion bd

        if(deletedRow == 1){
            this.refreshRegs(); // actualiza array de reg
            this.updateChartRegWeight(); // sobreescribe chart
            adapterRegWeight.notifyDataSetChanged(); // refresca pantalla del recycler
            Toast.makeText(this, "Registro eliminado correctamente", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Error eliminando registro", Toast.LENGTH_LONG).show();
        }
    }
    public void setTextEditRegPopup(int id){
        DTORegister regById = admin.getRegById(this.TABLE_NAME, id);
        if (regById != null){
            edit_date_weight.setText(regById.getDate());
            edit_value_weight.setText(String.valueOf(regById.getValue()));
            edit_notes_weight.setText(regById.getNotes());
        }else{
            Toast.makeText(this,"Error editando registro", Toast.LENGTH_SHORT).show();
        }
    }
    public void updateEditedReg(int id){
        DTORegister dtoUpdated = new DTORegister(edit_date_weight.getText().toString(), Double.valueOf(edit_value_weight.getText().toString()) ,edit_notes_weight.getText().toString());
        boolean updateResult = admin.updateRegById(this.TABLE_NAME,id,dtoUpdated);
        if (updateResult){
            edit_value_weight.setText("");// limpio pantalla
            edit_notes_weight.setText("");
            edit_date_weight.setText("");
            this.refreshRegs(); // actualiza array de reg
            this.updateChartRegWeight(); // actualiza chart
            adapterRegWeight.notifyDataSetChanged(); // refresca pantalla del recycler
            Toast.makeText(this, "Registro actualizado!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Error al editar registro", Toast.LENGTH_LONG).show();
        }
    }
}