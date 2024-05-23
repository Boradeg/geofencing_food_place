//package com.example.geofencing_food_place
//
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import com.google.android.material.bottomnavigation.BottomNavigationView
//
//class Userdash : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_userdash)
//
//
//        val bottom = findViewById<BottomNavigationView>(R.id.bottom)
//
//        bottom.setOnNavigationItemSelectedListener {
//            when(it.itemId)
//            {
//
//                R.id.shop ->
//                {
//                    val i = Intent(applicationContext, Locationshop::class.java)
//                    startActivity(i)
//
//                    true
//                }
//
//                R.id.place ->
//                {
//                    val i = Intent(applicationContext, Locationplace::class.java)
//                    startActivity(i)
//
//                    true
//                }
//
//                R.id.food ->
//                {
//                    val i = Intent(applicationContext, LocationFood::class.java)
//                    startActivity(i)
//
//                    true
//                }
//
//                R.id.notify ->
//                {
//                    val i = Intent(applicationContext,popnotify::class.java)
//                    startActivity(i)
//                    true
//                }
//
//
//
//                else -> {false}
//            }
//        }
//
//    }
//}