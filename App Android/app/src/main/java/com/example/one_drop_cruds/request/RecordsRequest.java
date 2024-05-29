package com.example.one_drop_cruds.request;

import com.example.one_drop_cruds.entities.dtos.RecordReadDto;
import com.example.one_drop_cruds.entities.dtos.AddNewRecordDto;
import com.example.one_drop_cruds.entities.user.RecordsPaginatedReadDtoArray;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RecordsRequest {
    /*
    @GET("/registros/glucemia/usuario/{userId}")
    Call<DTOReadAllRegisters> getAllGlycemiaRecordsByIdUser (@Path("userId") int userId);
     */
    // registros glucemia
    @GET("/registros/glucemia/usuario/{userId}")
    Call<RecordsPaginatedReadDtoArray> getAllGlycemiaRecordsByIdUser (
            @Path("userId") int userId,
            @Query("size") int pageSize,
            @Query("page") int pageNumber);
    @POST("/registros/glucemia/usuario/{fichaMedicaId}")
    Call<RecordReadDto> addNewGlycemiaRecord (@Path("fichaMedicaId") int fichaMedicaId, @Body AddNewRecordDto addNewRecordDto);
    @PUT("/registros/glucemia/{id}")
    Call<RecordReadDto> editGlycemiaRecord (@Path("id") int idRegistro, @Body AddNewRecordDto updateRecordDto);

    @DELETE("/registros/glucemia/{id}")
    Call<RecordReadDto> deleteGlycemiaRecord (@Path("id") int idRegistro);


    // registros peso
    @GET("/registros/peso/usuario/{userId}")
    Call<RecordsPaginatedReadDtoArray> getAllWeightRecordsByIdUser (
            @Path("userId") int userId,
            @Query("size") int pageSize,
            @Query("page") int pageNumber);
    @POST("/registros/peso/usuario/{fichaMedicaId}")
    Call<RecordReadDto> addNewWeightRecord (@Path("fichaMedicaId") int fichaMedicaId, @Body AddNewRecordDto addNewRecordDto);
    @PUT("/registros/peso/{id}")
    Call<RecordReadDto> editWeightRecord (@Path("id") int idRegistro, @Body AddNewRecordDto updateRecordDto);

    @DELETE("/registros/peso/{id}")
    Call<RecordReadDto> deleteWeightRecord (@Path("id") int idRegistro);



    // registros tension
    @GET("/registros/tensionArterial/usuario/{userId}")
    Call<RecordsPaginatedReadDtoArray> getAllPresureRecordsByIdUser (
            @Path("userId") int userId,
            @Query("size") int pageSize,
            @Query("page") int pageNumber);


    @POST("/registros/tensionArterial/usuario/{fichaMedicaId}")
    Call<RecordReadDto> addNewPresureRecord (@Path("fichaMedicaId") int fichaMedicaId, @Body AddNewRecordDto addNewRecordDto);
    @PUT("/registros/tensionArterial/{id}")
    Call<RecordReadDto> editPresureRecord (@Path("id") int idRegistro, @Body AddNewRecordDto updateRecordDto);

    @DELETE("/registros/tensionArterial/{id}")
    Call<RecordReadDto> deletePresureRecord (@Path("id") int idRegistro);


}
