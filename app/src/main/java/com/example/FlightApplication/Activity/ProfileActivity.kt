package com.example.FlightApplication.Activity

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.SpannableStringBuilder
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.FlightApplication.DatabaseHelper
import com.example.FlightApplication.databinding.ProfileChangeBinding
import com.google.android.material.imageview.ShapeableImageView
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ProfileChangeBinding
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        binding = ProfileChangeBinding.inflate(layoutInflater);
        val view = binding.root;
        setContentView(view);

        dbHelper = DatabaseHelper(this)
        val profile = dbHelper.loadProfile()
        binding.nick.text = profile.get(0).toEditable()
        binding.newProfile.setImageURI(Uri.parse(profile.get(1)))
        binding.newProfile.tag = Uri.parse(profile.get(1))


        binding.imageBtn.setOnClickListener {
            requestPermission()
        }

        binding.changeBtn.setOnClickListener {
            val name = binding.nick.text.toString()
            val profileImageUri = binding.newProfile.tag as? Uri
            val profileImage = profileImageUri.toString()
            dbHelper.insertOrUpdateProfile(name, profileImage)

            val resultIntent = Intent()
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }

    fun String.toEditable(): Editable = SpannableStringBuilder(this)


    private fun requestPermission() {
        TedPermission.create()
            .setPermissionListener(object : PermissionListener {
                override fun onPermissionGranted() {
                    openGallery()
                }

                override fun onPermissionDenied(deniedPermissions: List<String>) {
                    Toast.makeText(this@ProfileActivity, "권한을 허가해주세요!", Toast.LENGTH_SHORT).show()
                }
            })
            .setDeniedMessage("권한을 허용해주세요. \n" +
                    "[설정] > [애플리케이션] > [My Application]" +
                    " > [권한] > '사진 및 동영상' 허용")
            .setPermissions(Manifest.permission.READ_MEDIA_IMAGES)
            .check()
    }

    fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.setType("image/*")
        startActivityForResult(intent, 1)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                binding.newProfile.setImageURI(uri)
                binding.newProfile.tag = uri
            }
        }
    }

    fun saveImageToFile(context: Context, newProfile: ShapeableImageView): Uri? {
        // Drawable에서 Bitmap 가져오기
        val drawable = newProfile.drawable as? BitmapDrawable
        val bitmap = drawable?.bitmap

        // Bitmap이 null인 경우 null 반환
        if (bitmap == null) {
            return null
        }

        // 파일을 저장할 디렉토리 생성
        val wrapper = ContextWrapper(context)
        val imagesFolder = wrapper.getDir("images", Context.MODE_PRIVATE)
        val file = File(imagesFolder, "profile.jpg")

        try {
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }

        // 파일의 Uri 반환
        return Uri.fromFile(file)
    }
}