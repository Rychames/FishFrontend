package com.example.fishcontroller.network;

import com.example.fishcontroller.model.LoginRequest;
import com.example.fishcontroller.model.LoginResponse;
import com.example.fishcontroller.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/register")
    Call<Void> register(@Body User user);

    @POST("/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
}
