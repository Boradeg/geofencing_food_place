package com.example.geofencing_food_place.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.geofencing_food_place.Chats.ChatFragment
import com.example.geofencing_food_place.Foods.Addfood
import com.example.geofencing_food_place.Foods.FoodFragment
import com.example.geofencing_food_place.Place.Addplace
import com.example.geofencing_food_place.Place.PlaceFragment
import com.example.geofencing_food_place.Profile.ProfileFragment
import com.example.geofencing_food_place.R
import com.example.geofencing_food_place.Shops.Addshop
import com.example.geofencing_food_place.Shops.ShopOfferFragment
import com.example.geofencing_food_place.databinding.ActivityOwnerDashboardBinding

class OwnerDashboard : AppCompatActivity() {

    private lateinit var binding: ActivityOwnerDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner_dashboard)

        binding = ActivityOwnerDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        binding.bottom2.setOnNavigationItemSelectedListener {
//            when(it.itemId)
//            {
//                R.id.dashboard ->
//                {
//                    replaceFragment(ProfileFragment())
//                    true
//                }
//                R.id.profile ->
//                {
//                    replaceFragment(ProfileFragment())
//                    true
//                }
//                R.id.chats->
//                {
//                    replaceFragment(ChatFragment())
//                    true
//                }
//                else -> {false}
//            }
//        }

        binding.client2.setOnClickListener {
            val intent = Intent(applicationContext,
                Addshop::class.java)
            startActivity(intent)
        }
        binding.place2.setOnClickListener {
            val intent = Intent(applicationContext,
                Addplace::class.java)
            startActivity(intent)
        }

        binding.food2.setOnClickListener {
            val intent = Intent(applicationContext,
                Addfood::class.java)
            startActivity(intent)
        }


    }

}