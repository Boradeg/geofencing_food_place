package com.example.geofencing_food_place

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.location.Geocoder
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.io.IOException
import java.util.Locale

class popnotify : AppCompatActivity() {


    val MY_PREFS_NAME = "MyPrefsFile"

    val key:Int?=null
    var theno:Int?=null
    var lat: String? = null
    var log: String? = null
    var address: String? =null
    var fusedLocationProviderClient: FusedLocationProviderClient? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popnotify)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "My Notification",
                "My Notification",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)


        }

        getlocation()
    }

    private fun getlocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
            return
        }

        fusedLocationProviderClient!!.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val geocoder = Geocoder(this@popnotify, Locale.getDefault())
                try {
                    val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)


                    lat = addresses!![0].latitude.toString()
                    log = addresses[0].longitude.toString()
                    address = addresses.get(0).getAddressLine(0)
                    Toast.makeText(applicationContext,address.toString(), Toast.LENGTH_LONG).show()
                    val mynumber = 895
                    println("The key for value  $mynumber")

                    val map = HashMap<String, Int>() //Creating HashMap
                    map["bata"] = 50114
                    map["fasttrack watch"] = 895
                    map["sss"] = 757
                    map["starbucks"] = 895
                    for(key in map.keys)
                    {
                        println("Element at key $key : ${map[key]}")

                        if(map[key] == 895)
                        {
                            println("................"+key)




                            senddata(key)
                        }


                    }



                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

    }

    private fun senddata(key: String) {
        val builder = NotificationCompat.Builder(this@popnotify, "My Notification")

        builder.setContentTitle("New Notification")
        builder.setContentText(key.toString())
        builder.setSmallIcon(R.drawable.geo)
        builder.setAutoCancel(true)
        builder.setStyle(NotificationCompat.InboxStyle())

        val managerCompat = NotificationManagerCompat.from(this@popnotify)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        managerCompat.notify(1, builder.build())

        val notification: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val r = RingtoneManager.getRingtone(applicationContext, notification)
        r.play()


        val prefs = getSharedPreferences("My", MODE_PRIVATE)
        val email = prefs.getString("mail", "No name defined") //"No name defined" is the default value.

        val se = send(applicationContext,email,"Place Order Successfully",key);
        se.execute()

    }
}