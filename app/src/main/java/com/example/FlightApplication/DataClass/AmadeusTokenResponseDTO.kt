package com.example.FlightApplication.DataClass

import com.google.gson.annotations.SerializedName

data class AmadeusTokenResponseDTO(
    @SerializedName("type") val type : String,
    @SerializedName("username") val username : String,
    @SerializedName("application_name") val application_name : String,
    @SerializedName("client_id") val client_id : String,
    @SerializedName("token_type") val token_type : String,
    @SerializedName("access_token") val access_token : String,
    @SerializedName("expires_in") val expires_in : Int,
    @SerializedName("state") val state : String,
    @SerializedName("scope") val scope : String
)
