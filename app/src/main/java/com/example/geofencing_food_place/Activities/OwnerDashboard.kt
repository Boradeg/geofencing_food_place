package com.example.geofencing_food_place.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.geofencing_food_place.Foods.Addfood
import com.example.geofencing_food_place.Place.Addplace
import com.example.geofencing_food_place.R
import com.example.geofencing_food_place.Shops.Addshop
import com.example.geofencing_food_place.databinding.ActivityOwnerDashboardBinding

class OwnerDashboard : AppCompatActivity() {

    private lateinit var binding: ActivityOwnerDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner_dashboard)

        binding = ActivityOwnerDashboardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val img1 = binding.client2
        val img2 = binding.place2
        val img3 = binding.food2



        img1.setOnClickListener {
            val intent = Intent(applicationContext,
                Addshop::class.java)
            startActivity(intent)
        }
        img2.setOnClickListener {
            val intent = Intent(applicationContext,
                Addplace::class.java)
            startActivity(intent)
        }

        img3.setOnClickListener {
            val intent = Intent(applicationContext,
                Addfood::class.java)
            startActivity(intent)
        }


    }
}