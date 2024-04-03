package com.example.ict_services_mobile.api.model

import com.google.gson.annotations.SerializedName

data class TechTaskModel(
    @SerializedName("equipmentID")
    val equipmentID: String,

    @SerializedName("location")
    val location: String,

    @SerializedName("remarks")
    val remarks: String,

    @SerializedName("issuedBy")
    val issuedBy: String,

    @SerializedName("equipmentName")
    val equipmentName: String,
)