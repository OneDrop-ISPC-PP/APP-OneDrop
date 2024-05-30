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

import com.example.one_drop_cruds.entities.user.LoguedUserDetails;
import com.example.one_drop_cruds.utils.SharedPrefManager;
import com.example.one_drop_cruds.utils.ToastHelper;
import com.example.one_drop_cruds.utils.UserSessionManager;

import java.text.ParseException;
import java.util.ArrayList;

public class services extends AppCompatActivity {
    UserSessionManager userSessionManager;
    SharedPrefManager sharedPrefManager;
    String token;
    LoguedUserDetails loguedUser;

    // DATA SERVICIOS DISPONIBLES
    ArrayList<Integer> servicio_ids = new ArrayList<>();
    ArrayList<String> sevicio_nombres = new ArrayList<>();
    ArrayList<String> sevicio_descripciones = new ArrayList<>();
    ArrayList<Double> sevicio_precios = new ArrayList<>();
    ArrayList<String> sevicio_comentarios = new ArrayList<>();

    // DATA SERVICIOS COMPRADOS
    ArrayList<Integer> servicio_ids_by_user = new ArrayList<>();
    ArrayList<String> sevicio_nombres_by_user = new ArrayList<>();
    ArrayList<String> sevicio_descripciones_by_user = new ArrayList<>();
    ArrayList<Double> sevicio_precios_by_user = new ArrayList<>();
    ArrayList<String> sevicio_comentarios_by_user = new ArrayList<>();

    // RECICLER VIEW
    RecyclerView reciclerViewServicios;
    RecyclerView reciclerViewServiciosByUsuario;
    AdapterServicio adapterRVServicios;
    AdapterServicio_by_user adapterRVServiciosByUsuario;
    ToastHelper toastHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); setContentView(R.layout.activity_services);


        // user sessions
        userSessionManager = new UserSessionManager(getApplicationContext());
        sharedPrefManager = new SharedPrefManager(getApplicationContext(), "oneDrop_shared_preferences");
        loguedUser = userSessionManager.getLoguedUserDetails();  // SI NO ESTA LOGUEADO, SE REDIRIGE A LOGIN
        token = sharedPrefManager.getUserToken();

        // RECICLER VIEW
        reciclerViewServicios = findViewById(R.id.recyclerView_services);
        LinearLayoutManager linearLayoutManager_Servicios = new LinearLayoutManager(this);
        reciclerViewServicios.setLayoutManager(linearLayoutManager_Servicios);
        adapterRVServicios = new AdapterServicio();
        reciclerViewServicios.setAdapter(adapterRVServicios);

        reciclerViewServiciosByUsuario = findViewById(R.id.recyclerView_services_by_user);
        LinearLayoutManager linearLayoutManager_ServiciosByUsuario = new LinearLayoutManager(this);
        reciclerViewServiciosByUsuario.setLayoutManager(linearLayoutManager_ServiciosByUsuario);
        adapterRVServiciosByUsuario = new AdapterServicio_by_user();
        reciclerViewServiciosByUsuario.setAdapter(adapterRVServiciosByUsuario);

        toastHelper = new ToastHelper(services.this);
        getServiciosData();
        getServiciosDataByUser(loguedUser.getId());
    }

    // este es para todos los servicios
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

    // este deberia ser para todos los servicios comprados...
    // todo _by_user
    private class AdapterServicio_by_user extends RecyclerView.Adapter<AdapterServicio_by_user.AdapterServicioHolder> {
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
                recycler_service_nombre.setText(sevicio_nombres_by_user.get(position));
                recycler_servicio_descripcion.setText(sevicio_descripciones_by_user.get(position));
                recycler_servicio_precio.setText(String.valueOf(sevicio_precios_by_user.get(position)));
                recycler_servicio_comentarios.setText(sevicio_comentarios_by_user.get(position));
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
                toastHelper.showShort(String.valueOf(servicio_ids_by_user.get(getLayoutPosition())));
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
    // todo
    // sevicio_ids Integer
    // sevicio_nombres String
    // sevicio_descripciones String
    // sevicio_precios Double
    // sevicio_comentarios String
    public void getServiciosData(){

        servicio_ids.add(1);
        servicio_ids.add(2);
        servicio_ids.add(3);

        sevicio_nombres.add("primer");
        sevicio_nombres.add("segundo");
        sevicio_nombres.add("tercero");

        sevicio_descripciones.add("primer descripcion");
        sevicio_descripciones.add("segundo descripcion");
        sevicio_descripciones.add("tercero descripcion");

        sevicio_precios.add(1550.0);
        sevicio_precios.add(700.0);
        sevicio_precios.add(1500.0);

        sevicio_comentarios.add("primer comentario");
        sevicio_comentarios.add("segundo comentario");
        sevicio_comentarios.add("tercero comentario");

    }


    // todo *****************************************************************************
    // todo *****************************************************************************

        public void getServiciosDataByUser (Integer userId){

            // TODO BUSCAR TODOS LOS SERVICIOS COMPRADOS POR USUARIO
            // TODO BUSCAR TODOS LOS SERVICIOS COMPRADOS POR USUARIO
            // TODO BUSCAR TODOS LOS SERVICIOS COMPRADOS POR USUARIO

        servicio_ids_by_user.add(1);
        servicio_ids_by_user.add(2);
        servicio_ids_by_user.add(3);

        sevicio_nombres_by_user.add("primer _by_user");
        sevicio_nombres_by_user.add("segundo _by_user");
        sevicio_nombres_by_user.add("tercero _by_user");

        sevicio_descripciones_by_user.add("primer descripcion _by_user");
        sevicio_descripciones_by_user.add("segundo descripcion _by_user");
        sevicio_descripciones_by_user.add("tercero descripcion _by_user");

        sevicio_precios_by_user.add(1550.0);
        sevicio_precios_by_user.add(700.0);
        sevicio_precios_by_user.add(1500.0);

        sevicio_comentarios_by_user.add("primer comentario _by_user");
        sevicio_comentarios_by_user.add("segundo comentario _by_user");
        sevicio_comentarios_by_user.add("tercero comentario _by_user");

    }

}