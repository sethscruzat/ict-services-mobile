package com.example.ict_services_mobile.api

import com.example.ict_services_mobile.api.model.TicketModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface TaskApi {
    @PUT("/ticket/add")
    fun assignTask(): Call<TicketModel>

    @PUT("/ticket/complete")
    fun markTaskAsDone(): Call<TicketModel>

    @GET("/task/complete/{ticketID}")
    fun getAllCompletedTasks(@Path("ticketID")ticketID: Int): Call<TicketModel>

    @PUT("/task/{techID}/{ticketID}")
    fun adminMoveToCompleted(@Path("techID")techID: Int,@Path("ticketID")ticketID: Int): Call<TicketModel>
}