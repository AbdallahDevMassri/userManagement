package com.example.usermanagementapp.network;

import com.example.usermanagementapp.model.User;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ReqResApi {

    @GET("users")
    Call<List<User>> getUsers();

    // Additional API methods if needed
}
