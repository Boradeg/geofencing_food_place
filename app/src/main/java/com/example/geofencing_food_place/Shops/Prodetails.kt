package com.example.geofencing_food_place.Shops

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.geofencing_food_place.databinding.ActivityProdetailsBinding
import com.shashank.sony.fancytoastlib.FancyToast

class prodetails : AppCompatActivity() {
    private val SEND_SMS_PERMISSION_REQUEST_CODE = 1
    lateinit var number:String


    private lateinit var binding:ActivityProdetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityProdetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.name.text = intent.getStringExtra("ShopName")
        var Address=intent.getStringExtra("ShopAddress")

        binding.address.text= intent.getStringExtra("ShopAddress")
        binding.mobileNumber.text = intent.getStringExtra("MobileNo")
         number = intent.getStringExtra("MobileNo").toString()
        binding.productName.text = intent.getStringExtra("ProductName")
        binding.offer.text = intent.getStringExtra("Offer")
        binding.rating.text = intent.getStringExtra("Rating")
        val lati = intent.getDoubleExtra("lati", 0.0)
        val longi = intent.getDoubleExtra("longi", 0.0)
        binding.landmark.text= intent.getStringExtra("landmark")
        val url = intent.getStringExtra("url")
        Glide.with(applicationContext).load(url).into(binding.image)
        var mobileno=intent.getStringExtra("MobileNo")
        binding.sendSmsButton.setOnClickListener {
            if (checkPermission(android.Manifest.permission.SEND_SMS)) {
                sendSms()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.SEND_SMS),
                    SEND_SMS_PERMISSION_REQUEST_CODE
                )
            }

//            val smsManager = SmsManager.getDefault() as SmsManager
//            smsManager.sendTextMessage("+919112601217",null,"I am Interested",null,null)
//            Toast.makeText(applicationContext,"Send Sms",Toast.LENGTH_LONG).show()
        }
        binding.trackButton.setOnClickListener {

            try {
                val uri = Uri.parse("https://www.google.co.in/maps/dir/"+"/"+Address)
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
        binding.ivBackBtn.setOnClickListener{
            finish()
        }

    }
    private fun checkPermission(permission: String): Boolean {
        val check = ContextCompat.checkSelfPermission(this, permission)
        return check == PackageManager.PERMISSION_GRANTED
    }

    private fun sendSms() {
        val smsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(number, null, "I am intrested in this product", null, null)
        FancyToast.makeText(this,"Message Send Successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS,true).show()

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            SEND_SMS_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendSms()
                } else {
                    FancyToast.makeText(this,"Permission Not Granted", FancyToast.LENGTH_LONG, FancyToast.ERROR,true).show()
                    // Permission denied, handle the case
                }
            }
        }


    }

}