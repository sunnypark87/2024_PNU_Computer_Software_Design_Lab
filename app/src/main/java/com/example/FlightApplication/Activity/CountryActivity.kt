package com.example.FlightApplication.Activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.FlightApplication.databinding.SelectCountryBinding

class CountryActivity : AppCompatActivity() {
    private lateinit var binding : SelectCountryBinding
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SelectCountryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        var city = ""
        binding.japan.setOnClickListener {
            if(city == ""){
                city = "NRT"
                binding.japan.setBackgroundColor(Color.parseColor("#6666FF"))
                binding.japan.setTextColor(Color.parseColor("#FFFFFF"))
            }else if(city == "CDG") {
                city = "NRT"
                binding.japan.setBackgroundColor(Color.parseColor("#6666FF"))
                binding.japan.setTextColor(Color.parseColor("#FFFFFF"))
                binding.france.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binding.france.setTextColor(Color.parseColor("#000000"))
            }else if(city == "JFK") {
                city = "NRT"
                binding.japan.setBackgroundColor(Color.parseColor("#6666FF"))
                binding.japan.setTextColor(Color.parseColor("#FFFFFF"))
                binding.usa.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binding.usa.setTextColor(Color.parseColor("#000000"))
            }else if(city == "MNL") {
                city = "NRT"
                binding.japan.setBackgroundColor(Color.parseColor("#6666FF"))
                binding.japan.setTextColor(Color.parseColor("#FFFFFF"))
                binding.philippines.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binding.philippines.setTextColor(Color.parseColor("#000000"))
            }else if(city == "DPS") {
                city = "NRT"
                binding.japan.setBackgroundColor(Color.parseColor("#6666FF"))
                binding.japan.setTextColor(Color.parseColor("#FFFFFF"))
                binding.indonesia.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binding.indonesia.setTextColor(Color.parseColor("#000000"))
            }
        }
        binding.france.setOnClickListener {
            if(city == ""){
                city = "CDG"
                binding.france.setBackgroundColor(Color.parseColor("#6666FF"))
                binding.france.setTextColor(Color.parseColor("#FFFFFF"))
            }else if(city == "NRT") {
                city = "CDG"
                binding.france.setBackgroundColor(Color.parseColor("#6666FF"))
                binding.france.setTextColor(Color.parseColor("#FFFFFF"))
                binding.japan.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binding.japan.setTextColor(Color.parseColor("#000000"))
            }else if(city == "JFK") {
                city = "CDG"
                binding.france.setBackgroundColor(Color.parseColor("#6666FF"))
                binding.france.setTextColor(Color.parseColor("#FFFFFF"))
                binding.usa.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binding.usa.setTextColor(Color.parseColor("#000000"))
            }else if(city == "MNL") {
                city = "CDG"
                binding.france.setBackgroundColor(Color.parseColor("#6666FF"))
                binding.france.setTextColor(Color.parseColor("#FFFFFF"))
                binding.philippines.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binding.philippines.setTextColor(Color.parseColor("#000000"))
            }else if(city == "DPS") {
                city = "CDG"
                binding.france.setBackgroundColor(Color.parseColor("#6666FF"))
                binding.france.setTextColor(Color.parseColor("#FFFFFF"))
                binding.indonesia.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binding.indonesia.setTextColor(Color.parseColor("#000000"))
            }
        }
        binding.usa.setOnClickListener {
            if(city == ""){
                city = "JFK"
                binding.usa.setBackgroundColor(Color.parseColor("#6666FF"))
                binding.usa.setTextColor(Color.parseColor("#FFFFFF"))
            }else if(city == "NRT") {
                city = "JFK"
                binding.usa.setBackgroundColor(Color.parseColor("#6666FF"))
                binding.usa.setTextColor(Color.parseColor("#FFFFFF"))
                binding.japan.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binding.japan.setTextColor(Color.parseColor("#000000"))
            }else if(city == "CDG") {
                city = "JFK"
                binding.usa.setBackgroundColor(Color.parseColor("#6666FF"))
                binding.usa.setTextColor(Color.parseColor("#FFFFFF"))
                binding.france.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binding.france.setTextColor(Color.parseColor("#000000"))
            }else if(city == "MNL") {
                city = "JFK"
                binding.usa.setBackgroundColor(Color.parseColor("#6666FF"))
                binding.usa.setTextColor(Color.parseColor("#FFFFFF"))
                binding.philippines.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binding.philippines.setTextColor(Color.parseColor("#000000"))
            }else if(city == "DPS") {
                city = "JFK"
                binding.usa.setBackgroundColor(Color.parseColor("#6666FF"))
                binding.usa.setTextColor(Color.parseColor("#FFFFFF"))
                binding.indonesia.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binding.indonesia.setTextColor(Color.parseColor("#000000"))
            }
        }
        binding.philippines.setOnClickListener {
            if(city == ""){
                city = "MNL"
                binding.philippines.setBackgroundColor(Color.parseColor("#6666FF"))
                binding.philippines.setTextColor(Color.parseColor("#FFFFFF"))
            }else if(city == "NRT") {
                city = "MNL"
                binding.philippines.setBackgroundColor(Color.parseColor("#6666FF"))
                binding.philippines.setTextColor(Color.parseColor("#FFFFFF"))
                binding.japan.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binding.japan.setTextColor(Color.parseColor("#000000"))
            }else if(city == "CDG") {
                city = "MNL"
                binding.philippines.setBackgroundColor(Color.parseColor("#6666FF"))
                binding.philippines.setTextColor(Color.parseColor("#FFFFFF"))
                binding.france.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binding.france.setTextColor(Color.parseColor("#000000"))
            }else if(city == "JFK") {
                city = "MNL"
                binding.philippines.setBackgroundColor(Color.parseColor("#6666FF"))
                binding.philippines.setTextColor(Color.parseColor("#FFFFFF"))
                binding.usa.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binding.usa.setTextColor(Color.parseColor("#000000"))
            }else if(city == "DPS") {
                city = "MNL"
                binding.philippines.setBackgroundColor(Color.parseColor("#6666FF"))
                binding.philippines.setTextColor(Color.parseColor("#FFFFFF"))
                binding.indonesia.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binding.indonesia.setTextColor(Color.parseColor("#000000"))
            }
        }
        binding.indonesia.setOnClickListener {
            if(city == ""){
                city = "DPS"
                binding.indonesia.setBackgroundColor(Color.parseColor("#6666FF"))
                binding.indonesia.setTextColor(Color.parseColor("#FFFFFF"))
            }else if(city == "NRT") {
                city = "DPS"
                binding.indonesia.setBackgroundColor(Color.parseColor("#6666FF"))
                binding.indonesia.setTextColor(Color.parseColor("#FFFFFF"))
                binding.japan.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binding.japan.setTextColor(Color.parseColor("#000000"))
            }else if(city == "CDG") {
                city = "DPS"
                binding.indonesia.setBackgroundColor(Color.parseColor("#6666FF"))
                binding.indonesia.setTextColor(Color.parseColor("#FFFFFF"))
                binding.france.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binding.france.setTextColor(Color.parseColor("#000000"))
            }else if(city == "JFK") {
                city = "DPS"
                binding.indonesia.setBackgroundColor(Color.parseColor("#6666FF"))
                binding.indonesia.setTextColor(Color.parseColor("#FFFFFF"))
                binding.usa.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binding.usa.setTextColor(Color.parseColor("#000000"))
            }else if(city == "MNL") {
                city = "DPS"
                binding.indonesia.setBackgroundColor(Color.parseColor("#6666FF"))
                binding.indonesia.setTextColor(Color.parseColor("#FFFFFF"))
                binding.philippines.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binding.philippines.setTextColor(Color.parseColor("#000000"))
            }
        }

        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_CANCELED) {
                finish()
            }
        }

        binding.selectCountryBtn.setOnClickListener {
            if(city != "") {
                Log.d("API",city)
                val intent = Intent(this, DateActivity::class.java)
                intent.putExtra("city", city)
                activityResultLauncher.launch(intent)
            }else{
                Toast.makeText(this, "여행하실 곳을 선택하세요.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.backToMainBtn.setOnClickListener {
            finish()
        }
    }

}