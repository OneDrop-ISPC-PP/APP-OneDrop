package com.example.one_drop_cruds.request;

import com.example.one_drop_cruds.entities.user.RecordsPaginatedReadDtoArray;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Streaming;

public interface FileRequest {
    @Streaming
    @GET("/reportes/{userId}")
    Call</*byte[]*/ResponseBody> getFullResume (@Path("userId") int userId);

}
