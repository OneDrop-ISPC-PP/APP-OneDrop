package com.example.one_drop_cruds.request;

import com.example.one_drop_cruds.entities.user.AuthResponse;
import com.example.one_drop_cruds.entities.user.LoginRequest;
import com.example.one_drop_cruds.entities.user.LoguedUserDetails;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

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
    @GET("/auth/userDetails")
    Call<LoguedUserDetails> getUserDetailsRequest ();
    /* ,
            "Authorization" :

     */
}
