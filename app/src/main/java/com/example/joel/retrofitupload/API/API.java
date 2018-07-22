package com.example.joel.retrofitupload.API;

import com.example.joel.retrofitupload.response.UploadResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public  interface API{

    @GET("/")
    Call<UploadResponse> test();
    @Multipart
    @POST("/upload")
    Call<UploadResponse> uploadImage(@Part MultipartBody.Part file,@Part("file") RequestBody name);
}
