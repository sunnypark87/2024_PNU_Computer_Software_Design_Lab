package com.example.FlightApplication.Activity

import android.Manifest
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.FlightApplication.DatabaseHelper
import com.example.FlightApplication.databinding.ActivityMainBinding
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var profileGetResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)
        requestPermission()

        profileGetResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                requestPermission()
            }
        }

        binding.travelBtn.setOnClickListener {
            val intent = Intent(this, CountryActivity::class.java)
            startActivity(intent)
        }

        binding.profileBtn.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            profileGetResult.launch(intent)
        }
    }

    private fun loadProfile() {
        val profile = dbHelper.loadProfile()
        binding.welcomeText.text = "환영합니다  ${profile[0]}님!!"
        binding.currentProfile.setImageURI(Uri.parse(profile[1]))
    }

    private fun requestPermission() {
        TedPermission.create()
            .setPermissionListener(object : PermissionListener {
                override fun onPermissionGranted() {
                    loadProfile()
                }

                override fun onPermissionDenied(deniedPermissions: List<String>) {
                    Toast.makeText(this@MainActivity, "권한을 허가해주세요!", Toast.LENGTH_SHORT).show()
                }
            })
            .setDeniedMessage("권한을 허용해주세요. \n" +
                    "[설정] > [애플리케이션] > [My Application]" +
                    " > [권한] > '사진 및 동영상' 허용")
            .setPermissions(Manifest.permission.READ_MEDIA_IMAGES)
            .check()
    }
}
