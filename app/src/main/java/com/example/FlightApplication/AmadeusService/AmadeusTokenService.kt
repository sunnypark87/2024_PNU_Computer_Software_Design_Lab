package com.example.FlightApplication.AmadeusService

import com.example.FlightApplication.DataClass.AmadeusTokenResponseDTO
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface AmadeusTokenService {
    @Headers("Content-Type: application/x-www-form-urlencoded")

    @FormUrlEncoded
    @POST("v1/security/oauth2/token")
    fun getToken(@Field("grant_type") grant_type: String,
                 @Field("client_id") client_id: String,
                 @Field("client_secret") client_secret: String): Call<AmadeusTokenResponseDTO>
}