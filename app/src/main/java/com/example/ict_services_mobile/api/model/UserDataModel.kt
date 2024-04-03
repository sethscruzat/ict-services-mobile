package com.example.ict_services_mobile.api.model

import com.google.gson.annotations.SerializedName

data class UserDataModel (
    @SerializedName("id")
    val id: Int,

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

    @SerializedName("tasks")
    val tasks: List<TechTaskModel>,

    @SerializedName("remarks")
    val remarks: List<Any>
)
