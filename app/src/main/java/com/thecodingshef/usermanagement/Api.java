package com.thecodingshef.usermanagement;

import com.thecodingshef.usermanagement.ModelResponse.FetchUserResponse;
import com.thecodingshef.usermanagement.ModelResponse.LoginResponse;
import com.thecodingshef.usermanagement.ModelResponse.RegisterResponse;
import com.thecodingshef.usermanagement.ModelResponse.UpdatePassResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("register.php")
    Call<RegisterResponse> register(
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password

    );

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> login(
            @Field("email") String email,
            @Field("password") String password

    );


    @GET("fetchusers.php")
    Call<FetchUserResponse> fetchAllUsers();


    @FormUrlEncoded
    @POST("updateuser.php")
    Call<LoginResponse> updateUserAccount(
            @Field("id") int userid,
            @Field("username") String userName,
            @Field("email") String email


    );

    @FormUrlEncoded
    @POST("updatepassword.php")
    Call<UpdatePassResponse> updateUserPass(
            @Field("email") String email,
            @Field("current") String currentPassword,
            @Field("new") String newPassword


    );


}
