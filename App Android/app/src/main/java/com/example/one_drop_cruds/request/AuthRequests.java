package com.example.one_drop_cruds.request;

import com.example.one_drop_cruds.entities.user.AuthResponse;
import com.example.one_drop_cruds.entities.user.FichaMedicaUsuario;
import com.example.one_drop_cruds.entities.user.LoginRequest;
import com.example.one_drop_cruds.entities.user.LoguedUserDetails;
import com.example.one_drop_cruds.entities.user.RegisterRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AuthRequests {

    //@FormUrlEncoded
    @Headers({
            "Content-Type: application/json",
            "Accept: application/json"
    })
    @POST("/auth/login")
    Call<AuthResponse> loginRequest (@Body LoginRequest loginRequest);

    @Headers({
            "Content-Type: application/json",
            "Accept: application/json"
    })
    @POST("/auth/register")
    Call<AuthResponse> registerRequest (@Body RegisterRequest registerRequest);

    @Headers({
            "Content-Type: application/json",
            "Accept: application/json"
    })
    @GET("/auth/userDetails")
    Call<LoguedUserDetails> getUserDetailsRequest ();
    @GET("/fichaMedica/user/{userId}")
    Call<FichaMedicaUsuario> getFichaMedicaUsuario (@Path("userId") int userId);

}
