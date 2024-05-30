package com.example.one_drop_cruds;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.one_drop_cruds.utils.ToastHelper;

import java.text.ParseException;
import java.util.ArrayList;

public class services extends AppCompatActivity {

    // DATA
    ArrayList<Integer> servicio_ids = new ArrayList<>();
    ArrayList<String> sevicio_nombres = new ArrayList<>();
    ArrayList<String> sevicio_descripciones = new ArrayList<>();
    ArrayList<Double> sevicio_precios = new ArrayList<>();
    ArrayList<String> sevicio_comentarios = new ArrayList<>();

    // RECICLER VIEW
    RecyclerView reciclerViewServicios;
    RecyclerView reciclerViewServiciosByUsuario;
    AdapterServicio adapterRVServicios;
    AdapterServicio adapterRVServiciosByUsuario;
    ToastHelper toastHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); setContentView(R.layout.activity_services);

        // RECICLER VIEW
        reciclerViewServicios = findViewById(R.id.recyclerView_services);
        LinearLayoutManager linearLayoutManager_Servicios = new LinearLayoutManager(this);
        reciclerViewServicios.setLayoutManager(linearLayoutManager_Servicios);
        adapterRVServicios = new AdapterServicio();
        reciclerViewServicios.setAdapter(adapterRVServicios);

        reciclerViewServiciosByUsuario = findViewById(R.id.recyclerView_services_by_user);
        LinearLayoutManager linearLayoutManager_ServiciosByUsuario = new LinearLayoutManager(this);
        reciclerViewServiciosByUsuario.setLayoutManager(linearLayoutManager_ServiciosByUsuario);
        adapterRVServiciosByUsuario = new AdapterServicio();
        reciclerViewServiciosByUsuario.setAdapter(adapterRVServiciosByUsuario);

        toastHelper = new ToastHelper(services.this);
    }
    private class AdapterServicio extends RecyclerView.Adapter<AdapterServicio.AdapterServicioHolder> {
        @NonNull
        @Override
        public AdapterServicioHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new AdapterServicioHolder(getLayoutInflater().inflate(R.layout.recicler_view_service,parent,false));
        }
        @Override
        public void onBindViewHolder(@NonNull AdapterServicioHolder holder, int position) {
            try {
                holder.printItem(position);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        @Override
        public int getItemCount() {
            return servicio_ids.size(); // debe retornar la cantidad de registros..
        }
        // Clase que se encargara de IMPRIMIR todos los elementos
        private class AdapterServicioHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            TextView recycler_service_nombre;
            TextView recycler_servicio_precio;
            TextView recycler_servicio_descripcion;
            TextView recycler_servicio_comentarios;

            //todo Button btn_comprar? btn_ver_mas_detalles?;

            public AdapterServicioHolder(@NonNull View itemView) {
                super(itemView);
                recycler_service_nombre = itemView.findViewById(R.id.recycler_service_nombre);
                recycler_servicio_precio = itemView.findViewById(R.id.recycler_servicio_precio);
                recycler_servicio_descripcion = itemView.findViewById(R.id.recycler_servicio_descripcion);
                recycler_servicio_comentarios = itemView.findViewById(R.id.recycler_servicio_comentarios);
                //todo btn_comprar, btn_ver_mas_detalles = itemView.findViewById(R.id.);
                itemView.setOnClickListener(this);
            }
            public void printItem(int position) throws ParseException {
                recycler_service_nombre.setText(sevicio_nombres.get(position));
                recycler_servicio_descripcion.setText(sevicio_descripciones.get(position));
                recycler_servicio_precio.setText(String.valueOf(sevicio_precios.get(position)));
                recycler_servicio_comentarios.setText(sevicio_comentarios.get(position));
                /*
                todo btn_comprar, btn_ver_mas_detalles = itemView.findViewById(R.id.);
                btn_edit_reg_gly.setOnClickListener(view -> {
                    btnEditOnClick(servicio_ids.get(getLayoutPosition()));
                });
                btn_delete_reg_gly.setOnClickListener(view -> {
                    btnDeleteOnClick(servicio_ids.get(getLayoutPosition()));
                });
                 */
            }
            @Override
            public void onClick(View v) {
                toastHelper.showShort(String.valueOf(servicio_ids.get(getLayoutPosition())));
            }
            /*
            todo btn_comprar, btn_ver_mas_detalles = itemView.findViewById(R.id.);
            public void btnEditOnClick(int id){
                openPopupBtnEdit(id);
            }
            public void btnDeleteOnClick(int id){
                openPopupBtnDel(id);
            }
             */
        }
    }



    // todo *****************************************************************************
    // todo *****************************************************************************
    // TODO BUSCAR TODOS LOS SERVICIOS DISPONIBLES
    //todo sevicio_ids
    // sevicio_nombres
    // sevicio_descripciones
    // sevicio_precios
    // sevicio_comentarios



    // todo *****************************************************************************
    // todo *****************************************************************************






    // TODO BUSCAR TODOS LOS SERVICIOS COMPRADOS POR USUARIO
}