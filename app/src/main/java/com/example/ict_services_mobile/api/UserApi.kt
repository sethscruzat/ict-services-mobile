package com.example.ict_services_mobile.api

import com.example.ict_services_mobile.api.model.TicketModel
import com.example.ict_services_mobile.api.model.UserDataModel

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApi {
    @POST("/login")
    fun authenticateUser(@Body map: HashMap<String, String>): Call<UserDataModel>

    //gets technician information
    @GET("/user/tech/{techID}")
    fun getTechnicianData(@Path("techID") techID: Int): Call<UserDataModel>

    // gets the task details for 1 specific task that a technician has
    @GET("/ticket/find/{techID}/{ticketID}")
    fun getTechTaskList(@Path("techID")techID: Int,@Path("ticketID")ticketID: Int): Call<TicketModel>

    //gets list of tasks for 1 technician
   @GET("/ticket/list/{techID}")
   fun getTechTaskItems(@Path("techID")techID: Int): Call<List<TicketModel>>
}