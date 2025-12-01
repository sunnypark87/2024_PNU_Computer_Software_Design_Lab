package com.example.FlightApplication.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.FlightApplication.RecyclerAdapter
import com.example.FlightApplication.databinding.FlightInfoBinding

class FlightActivity: AppCompatActivity() {
    private lateinit var binding: FlightInfoBinding
    var flights: MutableList<String> = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FlightInfoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val getIntent: Intent = getIntent()
        val departureData = getIntent.getStringExtra("departureData").toString()
        val entryData = getIntent.getStringExtra("entryData").toString()
        flights.add(departureData)
        flights.add(entryData)

        binding.flightList.layoutManager = LinearLayoutManager(this)
        binding.flightList.adapter = RecyclerAdapter(flights) {data ->
            Log.d("Finish", "finish")
        }
        binding.flightList.addItemDecoration(
            DividerItemDecoration(
                this, LinearLayoutManager.VERTICAL
            )
        )

        binding.backDepartureBtn.setOnClickListener {
            finish()
        }
    }
}