package com.example.ict_services_mobile.api.model

import com.google.gson.annotations.SerializedName

data class UserDataModel (
    @SerializedName("techID")
    val techID: Int,

    @SerializedName("adminID")
    val adminID: Int,

    @SerializedName("email")
    val email: String,

    @SerializedName("firstName")
    val firstName: String,

    @SerializedName("lastName")
    val lastName: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("role")
    val role: String,

    @SerializedName("remarks")
    val remarks: List<RemarkModel>,
)

data class RemarkModel(
    @SerializedName("rating")
    val rating: Double,
    @SerializedName("ticketID")
    val ticketID: Int,
    @SerializedName("comment")
    val comment: String,
    @SerializedName("ratedBy")
    val ratedBy: IssuedByModel,
)
