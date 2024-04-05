package com.example.ict_services_mobile.api.model

import com.google.gson.annotations.SerializedName

data class TicketModel(
    @SerializedName("ticketID")
    val ticketID: Int,

    @SerializedName("equipmentID")
    val equipmentID: String,

    @SerializedName("location")
    val location: String,

    @SerializedName("remarks")
    val remarks: String,

    @SerializedName("issuedBy")
    val issuedBy: IssuedByModel,

    @SerializedName("assignedTo")
    val assignedTo: AssignedToModel,
)

data class IssuedByModel(
    @SerializedName("adminID")
    val adminID: Int,

    @SerializedName("adminName")
    val adminName: String,
)

data class AssignedToModel(
    @SerializedName("techID")
    val techID: Int,

    @SerializedName("techName")
    val techName: String,
)