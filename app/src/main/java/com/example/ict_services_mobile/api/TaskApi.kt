package com.example.ict_services_mobile.api

import com.example.ict_services_mobile.api.model.TicketModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface TicketApi {
    @PUT("/ticket/add")
    fun assignTask(@Body reqBody: TicketModel): Call<TicketModel>

    @GET("/ticket/complete")
    fun getAllCompletedTasks(): Call<List<TicketModel>>

    @GET("/technician/complete/{techID}")
    fun getCompletedTickets(@Path("techID")techID: Int): Call<List<TicketModel>>

    // gets the ticket details for 1 specific task that a technician has
    @GET("/technician/find/{ticketID}")
    fun getTicketInfo(@Path("ticketID")ticketID: Int): Call<TicketModel>

    //gets list of tickets for 1 technician
    @GET("/technician/list/{techID}")
    fun getTicketList(@Path("techID")techID: Int): Call<List<TicketModel>>

    @GET("/admin/list/{adminID}")
    fun getCompletedAssignedTasks(@Path("adminID")adminID: Int): Call<List<TicketModel>>

    @GET("/admin/find/{ticketID}")
    fun getCompletedTicketInfo(@Path("ticketID")ticketID: Int): Call<TicketModel>

    @PUT("/ticket/mark/{ticketID}")
    fun markTaskAsDone(@Path("ticketID")ticketID: Int): Call<TicketModel>
}