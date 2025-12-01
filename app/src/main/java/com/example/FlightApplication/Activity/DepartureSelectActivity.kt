package com.example.FlightApplication.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.FlightApplication.AmadeusService.AmadeusFactory
import com.example.FlightApplication.DataClass.AmadeusTokenResponseDTO
import com.example.FlightApplication.DataClass.FlightOfferResponse
import com.example.FlightApplication.DataClass.FlightSearchRequest
import com.example.FlightApplication.R
import com.example.FlightApplication.RecyclerAdapter
import com.example.FlightApplication.databinding.DepartureSelectBinding

import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DepartureSelectActivity : AppCompatActivity() {
    private lateinit var binding: DepartureSelectBinding
    var departureFlight: MutableList<String> = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DepartureSelectBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val getIntent: Intent = getIntent()
        val city: String = getIntent.getStringExtra("city").toString()
        var startDate = getIntent.getStringExtra("startDate").toString()
        var endDate = getIntent.getStringExtra("endDate").toString()
        Toast.makeText(this,"출발하는 항공권 정보를 받아오는 중입니다.", Toast.LENGTH_LONG).show()
        when(city){
            "NRT" -> binding.departureDate.text = "한국 -> 일본 항공편"
            "JFK" -> binding.departureDate.text = "한국 -> 미국 항공편"
            "CDG" -> binding.departureDate.text = "한국 -> 프랑스 항공편"
            "MNL" -> binding.departureDate.text = "한국 -> 필리핀 항공편"
            "DPS" -> binding.departureDate.text = "한국 -> 인도네시아 항공편"
        }


        getFlights( {
            Log.d("RE", "print")
            binding.flightDepartureList.layoutManager = LinearLayoutManager(this)
            binding.flightDepartureList.adapter = RecyclerAdapter(departureFlight) {data ->
                val intent = Intent(this, EntrySelectActivity::class.java)
                intent.putExtra("departureData", data)
                intent.putExtra("city", city)
                intent.putExtra("endDate", endDate)
                startActivity(intent)
            }
            binding.flightDepartureList.addItemDecoration(DividerItemDecoration(
                this, LinearLayoutManager.VERTICAL
            ))
            Log.d("RE", departureFlight.toString())
        } ,city, startDate, endDate)

        binding.backToInfoBtn1.setOnClickListener {
            finish()
        }
    }

    fun getFlights(callback: () -> Unit, city: String, startDate: String, endDate: String) {
        val amadeus = AmadeusFactory()
        val service_token = amadeus.create_token()
        var token: String = ""
        // amadeus token 받아오는 것
        service_token.getToken(
            "client_credentials",
            getString(R.string.client_id),
            getString(R.string.client_secret)
        ).enqueue(object : Callback<AmadeusTokenResponseDTO> {
            override fun onResponse(
                call: Call<AmadeusTokenResponseDTO>,
                response: Response<AmadeusTokenResponseDTO>
            ) {
                if (response.isSuccessful) {
                    var result: AmadeusTokenResponseDTO? = response.body();
                    token = result?.access_token.toString();
                    getFlightOffers(callback, token, city, startDate, endDate)
                    Log.d("API", "onResponse 성공 : " + result?.toString());
                } else {
                    Log.d("API", "onResponse 실패");
                    Log.d("API", response.message().toString())
                }
            }

            override fun onFailure(call: Call<AmadeusTokenResponseDTO>, t: Throwable) {
                Log.d("API", "onFailure 에러 : " + t.message.toString());
            }
        })
    }

    fun getFlightOffers(callback: () -> Unit, token: String, city: String, startDate: String, endDate: String) {
        // api 호출을 위한 request body 생성
        val flightSearchRequestJson = """
           {
              "currencyCode": "USD",
              "originDestinations": [
                {
                  "id": "1",
                  "originLocationCode": "INC",
                  "destinationLocationCode": "$city",
                  "departureDateTimeRange": {
                    "date": "$startDate",
                    "time": "00:00:00"
                  }
                }
              ],
              "travelers": [
                {
                  "id": "1",
                  "travelerType": "ADULT"
                }
              ],
              "sources": [
                "GDS"
              ],
              "searchCriteria": {
                "maxFlightOffers": 5,
                "flightFilters": {
                  "cabinRestrictions": [
                    {
                      "cabin": "ECONOMY",
                      "coverage": "MOST_SEGMENTS",
                      "originDestinationIds": [
                        "1"
                      ]
                    }
                  ]
                }
              }
            } 
        """
        val gson = Gson()
        val flightSearchRequest: FlightSearchRequest = gson.fromJson(flightSearchRequestJson, FlightSearchRequest::class.java)

        val amadeus = AmadeusFactory()
        val service = amadeus.create()

        // amadeus flight
        service.getFlightOffers("Bearer " + token, flightSearchRequest)
            .enqueue(object : Callback<FlightOfferResponse> {
                override fun onResponse(
                    call: Call<FlightOfferResponse>,
                    response: Response<FlightOfferResponse>
                ) {
                    if (response.isSuccessful) {
                        var result: FlightOfferResponse? = response.body();
                        Log.d("API", "onResponse 성공 : ");
                        for (i: Int in 0..4) {
                            val itineraries = result?.data!![i].itineraries[0]
                            var oneString = """
                                ${itineraries.duration}, ${itineraries.segments[0].departure.at},
                                ${itineraries.segments.last().arrival.at}, ${result?.data!![i].price.grandTotal},
                                ${itineraries.segments[0].carrierCode + itineraries.segments[0].number}, ${result?.data!![i].numberOfBookableSeats.toString()},
                                ICN, ${city} 
                            """.trimIndent()
                            departureFlight.add(oneString)
                            Log.d("API", "소요시간 : " + result?.data!![i].itineraries[0].duration)
                            Log.d("API", "출발시간 : " + result?.data!![i].itineraries[0].segments[0].departure.at)
                            Log.d("API", "도착시간 : " + result?.data!![i].itineraries[0].segments.last().arrival.at)
                            Log.d("API", "비용 : " + result?.data!![i].price.grandTotal)
                            Log.d("API", "항공편명 : " + result?.data!![i].itineraries[0].segments[0].carrierCode + result?.data!![i].itineraries[0].segments[0].number)
                            Log.d("API", "잔여 좌석 : " + result?.data!![i].numberOfBookableSeats.toString())
                        }
                        callback()
                    } else {
                        Log.d("API", "onResponse 실패");
                        Log.d("API", response.message().toString())
                        Toast.makeText(this@DepartureSelectActivity, "선택하신 날짜의 항공편 정보가 없습니다. 다른 날짜를 선택하세요", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<FlightOfferResponse>, t: Throwable) {
                    Log.d("API", "onFailure 에러 : " + t.message.toString());
                    Toast.makeText(this@DepartureSelectActivity, "오류가 발생해 항공편 정보를 받아오지 못했습니다. 뒤로 가기 후 다시 시도 해주세요", Toast.LENGTH_SHORT).show()
                }
            })
    }
}