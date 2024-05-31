package com.example.one_drop_cruds.request;

import com.example.one_drop_cruds.entities.dtos.records.AddNewRecordDto;
import com.example.one_drop_cruds.entities.dtos.records.RecordReadDto;
import com.example.one_drop_cruds.entities.dtos.services.ServicesPaginatedReadDtoArray;
import com.example.one_drop_cruds.entities.user.RecordsPaginatedReadDtoArray;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CarritoRequest {

    // registros peso
    @GET("/servicios/")
    Call<ServicesPaginatedReadDtoArray> getAllServices ();
    /*
    // todo debo crear clase que represente a :

    "id": 1,
    "paciente": {
        "id": 1,
        "username": "davidcst",
        "dni": "35924420",
        "telefono": "2644647572",
        "nombre": "david",
        "apellido": "costa",
        "email": "davidcst2991@gmail.com",
        "nacimiento": [
            1992,
            12,
            2
        ],
        "sexo": "masculino",
        "role": "USUARIO"
    },
    "servicios": [],
    "historialCompras": [
        {
            "id": 3,
            "importe": 11000.0,
            "fecha": 1715876693576,
            "servicios": [
                {
                    "id": 2,
                    "nombre": "Segundo servicio",
                    "descripcion": "Holter",
                    "precio": 3500.0,
                    "comentarios": "UN POCOo mas caro"
                },
                {
                    "id": 3,
                    "nombre": "UN tercer servicio",
                    "descripcion": "ergometria",
                    "precio": 7500.0,
                    "comentarios": "solo los cardio"
                }
            ],
            "metodoDePago": "EFECTIVO"
        }
    ]
} y recibir esto desde el endpoint
    @GET("/carrito/{idUser}")
    Call<ServicesPaginatedReadDtoArray> getAllServicesByIdUser (@Path("idUser") int idUser);

     */


    /*
    @POST("/registros/peso/usuario/{fichaMedicaId}")
    Call<RecordReadDto> addNewWeightRecord (@Path("fichaMedicaId") int fichaMedicaId, @Body AddNewRecordDto addNewRecordDto);
    @PUT("/registros/peso/{id}")
    Call<RecordReadDto> editWeightRecord (@Path("id") int idRegistro, @Body AddNewRecordDto updateRecordDto);

    @DELETE("/registros/peso/{id}")
    Call<RecordReadDto> deleteWeightRecord (@Path("id") int idRegistro);

     */

}
