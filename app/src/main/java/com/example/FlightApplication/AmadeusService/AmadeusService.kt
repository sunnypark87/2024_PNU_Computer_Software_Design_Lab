package com.example.FlightApplication.AmadeusService

import com.example.FlightApplication.DataClass.FlightOfferResponse
import com.example.FlightApplication.DataClass.FlightSearchRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AmadeusService {

    @POST("v2/shopping/flight-offers")
    fun getFlightOffers(
        @Header("Authorization")
        authorization : String,

        @Body
        params : FlightSearchRequest
    ) : Call<FlightOfferResponse>
}