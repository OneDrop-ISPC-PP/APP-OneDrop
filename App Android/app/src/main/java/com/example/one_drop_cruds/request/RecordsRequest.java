package com.example.one_drop_cruds.request;

import com.example.one_drop_cruds.entities.dtos.RegistroReadDto;
import com.example.one_drop_cruds.entities.requests.AddNewRecordDto;
import com.example.one_drop_cruds.entities.user.RecordsPaginatedReadDtoArray;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RecordsRequest {
    /*
    @GET("/registros/glucemia/usuario/{userId}")
    Call<DTOReadAllRegisters> getAllGlycemiaRecordsByIdUser (@Path("userId") int userId);
     */
    @GET("/registros/glucemia/usuario/{userId}")
    Call<RecordsPaginatedReadDtoArray> getAllGlycemiaRecordsByIdUser (
            @Path("userId") int userId,
            @Query("size") int pageSize,
            @Query("page") int pageNumber);


    @POST("/registros/glucemia/usuario/{userId}")
    Call<RegistroReadDto> addNewGlycemiaRecord (@Path("userId") int userId, @Body AddNewRecordDto addNewRecordDto);

    @DELETE("/registros/glucemia/{id}")
    Call<RegistroReadDto> deleteGlycemiaRecord (@Path("id") int idRegistro);
}
