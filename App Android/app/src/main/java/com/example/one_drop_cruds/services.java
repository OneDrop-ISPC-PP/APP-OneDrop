package com.example.one_drop_cruds;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.one_drop_cruds.entities.Service;
import com.example.one_drop_cruds.entities.dtos.carts.CartReadDto;
import com.example.one_drop_cruds.entities.dtos.services.ServicesPaginatedReadDtoArray;
import com.example.one_drop_cruds.entities.user.LoguedUserDetails;
import com.example.one_drop_cruds.request.CarritoRequest;
import com.example.one_drop_cruds.utils.RetrofitHelper;
import com.example.one_drop_cruds.utils.SharedPrefManager;
import com.example.one_drop_cruds.utils.ToastHelper;
import com.example.one_drop_cruds.utils.UserSessionManager;
import com.google.gson.Gson;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class services extends AppCompatActivity {
    UserSessionManager userSessionManager;
    SharedPrefManager sharedPrefManager;
    String token;
    LoguedUserDetails loguedUser;

    // DATA SERVICIOS DISPONIBLES
    ArrayList<Integer> servicio_ids = new ArrayList<>();
    ArrayList<String> sevicio_nombres = new ArrayList<>();
    ArrayList<String> sevicio_descripciones = new ArrayList<>();
    ArrayList<Integer> sevicio_precios = new ArrayList<>();
    ArrayList<String> sevicio_comentarios = new ArrayList<>();

    // DATA SERVICIOS COMPRADOS
    ArrayList<Integer> servicio_ids_by_user = new ArrayList<>();
    ArrayList<String> sevicio_nombres_by_user = new ArrayList<>();
    ArrayList<String> sevicio_descripciones_by_user = new ArrayList<>();
    ArrayList<Integer> sevicio_precios_by_user = new ArrayList<>();
    ArrayList<String> sevicio_comentarios_by_user = new ArrayList<>();

    // RECICLER VIEW
    RecyclerView reciclerViewServicios;
    RecyclerView reciclerViewServiciosByUsuario;
    AdapterServicio adapterRVServicios;
    AdapterServicio_by_user adapterRVServiciosByUsuario;
    ToastHelper toastHelper;

    CarritoRequest carritoRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); setContentView(R.layout.activity_services);


        // user sessions
        userSessionManager = new UserSessionManager(getApplicationContext());
        sharedPrefManager = new SharedPrefManager(getApplicationContext(), "oneDrop_shared_preferences");
        loguedUser = userSessionManager.getLoguedUserDetails();  // SI NO ESTA LOGUEADO, SE REDIRIGE A LOGIN
        token = sharedPrefManager.getUserToken();

        carritoRequest = new RetrofitHelper(token).getRetrofitHelperWithToken().create(CarritoRequest.class);

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
        getServiciosDataRequest();
        getServiciosDataRequestByUser(loguedUser.getId());
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
                toastHelper.showShort("Para adquirir servicios dirigete a la pagina web por favor!");
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
            return servicio_ids_by_user.size(); // debe retornar la cantidad de registros..
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


    public void getServiciosDataRequest(){
        Call<ServicesPaginatedReadDtoArray> call= carritoRequest.getAllServices();
        call.enqueue(new Callback<ServicesPaginatedReadDtoArray>() {
            @Override
            public void onResponse(Call<ServicesPaginatedReadDtoArray> call, Response<ServicesPaginatedReadDtoArray> response) {
                if(response.isSuccessful() && response.body() != null){
                    setDataServices(response.body().getServices());
                } else if (response.code()==400){
                    // todo pendiente de manejar
                    System.out.println(" DTOReadAllRegisters response.code()==400  *********");
                    System.out.println(response.body());
                    System.out.println(" DTOReadAllRegisters response.code()==400  *********");
                }
            }
            @Override
            public void onFailure(Call<ServicesPaginatedReadDtoArray> call, Throwable t) {
                // todo pendiente de manejar
                System.out.println("******************** DTOReadAllRegisters Throwable t*************************************************");
                System.out.println( t);
                System.out.println("******************** DTOReadAllRegisters Throwable t******************************************************");
            }
        });

    }
    public void getServiciosDataRequestByUser(Integer userId){
        Call<CartReadDto> call= carritoRequest.getAllServicesInCartByUser(userId);
        call.enqueue(new Callback<CartReadDto>() {
            @Override
            public void onResponse(Call<CartReadDto> call, Response<CartReadDto> response) {
                if(response.isSuccessful() && response.body() != null ){
                    System.out.println(" getServiciosDataRequestByUser  ** >>" + response.body().getServicios());
                    setDataServicesByUser(response.body().getServicios());
                } else if (response.code()==400){
                    // todo pendiente de manejar
                    System.out.println(" DTOReadAllRegisters response.code()==400  *********");
                    System.out.println(response.body());
                    System.out.println(" DTOReadAllRegisters response.code()==400  *********");
                }
            }
            @Override
            public void onFailure(Call<CartReadDto> call, Throwable t) {
                // todo pendiente de manejar
                System.out.println("******************** DTOReadAllRegisters Throwable t*************************************************");
                System.out.println( t);
                System.out.println("******************** DTOReadAllRegisters Throwable t******************************************************");
            }
        });

    }






    public void setDataServices(List services){
        Gson gson = new Gson();
        if(services.size()>0){
            for (int i = 0; i <services.size(); i++){
                try {
                    Service service = gson.fromJson( gson.toJson(services.get(i)) , Service.class);  // todo gson.toJson(), debo usarlo porque previamente ya habia deserializado el objeto serviciosPaginados, que incluye la lista de servicios, pero la lista de servicios quedo como un objeto plano.. services.get(i) entoces no es un json valido, por lo que luego lo debo transformar en una cadena Json valida para usar gson.fromJson() y que funcione correctamente

                    servicio_ids.add(service.getId());
                    sevicio_nombres.add(service.getNombre());
                    sevicio_descripciones.add(service.getDescripcion());
                    sevicio_precios.add(service.getPrecio());
                    sevicio_comentarios.add(service.getComentarios());
                } catch (Exception e){
                    System.out.println("ERROR >> "+e.getMessage());
                    // todo pendiente de manejar
                }
            }
            if (servicio_ids.isEmpty()){
                toastHelper.showLong("Aun no hay servicios");
            } else{
                adapterRVServicios.notifyDataSetChanged();
            }
        } else{
            toastHelper.showLong("Aun no hay servicios");
        }
    }
    public void setDataServicesByUser(List services){
        Gson gson = new Gson();
        if(services.size()>0){
            for (int i = 0; i <services.size(); i++){
                try {
                    Service service = gson.fromJson(gson.toJson(services.get(i)) , Service.class);  // todo gson.toJson(), debo usarlo porque previamente ya habia deserializado el objeto serviciosPaginados, que incluye la lista de servicios, pero la lista de servicios quedo como un objeto plano.. services.get(i) entoces no es un json valido, por lo que luego lo debo transformar en una cadena Json valida para usar gson.fromJson() y que funcione correctamente

                    servicio_ids_by_user.add(service.getId());
                    sevicio_nombres_by_user.add(service.getNombre());
                    sevicio_descripciones_by_user.add(service.getDescripcion());
                    sevicio_precios_by_user.add(service.getPrecio());
                    sevicio_comentarios_by_user.add(service.getComentarios());
                } catch (Exception e){
                    // todo pendiente de manejar
                }
            }
            if (servicio_ids_by_user.isEmpty()){
                toastHelper.showLong("Aun no hay servicios comprados");
            } else{
                adapterRVServiciosByUsuario.notifyDataSetChanged();
            }
        } else {
            toastHelper.showLong("Aun no hay servicios comprados");
        }
    }
    // navegacion
    public void toHome(View v){
        Intent home = new Intent(this, Home.class);
        startActivity(home);
    }
}