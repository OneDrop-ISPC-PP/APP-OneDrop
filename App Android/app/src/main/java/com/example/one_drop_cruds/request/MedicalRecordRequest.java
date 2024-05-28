package com.example.one_drop_cruds.request;

import com.example.one_drop_cruds.entities.dtos.medicalRecords.AddNewMedicalRecordDto;
import com.example.one_drop_cruds.entities.dtos.medicalRecords.MedicalRecordReadDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MedicalRecordRequest {
    @GET("/fichaMedica/user/{userId}")
    Call<MedicalRecordReadDto> getByUserId (@Path("userId") int userId);
    @GET("/fichaMedica/{id}")
    Call<MedicalRecordReadDto> getById (@Path("id") int id);
    @POST("/fichaMedica/")
    Call<MedicalRecordReadDto> addNewMedicalRecord ( @Body AddNewMedicalRecordDto addNewMedicalRecordDto);
    @PUT("/fichaMedica/{id}")
    Call<MedicalRecordReadDto> editMedicalRecord (@Path("id") int id, @Body  AddNewMedicalRecordDto addNewMedicalRecordDto);
    @DELETE("/fichaMedica/{id}")
    Call<MedicalRecordReadDto> deleteMedicalRecord (@Path("id") int id);
}
