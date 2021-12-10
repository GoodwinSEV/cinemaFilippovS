package com.example.cinema.Services;


import com.example.cinema.Request.RegisterRequest;
import com.example.cinema.Response.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegisterService {
    @POST("/auth/register")
    Call<RegisterResponse> registerUser(@Body RegisterRequest registerRequest);


}
