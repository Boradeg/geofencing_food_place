package com.example.geofencing_food_place

import android.content.Context
import com.shashank.sony.fancytoastlib.FancyToast

class Utility {

    fun showFancyToast(context: Context, message: String, length: Int, icon: Int, enableIconAnimation: Boolean) {
        FancyToast.makeText(context, message, length, icon, enableIconAnimation).show()
    }

}