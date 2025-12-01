package com.example.FlightApplication.AmadeusService

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AmadeusFactory {
    val BASE_URL:String = "https://test.api.amadeus.com/";

    fun create_token(): AmadeusTokenService {
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        return retrofit.create(AmadeusTokenService::class.java);
    }

    fun create(): AmadeusService {
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        return retrofit.create(AmadeusService::class.java);
    }

}