package com.example.geofencing_food_place.Activities

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

import androidx.core.content.ContextCompat
import com.example.geofencing_food_place.R
import com.example.geofencing_food_place.Sign_in_and_register.RegistrationActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // User is signed in, navigate to MainActivity
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        } else {
            // No user is signed in, navigate to LoginActivity
            startActivity(Intent(this, RegistrationActivity::class.java))
            finish()
        }
        var start = findViewById<AppCompatButton>(R.id.login_btn_start)
        start.setOnClickListener {

            startActivity(Intent(this, RegistrationActivity::class.java))
            finish()
        }
        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this,
            R.color.black
        )))


    }
}