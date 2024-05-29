package com.example.geofencing_food_place.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.geofencing_food_place.Chats.ChatActivity
import com.example.geofencing_food_place.Chats.ChatFragment
import com.example.geofencing_food_place.Foods.FoodFragment
import com.example.geofencing_food_place.Place.PlaceFragment
import com.example.geofencing_food_place.Profile.ProfileFragment
import com.example.geofencing_food_place.R
import com.example.geofencing_food_place.Shops.ShopOfferFragment
import com.example.geofencing_food_place.databinding.ActivityUserDashboard2Binding
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView

class UserDashboard2 : AppCompatActivity() {
    lateinit var binding:ActivityUserDashboard2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityUserDashboard2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        val bottom = findViewById<BottomNavigationView>(R.id.bottom2)
        replaceFragment(ShopOfferFragment())
        val badgeDrawable: BadgeDrawable = binding.bottom2.getOrCreateBadge(R.id.food)
        if (badgeDrawable != null) {
            badgeDrawable.isVisible = true
            badgeDrawable.text = "3+"
        }
        val badgeDrawable2: BadgeDrawable = binding.bottom2.getOrCreateBadge(R.id.place)
        if (badgeDrawable2 != null) {
            badgeDrawable2.isVisible = true
            badgeDrawable2.text = "1+"
        }
        bottom.setOnNavigationItemSelectedListener {
            when(it.itemId)
            {
                R.id.shop ->
                {
                    replaceFragment(ShopOfferFragment())
                    true
                }
                R.id.place ->
                {
                    badgeDrawable2.isVisible=false
                    replaceFragment(PlaceFragment())
                    true
                }
                R.id.food ->
                {
                    badgeDrawable.isVisible=false
                    replaceFragment(FoodFragment())
                    true
                }
                R.id.profile ->
                {
                    replaceFragment(ProfileFragment())
                    true
                }
                R.id.chats->
                {
                    replaceFragment(ChatFragment())
                    true
                }
                else -> {false}
            }
        }

    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container2, fragment)
        fragmentTransaction.commit()
    }
}