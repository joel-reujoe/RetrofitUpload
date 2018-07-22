package com.example.joel.retrofitupload.API;

import retrofit2.Retrofit;


public class RestClient {
    public static final String baseUrl = "http://172.30.74.49:8000/";
    public static Retrofit client = new Retrofit.Builder().baseUrl(baseUrl).build();
}
