package com.example.one_drop_cruds.request;

import com.example.one_drop_cruds.entities.DTOReadAllRegisters;
import com.example.one_drop_cruds.entities.user.RecordsPaginatedReadDtoArray;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RecordsRequest {
    /*
    @GET("/registros/glucemia/usuario/{userId}")
    Call<DTOReadAllRegisters> getAllGlycemiaRecordsByIdUser (@Path("userId") int userId);
     */
    @GET("/registros/glucemia/usuario/{userId}")
    Call<RecordsPaginatedReadDtoArray> getAllGlycemiaRecordsByIdUser (@Path("userId") int userId);
}
