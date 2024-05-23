package com.example.geofencing_food_place
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.geofencing_food_place.Activities.StartActivity

@SuppressLint("CustomSplashScreen")
class Splashscreen : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)


        val img = findViewById<ImageView>(R.id.imageView3)

        img.alpha = 0f
        img.animate().setDuration(1500).alpha(1f).withEndAction{
            val i = Intent(this, StartActivity::class.java)
            startActivity(i)

            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()

        }
    }
}