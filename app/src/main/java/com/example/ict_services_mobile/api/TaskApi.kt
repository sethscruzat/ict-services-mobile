package com.example.ict_services_mobile.api

import com.example.ict_services_mobile.api.model.TicketModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface TaskApi {
    @PUT("/ticket/add")
    fun assignTask(@Body reqBody: TicketModel): Call<TicketModel>

    @PUT("/ticket/complete")
    fun getAllCompletedTasks(): Call<List<TicketModel>>

    @GET("/ticket/complete/{ticketID}")
    fun markTaskAsDone(@Path("ticketID")ticketID: Int): Call<TicketModel>
}