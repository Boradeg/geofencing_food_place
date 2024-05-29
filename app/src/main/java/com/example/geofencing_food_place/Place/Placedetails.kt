package com.example.geofencing_food_place.Place

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.geofencing_food_place.R
import com.example.geofencing_food_place.databinding.ActivityPlacedetailsBinding

class Placedetails : AppCompatActivity() {

   lateinit var binding:ActivityPlacedetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityPlacedetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.name.text = intent.getStringExtra("Name")
        var Address=intent.getStringExtra("Address")
        binding.address.text= intent.getStringExtra("Address")
        binding.mobileNumber.text = intent.getStringExtra("MobileNo")
        binding.time.text = intent.getStringExtra("Time")

        binding.landmark.text = intent.getStringExtra("Landmark")

        binding.history.text = intent.getStringExtra("History")

        binding.rating.text = intent.getStringExtra("Rating")

        val lati = intent.getDoubleExtra("lati", 0.0)
        val longi = intent.getDoubleExtra("longi", 0.0)
        val url = intent.getStringExtra("url")
        Glide.with(applicationContext).load(url).into(binding.image)
        var mobileno=intent.getStringExtra("MobileNo")
        Glide.with(applicationContext).load(url).into(binding.image)
        binding.ivBackBtn.setOnClickListener{
            finish()
        }
        binding.trackButton.setOnClickListener {
            try {
                val uri = Uri.parse("https://www.google.co.in/maps/dir/"+"/"+ Address)
                val intent = Intent(Intent.ACTION_VIEW,uri)
                intent.setPackage("com.google.android.apps.maps")
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)

            }catch (e: ActivityNotFoundException)
            {
                val uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps")
                val intent = Intent(Intent.ACTION_VIEW,uri)
                intent.setPackage("com.google.android.apps.maps")
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }

        }
    }
}