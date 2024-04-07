package com.example.ict_services_mobile.api

import com.example.ict_services_mobile.api.model.RemarkModel
import com.example.ict_services_mobile.api.model.TicketModel
import com.example.ict_services_mobile.api.model.UserDataModel

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApi {
    @POST("/login")
    fun authenticateUser(@Body map: HashMap<String, String>): Call<UserDataModel>

    //get technician List
    @GET("/technician/all")
    fun getTechnicianList(): Call<List<UserDataModel>>

    //gets technician information
    @GET("/user/tech/{techID}")
    fun getTechnicianData(@Path("techID") techID: Int): Call<UserDataModel>

    //gets admin data
    @GET("/user/admin/{adminID}")
    fun getAdminData(@Path("adminID") adminID: Int): Call<UserDataModel>

    // admin rates technician's performance on a task
    @PUT("/admin/rate/{techID}")
    fun rateTechnicianPerformance(@Path("techID")techID: Int,@Body reqBody: RemarkModel): Call<RemarkModel>
}