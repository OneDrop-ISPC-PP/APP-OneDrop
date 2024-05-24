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

import com.example.one_drop_cruds.entities.DTORegister;
import com.example.one_drop_cruds.entities.DTOReadAllRegisters;
import com.example.one_drop_cruds.entities.user.AuthResponse;
import com.example.one_drop_cruds.entities.user.FichaMedicaUsuario;
import com.example.one_drop_cruds.entities.user.LoguedUserDetails;
import com.example.one_drop_cruds.entities.user.Record;
import com.example.one_drop_cruds.entities.user.RecordsPaginatedReadDtoArray;
import com.example.one_drop_cruds.entities.user.RegisterRequest;
import com.example.one_drop_cruds.entities.user.enums.ErrorResponse;
import com.example.one_drop_cruds.request.AuthRequests;
import com.example.one_drop_cruds.request.RecordsRequest;
import com.example.one_drop_cruds.utils.AdminSQLiteOpenHelper;
import com.example.one_drop_cruds.utils.BackendUrl;
import com.example.one_drop_cruds.utils.SharedPrefManager;
import com.example.one_drop_cruds.utils.UserSessionManager;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegGlyActivity extends AppCompatActivity implements View.OnClickListener{
    UserSessionManager userSessionManager;
    String baseUrl = new BackendUrl().getBackendUrl();
    String token;
    LoguedUserDetails loguedUser;
    SharedPrefManager sharedPrefManager;
    // todo eliminar AdminSQLiteOpenHelper admin;
    // todo eliminar String TABLE_NAME = "glycemia";
    EditText add_value_gly, add_notes_gly, add_date_gly;
    EditText edit_value_gly, edit_notes_gly, edit_date_gly;
    // Button btn_add_reg_gly;
    FloatingActionButton float_btn_add_reg_gly;

    // RECICLER VIEW
    RecyclerView rv1;
    AdapterRegGly adapterRegGly;

    //DATA
    ArrayList<Integer> reg_gly_ids = new ArrayList<Integer>();
    ArrayList<String> reg_gly_dates = new ArrayList<String>();
    ArrayList<Double> reg_gly_values = new ArrayList<Double>();
    ArrayList<String> reg_gly_notes = new ArrayList<String>();


    // GRAPHS
    LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_gly);
        // TODO ELIMINAR admin = new AdminSQLiteOpenHelper(this, "bd_one_drop", null, 1); // version es para las futuras modificaciones de la estructura de la bd

        userSessionManager = new UserSessionManager(getApplicationContext());
        sharedPrefManager = new SharedPrefManager(getApplicationContext(), "oneDrop_shared_preferences");
        token = sharedPrefManager.getUserToken();
        loguedUser = userSessionManager.getLoguedUserDetails();  // SI NO ESTA LOGUEADO, SE REDIRIGE A LOGIN

        this.refreshRegs(); // Obtiene data, la setea y grafica

        // btn float add
        float_btn_add_reg_gly = findViewById(R.id.float_btn_add_reg_gly);

        lineChart = findViewById(R.id.glyLineChart);

        // RECICLER VIEW
        rv1 = findViewById(R.id.recyclerView_reg_gly);
        LinearLayoutManager linearLayoutManager_reg_gly = new LinearLayoutManager(this);
        rv1.setLayoutManager(linearLayoutManager_reg_gly);

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
        reg_gly_dates.forEach(date ->{
            Double value = reg_gly_values.get(reg_gly_dates.indexOf(date));
            int index = reg_gly_dates.indexOf(date);
            dataSet.add(new Entry (index, Float.valueOf(String.valueOf(value))));
        });
        return dataSet;
    }
    public void openPopupBtnEdit(int id_reg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Editar registro de glucemia");
        View popupEditReg = getLayoutInflater().inflate(R.layout.popup_form_edit_reg_gly, null);
        builder.setView(popupEditReg); // ESTO ES PARA QUE PUEDA OBTENER LAS REFERENCIAS DESDE popupEditReg Y PODER OBTENER EL CONTROL DE LOS ELEMENTOS
        edit_value_gly = popupEditReg.findViewById(R.id.edit_value_gly);
        edit_notes_gly = popupEditReg.findViewById(R.id.edit_notes_gly);
        edit_date_gly = popupEditReg.findViewById(R.id.edit_date_gly);

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
        builder.setMessage("Agregar registro de glucemia");
        View popupAddReg = getLayoutInflater().inflate(R.layout.popup_form_add_reg_gly, null);
        builder.setView(popupAddReg); // ESTO ES PARA QUE PUEDA OBTENER LAS REFERENCIAS DESDE popupAddReg Y PODER OBTENER EL CONTROL DE LOS ELEMENTOS
        add_value_gly = popupAddReg.findViewById(R.id.add_value_gly);
        add_notes_gly = popupAddReg.findViewById(R.id.add_notes_gly);

        add_date_gly = popupAddReg.findViewById(R.id.add_date_gly);
        // add_date_gly = popupAddReg.findViewById(R.id.edit_date_gly_pickdater); // ESTE ES PARA EL PICKDATER
        // ESTA FORMA AGREGA A ESTA MISMA CLASE COMO LISTENER Y LUEGO EN UN SWITCH SE ELIJE EL EVENTO SEGUN SU ID..
        add_date_gly.setOnClickListener(this);

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


    // todo pendiente revisar y mejorar el RECICLER VIEW crear clase en UTILS, que sea reusable por todos los otros crudso!!!!!
    // todo pendiente revisar y mejorar el RECICLER VIEW crear clase en UTILS, que sea reusable por todos los otros crudso!!!!!
    // todo pendiente revisar y mejorar el RECICLER VIEW crear clase en UTILS, que sea reusable por todos los otros crudso!!!!!
    // todo pendiente revisar y mejorar el RECICLER VIEW crear clase en UTILS, que sea reusable por todos los otros crudso!!!!!

    // RECICLER VIEW Clase que se encargara de CREAR todos los elementos de lista
    private class AdapterRegGly extends RecyclerView.Adapter<AdapterRegGly.AdapterRegGlyHolder> {
        @NonNull
        @Override
        public AdapterRegGlyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new AdapterRegGlyHolder(getLayoutInflater().inflate(R.layout.recicler_view_reg_gly,parent,false));
        }
        @Override
        public void onBindViewHolder(@NonNull AdapterRegGlyHolder holder, int position) {
            try {
                holder.printItem(position);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        @Override
        public int getItemCount() {
            return reg_gly_dates.size(); // debe retornar la cantidad de registros..
        }
        // Clase que se encargara de IMPRIMIR todos los elementos
        private class AdapterRegGlyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            TextView reg_date;
            TextView reg_value;
            TextView reg_note;
            Button btn_edit_reg_gly, btn_delete_reg_gly;
            public AdapterRegGlyHolder(@NonNull View itemView) {
                super(itemView);
                reg_date = itemView.findViewById(R.id.recycler_reg_gly_date);
                reg_value = itemView.findViewById(R.id.recycler_reg_gly_value);
                reg_note = itemView.findViewById(R.id.recycler_reg_gly_note);
                btn_edit_reg_gly = itemView.findViewById(R.id.recycler_btn_edit_reg_gly);
                btn_delete_reg_gly = itemView.findViewById(R.id.recycler_btn_delete_reg_gly);
                itemView.setOnClickListener(this);
            }
            public void printItem(int position) throws ParseException {
                reg_date.setText(reg_gly_dates.get(position));
                reg_value.setText(String.valueOf(reg_gly_values.get(position)));
                reg_note.setText(reg_gly_notes.get(position));

                btn_edit_reg_gly.setOnClickListener(view -> {
                    btnEditOnClick(reg_gly_ids.get(getLayoutPosition()));
                });
                btn_delete_reg_gly.setOnClickListener(view -> {
                    btnDeleteOnClick(reg_gly_ids.get(getLayoutPosition()));
                });
            }
            @Override
            public void onClick(View v) {
                Toast.makeText(RegGlyActivity.this, String.valueOf(reg_gly_ids.get(getLayoutPosition())),Toast.LENGTH_SHORT).show();
            }
            public void btnEditOnClick(int id){
                openPopupBtnEdit(id);
            }
            public void btnDeleteOnClick(int id){
                openPopupBtnDel(id);
            }
        }
    }

    // todo pendiente revisar y mejorar el RECICLER VIEW crear clase en UTILS, que sea reusable por todos los otros crudso!!!!!
    // todo pendiente revisar y mejorar el RECICLER VIEW crear clase en UTILS, que sea reusable por todos los otros crudso!!!!!
    // todo pendiente revisar y mejorar el RECICLER VIEW crear clase en UTILS, que sea reusable por todos los otros crudso!!!!!
    // todo pendiente revisar y mejorar el RECICLER VIEW crear clase en UTILS, que sea reusable por todos los otros crudso!!!!!


    public void cleanAllRegs(){
        // limpio arrays para recibir los datos nuevos..
        reg_gly_ids.clear();
        reg_gly_dates.clear();
        reg_gly_values.clear();
        reg_gly_notes.clear();
    }
    private void updateAllDataRegs(ArrayList<Integer> reg_ids, ArrayList<String> reg_dates, ArrayList<Double> reg_values, ArrayList<String> reg_notes){
        cleanAllRegs();
        // SETEO DATA A LOS NUEVOS VALORES RECIBIDOS
        reg_gly_ids = reg_ids;
        reg_gly_dates = reg_dates;
        reg_gly_values = reg_values;
        reg_gly_notes = reg_notes;
        updateReciclerView();// actualizo recicler view
    }
    private void updateReciclerView(){
        adapterRegGly = new AdapterRegGly();
        rv1.setAdapter(adapterRegGly);
    }
    private String getStringDate(Long dateInMilli){
        Instant instant = Instant.ofEpochMilli(dateInMilli); // Crear un objeto Instant a partir de los milisegundos en Long
        ZonedDateTime fechaHoraZona = instant.atZone(ZoneId.systemDefault()); // Convertir el Instant a una fecha y hora en la zona horaria predeterminada del sistema
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EE dd '-' HH:mm'hs'", Locale.ENGLISH); //Formatear la fecha y hora según el estilo // todo VERIFICAR IDIOMA DEL GRAFICO!!!
        return fechaHoraZona.format(dateFormatter);// Obtener la fecha y hora formateada como una cadena para el grafico
    }
    public void renderRegs(List recordsObjects){
        ArrayList<Integer> reg_ids = new ArrayList<Integer>();
        ArrayList<String> reg_dates = new ArrayList<String>();
        ArrayList<Double> reg_values = new ArrayList<Double>();
        ArrayList<String> reg_notes = new ArrayList<String>();

        Gson gson = new Gson();
        for (int i = 0; i <recordsObjects.size(); i++){
            try {
                Record record = gson.fromJson(recordsObjects.get(i).toString(), Record.class);// obtengo el string y lo serializo a la clase record
                reg_ids.add(record.getId());
                reg_dates.add( getStringDate(record.getFecha())); // obtiene fecha en milisegundos en un dato Long y lo convierte a un string del estilo Sun 02:44hs
                reg_values.add(record.getValor());
                reg_notes.add(record.getComentario());
            } catch (Exception e){
                // todo pendiente de manejar
            }
        }
        if (! reg_ids.isEmpty()){
            updateChartRegGly(reg_ids,reg_dates, reg_values, reg_notes);
        } else{
            Toast.makeText(this, "Aun no hay registros guardados..", Toast.LENGTH_LONG).show();
        }
    }
    private void updateChartRegGly(ArrayList<Integer> reg_ids, ArrayList<String> reg_dates, ArrayList<Double> reg_values, ArrayList<String> reg_notes){
        updateAllDataRegs(reg_ids,reg_dates, reg_values, reg_notes); // actualiza arrays de datos
        LineDataSet lineDataSet = new LineDataSet(createLineChartDataSet(), "Glucemia");
        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(lineDataSet);

        //Edito los datos de fecha al formato corto
        ArrayList<String> formatedDates = new ArrayList<String>();
        reg_gly_dates.forEach(date ->{ formatedDates.add(date);

        });
        //Seteo el formateador para leyendas del eje X
        LineData lineData = new LineData(iLineDataSets);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new DateAxisValueFormatter(formatedDates));

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
    public void refreshRegs(){
        HttpLoggingInterceptor recordInterceptor = new HttpLoggingInterceptor();
        recordInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(recordInterceptor);

        // todo el interceptor para agregar el token al header
        httpClient.addInterceptor(chain -> {
            Request originalRequest = chain.request();
            Request.Builder builder = originalRequest.newBuilder();
            if (token!= null &&!token.isEmpty()) {
                builder.header("Authorization", "Bearer " + token);
            }
            return chain.proceed(builder.build());
        }); //todo el interceptor para agregar el token al header

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl) // +"/auth/register/"
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        RecordsRequest recordRequest = retrofit.create(RecordsRequest.class);

        Call<RecordsPaginatedReadDtoArray> call = recordRequest.getAllGlycemiaRecordsByIdUser(loguedUser.getId());
        call.enqueue(new Callback<RecordsPaginatedReadDtoArray>() {
            @Override
            public void onResponse(Call<RecordsPaginatedReadDtoArray> call, Response<RecordsPaginatedReadDtoArray> response) {
                if(response.isSuccessful() && response.body() != null){
                    renderRegs(response.body().getRegistros());
                } else if (response.code()==400){
                    // todo pendiente de manejar
                    System.out.println(" DTOReadAllRegisters response.code()==400  *********");
                    System.out.println(response.body());
                    System.out.println(" DTOReadAllRegisters response.code()==400  *********");
                }
            }
            @Override
            public void onFailure(Call<RecordsPaginatedReadDtoArray> call, Throwable t) {
                // todo pendiente de manejar
                System.out.println("******************** DTOReadAllRegisters Throwable t*************************************************");
                System.out.println( t);
                System.out.println("******************** DTOReadAllRegisters Throwable t******************************************************");
            }
        });
    }
    public void addNewReg(){
        // todo PENDIENTEEEEE
        // todo PENDIENTEEEEE
        // todo PENDIENTEEEEE
        // todo PENDIENTEEEEE
        String newDate;
        if(add_date_gly.getText().toString().equals("") ){
            System.out.println("CREO NUEVA FECHA NOW PORQUE VINO VACIA");
            newDate = new Date().toString();
        } else {
            newDate = add_date_gly.getText().toString();
        }
        DTORegister newReg = new DTORegister(newDate ,Double.valueOf(add_value_gly.getText().toString()) , add_notes_gly.getText().toString());

        Boolean insertResult = true; // todo PENDIENTEEEEE admin.addReg(this.TABLE_NAME, newReg);

        if(insertResult){
            add_value_gly.setText("");// limpio pantalla
            add_notes_gly.setText("");
            add_date_gly.setText("");
            this.refreshRegs();
            this.updateChartRegGly(reg_gly_ids,reg_gly_dates, reg_gly_values, reg_gly_notes); // sobreescribe chart
            adapterRegGly.notifyDataSetChanged(); // refresca pantalla del recycler
            rv1.smoothScrollToPosition(reg_gly_ids.size()-1); // mueve la vista al ultimo elemento agregado
            Toast.makeText(this,"Se agrego registro de glucemia", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,"Error agregando registro!", Toast.LENGTH_SHORT).show();
        }
    }
    public void deleteReg(int id){
        // todo PENDIENTEEEEE
        // todo PENDIENTEEEEE
        // todo PENDIENTEEEEE
        // todo PENDIENTEEEEE
        Boolean deleteResult = true;// todo PENDIENTEEEEE admin.deleteReg(this.TABLE_NAME , id);
        if(deleteResult){
            this.refreshRegs(); // actualiza array de reg
            this.updateChartRegGly(reg_gly_ids,reg_gly_dates, reg_gly_values, reg_gly_notes); // sobreescribe chart
            adapterRegGly.notifyDataSetChanged(); // refresca pantalla del recycler
            Toast.makeText(this, "Registro eliminado correctamente", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Error eliminando registro", Toast.LENGTH_LONG).show();
        }
    }
    public void setTextEditRegPopup(int id){
        // todo PENDIENTEEEEE
        // todo PENDIENTEEEEE
        // todo PENDIENTEEEEE
        // todo PENDIENTEEEEE
        DTORegister regById = new DTORegister();// todo PENDIENTEEEEE admin.getRegById(this.TABLE_NAME, id);
        if (regById != null){
            edit_date_gly.setText(regById.getDate());
            edit_value_gly.setText(String.valueOf(regById.getValue()));
            edit_notes_gly.setText(regById.getNotes());
        }else{
            Toast.makeText(this,"Error editando registro", Toast.LENGTH_SHORT).show();
        }
    }
    public void updateEditedReg(int id){
        DTORegister dtoUpdated = new DTORegister(edit_date_gly.getText().toString(), Double.valueOf(edit_value_gly.getText().toString()) ,edit_notes_gly.getText().toString());
        boolean updateResult = true;// todo PENDIENTEEEEE admin.updateRegById(this.TABLE_NAME,id,dtoUpdated);
        if (updateResult){
            edit_value_gly.setText("");// limpio pantalla
            edit_notes_gly.setText("");
            edit_date_gly.setText("");
            this.refreshRegs(); // actualiza array de reg
            this.updateChartRegGly(reg_gly_ids,reg_gly_dates, reg_gly_values, reg_gly_notes); // actualiza chart
            adapterRegGly.notifyDataSetChanged(); // refresca pantalla del recycler
            Toast.makeText(this, "Registro actualizado!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Error al editar registro", Toast.LENGTH_LONG).show();
        }
    }
    public void toHome(View v){
        Intent home = new Intent(this, Home.class);
        startActivity(home);
    }
    @Override
    public void onClick(View view) { }


}