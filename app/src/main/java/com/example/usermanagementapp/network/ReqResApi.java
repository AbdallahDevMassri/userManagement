package com.example.usermanagementapp.network;

import com.example.usermanagementapp.model.User;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ReqResApi {

    @GET("users")
    Call<List<User>> getUsers();

    // Create a new user
    @POST("users")
    Call<User> createUser(@Body User user);

    // Update an existing user
    @PUT("users/{id}")
    Call<User> updateUser(@Path("id") String id, @Body User user);

    // Delete a user
    @DELETE("users/{id}")
    Call<Void> deleteUser(@Path("id") String id);
}
