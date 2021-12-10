package com.example.cinema.Services;

import com.example.cinema.Request.LoginRequest;
import com.example.cinema.Response.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {
    @POST("/auth/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);
}
