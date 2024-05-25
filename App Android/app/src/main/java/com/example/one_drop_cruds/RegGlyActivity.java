package com.example.one_drop_cruds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.one_drop_cruds.entities.DTORegister;
import com.example.one_drop_cruds.entities.dtos.RegistroReadDto;
import com.example.one_drop_cruds.entities.requests.AddNewRecordDto;
import com.example.one_drop_cruds.entities.user.LoguedUserDetails;
import com.example.one_drop_cruds.entities.user.Record;
import com.example.one_drop_cruds.entities.user.RecordsPaginatedReadDtoArray;
import com.example.one_drop_cruds.request.RecordsRequest;
import com.example.one_drop_cruds.utils.RetrofitHelper;
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

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegGlyActivity extends AppCompatActivity implements View.OnClickListener{
    final Calendar c = Calendar.getInstance(); // Obtener la instancia actual del calendario
    UserSessionManager userSessionManager;
    SharedPrefManager sharedPrefManager;
    String token;
    LoguedUserDetails loguedUser;
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

    // TODO DATE PICKER
    private Button recordDateBtn;
    private TextView recordDateEditText;
    // TODO DATE PICKER

    // paginado de resultados
    private Button btn_first_page, btn_prev_page, btn_next_page, btn_last_page;
    private Spinner spinnerPageSize;
    private Integer pageSize;
    private Integer pageNumber;
    private Integer actualPage;
    private Integer lastPage;

    RecordsRequest recordRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_gly);

        // user sessions
        userSessionManager = new UserSessionManager(getApplicationContext());
        sharedPrefManager = new SharedPrefManager(getApplicationContext(), "oneDrop_shared_preferences");
        token = sharedPrefManager.getUserToken();
        loguedUser = userSessionManager.getLoguedUserDetails();  // SI NO ESTA LOGUEADO, SE REDIRIGE A LOGIN

        // request, Crea helper con el jwt, inicializa retrofit y crea RecordsRequest, para hacer solicitudes
        recordRequest = new RetrofitHelper(token).getRetrofitHelperWithToken().create(RecordsRequest.class);

        // paginado resultados
        spinnerPageSize = findViewById(R.id.spinnerPageSize);
        pageSize = 5;
        pageNumber = 0;
        setSpinnerPageSize(pageSize.toString());
        refreshRegsRequest(pageSize, pageNumber);   // actualiza array de regs, recicler y grafico


        // btns
        float_btn_add_reg_gly = findViewById(R.id.float_btn_add_reg_gly);
        btn_first_page = findViewById(R.id.btn_first_page);
        btn_prev_page = findViewById(R.id.btn_prev_page);
        btn_next_page = findViewById(R.id.btn_next_page);
        btn_last_page = findViewById(R.id.btn_last_page);


        lineChart = findViewById(R.id.glyLineChart);

        // RECICLER VIEW
        rv1 = findViewById(R.id.recyclerView_reg_gly);
        LinearLayoutManager linearLayoutManager_reg_gly = new LinearLayoutManager(this);
        rv1.setLayoutManager(linearLayoutManager_reg_gly);
        adapterRegGly = new AdapterRegGly();
        rv1.setAdapter(adapterRegGly);

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



    public void addNewReg(){
        String newDate;
        if(add_date_gly.getText().toString().equals("") ){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime now = LocalDateTime.now();
            newDate = now.format(formatter);
        } else {
            newDate = add_date_gly.getText().toString();
        }
        addNewRegRequest(newDate, Double.valueOf(add_value_gly.getText().toString()), add_notes_gly.getText().toString());
    }
    private void renderRegs(List recordsObjects){
        ArrayList<Integer> reg_ids = new ArrayList<Integer>();
        ArrayList<String> reg_dates = new ArrayList<String>();
        ArrayList<Double> reg_values = new ArrayList<Double>();
        ArrayList<String> reg_notes = new ArrayList<String>();

        Gson gson = new Gson();
        for (int i = 0; i <recordsObjects.size(); i++){
            try {
                Record record = gson.fromJson( escapeBlankSpacesInComentario(recordsObjects.get(i).toString()), Record.class);
                reg_ids.add(record.getId());
                reg_dates.add( getStringDate(record.getFecha())); // obtiene fecha en milisegundos en un dato Long y lo convierte a un string del estilo Sun 02:44hs
                reg_values.add(record.getValor());
                reg_notes.add(record.getComentario().replace("**", " "));
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

    // requests
    private void refreshRegsRequest(Integer pageSize, Integer pageNumber){
        Call<RecordsPaginatedReadDtoArray> call = recordRequest.getAllGlycemiaRecordsByIdUser(loguedUser.getId(), pageSize, pageNumber);
        call.enqueue(new Callback<RecordsPaginatedReadDtoArray>() {
            @Override
            public void onResponse(Call<RecordsPaginatedReadDtoArray> call, Response<RecordsPaginatedReadDtoArray> response) {
                if(response.isSuccessful() && response.body() != null){
                    renderRegs(response.body().getRegistros());
                    actualPage = response.body().getCurrent_page();
                    lastPage = response.body().getPages()-1;
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
    private void addNewRegRequest(String fecha, Double valor, String comentario){
        AddNewRecordDto newRecordDto = new AddNewRecordDto(fecha, valor, comentario);
        Call<RegistroReadDto> call = recordRequest.addNewGlycemiaRecord(loguedUser.getId(), newRecordDto);
        call.enqueue(new Callback<RegistroReadDto>() {
            @Override
            public void onResponse(Call<RegistroReadDto> call, Response<RegistroReadDto> response) {
                if(response.isSuccessful() && response.body() != null){
                    cleanEditTextFields();
                    refreshRegsRequest(pageSize, pageNumber);   // actualiza array de regs, recicler y grafico
                    rv1.smoothScrollToPosition(reg_gly_ids.size()-1); // mueve la vista al ultimo elemento agregado
                    makeToast("Se agrego registro de glucemia");
                } else if (response.code()==400){
                    // todo pendiente de manejar
                    System.out.println(" DTOReadAllRegisters response.code()==400  *********");
                    makeToast("Error agregando response.code()==400??");
                    System.out.println(response.body());
                    System.out.println(" DTOReadAllRegisters response.code()==400  *********");
                }
            }
            @Override
            public void onFailure(Call<RegistroReadDto> call, Throwable t) {
                // todo pendiente de manejar
                System.out.println("********* DTOReadAllRegisters Throwable t******");
                makeToast("Error onFailure Throwable=> "+t);
                System.out.println( t.getLocalizedMessage());
                System.out.println( t.getCause());
                System.out.println( t.getMessage());
                System.out.println("********* DTOReadAllRegisters Throwable t******");
            }
        });
    }
    private void deleteRegRequest(int id){
        Call<RegistroReadDto> call = recordRequest.deleteGlycemiaRecord(id);
        call.enqueue(new Callback<RegistroReadDto>() {
            @Override
            public void onResponse(Call<RegistroReadDto> call, Response<RegistroReadDto> response) {
                if(response.isSuccessful()){
                    refreshRegsRequest(pageSize, pageNumber);   // actualiza array de regs, recicler y grafico
                    makeToast("Registro eliminado correctamente");
                }
            }
            @Override
            public void onFailure(Call<RegistroReadDto> call, Throwable t) {
                makeToast("Error eliminando registro");
            }
        });
    }

    // todo PENDIENTEEEEE
    // todo PENDIENTEEEEE
    public void updateRegRequest(int id){
        DTORegister dtoUpdated = new DTORegister(edit_date_gly.getText().toString(), Double.valueOf(edit_value_gly.getText().toString()) ,edit_notes_gly.getText().toString());
        boolean updateResult = true;// todo PENDIENTEEEEE admin.updateRegById(this.TABLE_NAME,id,dtoUpdated);
        if (updateResult){
            edit_value_gly.setText("");// limpio pantalla
            edit_notes_gly.setText("");
            edit_date_gly.setText("");
            refreshRegsRequest(pageSize, pageNumber);   // actualiza array de regs, recicler y grafico
            Toast.makeText(this, "Registro actualizado!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Error al editar registro", Toast.LENGTH_LONG).show();
        }
    }


    // todo PENDIENTEEEEE
    // todo PENDIENTEEEEE
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




    // charts
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


    // popups
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
                updateRegRequest(id_reg); // toma los campos modificados y actualiza la bd
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
                deleteRegRequest(id_reg);
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
        add_date_gly.setOnClickListener(this); // ESTA FORMA AGREGA A ESTA MISMA CLASE COMO LISTENER Y LUEGO EN UN SWITCH SE ELIJE EL EVENTO SEGUN SU ID..
        recordDateBtn = popupAddReg.findViewById(R.id.recordDateBtn); // DATE PICKER
        recordDateEditText = popupAddReg.findViewById(R.id.add_date_gly); // DATE PICKER
        recordDateBtn.setOnClickListener(new View.OnClickListener() { // DATE PICKER
            @Override
            public void onClick(View v) {
                showDatePickerDialogWithTime();
            }
        });

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

    // spinner
    public void setSpinnerPageSize(String elementoPreseleccionado){
        List<String> opciones = new ArrayList<>();
        opciones.add("5");
        opciones.add("10");
        opciones.add("15");
        opciones.add("20");
        opciones.add("30");
        opciones.add("100");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opciones.toArray(new String[0])); // SE MUESTRA EN this ACTIVITY, dentro de un SIMPLE_SPINNER
        spinnerPageSize.setAdapter(adapter); // mostrar spinner con el adapatador creado
        spinnerPageSize.setSelection(adapter.getPosition(elementoPreseleccionado));

        spinnerPageSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                refreshRegsRequest(Integer.parseInt(spinnerPageSize.getSelectedItem().toString()), pageNumber); // Obtiene data, la setea y grafica
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // todo si no hubiese nada seleccionado...
            }
        });
    }

    // date time picker
    private void showDatePickerDialogWithTime() {
        // Primero muestro fecha, y despues que seleciona fecha, muestra horario
        DatePickerDialog datePickerDialog = new DatePickerDialog(RegGlyActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Configurar el TimePickerDialog
                        TimePickerDialog timePickerDialog = new TimePickerDialog(RegGlyActivity.this,
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

                                        recordDateEditText.setText(dateTime); // Mostrar la fecha y hora
                                    }
                                }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false);
                        timePickerDialog.show();// Mostrar el hora después de seleccionar la fecha
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show(); // Mostrar el fecha inicialmente
    }

    // metodos accesorios
    private void makeToast(String msg){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
    }
    private void cleanEditTextFields(){
        add_value_gly.setText("");
        add_notes_gly.setText("");
        add_date_gly.setText("");
    }
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
        adapterRegGly.notifyDataSetChanged();
    }
    private String getStringDate(Long dateInMilli){
        Instant instant = Instant.ofEpochMilli(dateInMilli); // Crear un objeto Instant a partir de los milisegundos en Long
        ZonedDateTime fechaHoraZona = instant.atZone(ZoneId.systemDefault()); // Convertir el Instant a una fecha y hora en la zona horaria predeterminada del sistema
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EE dd '-' HH:mm'hs'", Locale.ENGLISH); //Formatear la fecha y hora según el estilo // todo VERIFICAR IDIOMA DEL GRAFICO!!!
        return fechaHoraZona.format(dateFormatter);// Obtener la fecha y hora formateada como una cadena para el grafico

    }
    private String escapeBlankSpacesInComentario(String objectString){
        // por problemas para escapar espacios en blanco al serializar los comentarios, es que primero obtengo el string en guardado en comentario, reemplazo los espacios en blacos por **, luego serializo a clase Record y por ultimo elimino los **
        String onlyComentario =  objectString.split("comentario=")[1].replace(" ", "**");
        return objectString.split("comentario")[0]+"comentario="+ onlyComentario; //junto el string antes de serializar
    }

    // navegacion
    public void goToFirstPage(View v){
        refreshRegsRequest(pageSize, 0);   // actualiza array de regs, recicler y grafico
    }
    public void goToLastPage(View v){
        refreshRegsRequest(pageSize, lastPage);   // actualiza array de regs, recicler y grafico
    }
    public void goToPrevPage(View v){
        refreshRegsRequest(pageSize, actualPage-1);   // actualiza array de regs, recicler y grafico
    }
    public void goToNextPage(View v){
        refreshRegsRequest(pageSize, actualPage+1);   // actualiza array de regs, recicler y grafico
    }
    public void toHome(View v){
        Intent home = new Intent(this, Home.class);
        startActivity(home);
    }
    @Override
    public void onClick(View view) { }


}