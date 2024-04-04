package com.example.ict_services_mobile.api

import com.example.ict_services_mobile.api.model.TechTaskModel
import com.example.ict_services_mobile.api.model.UserDataModel
import com.example.ict_services_mobile.screens.technician.tasks.techTaskViewModel

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApi {
    @GET("/users")
    fun getUserList(): Call<List<UserDataModel>>

    @GET("/user/{email}")
    fun getTechnicianData(@Path("email") email: String): Call<UserDataModel>

    @POST("/login")
    fun authenticateUser(@Body map: HashMap<String, String>): Call<UserDataModel>

    @GET("/user/{email}/{index}")
    fun getTechTaskList(@Path("email")email: String,@Path("index")index: Int): Call<TechTaskModel>

   @GET("/user/{email}")
   fun getTechTaskItems(@Path("email")email: String): Call<UserDataModel>
}