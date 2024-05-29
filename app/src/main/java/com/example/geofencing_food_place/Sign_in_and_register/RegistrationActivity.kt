package com.example.geofencing_food_place.Sign_in_and_register

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.geofencing_food_place.Activities.HomeActivity
import com.example.geofencing_food_place.Activities.OwnerDashboard
import com.example.geofencing_food_place.Activities.OwnerDashboard2Activity
import com.example.geofencing_food_place.Activities.UserDashboard2
import com.example.geofencing_food_place.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.shashank.sony.fancytoastlib.FancyToast

class RegistrationActivity : AppCompatActivity() {


    lateinit var auth: FirebaseAuth

    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null
    lateinit var flag:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         flag= intent.getStringExtra("role").toString()
        Toast.makeText(this, ""+flag, Toast.LENGTH_SHORT).show()



        val name = findViewById<TextInputEditText>(R.id.username_si)
        val number = findViewById<TextInputEditText>(R.id.number_si)
        val email = findViewById<TextInputEditText>(R.id.email_si)
        val password = findViewById<TextInputEditText>(R.id.password_si)
        val btn = findViewById<AppCompatButton>(R.id.sign_up_si)
        val back = findViewById<RelativeLayout>(R.id.back_btn_act_main)
        back.setOnClickListener(){
            val intent = Intent(applicationContext,HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        val txtloginuser = findViewById<TextView>(R.id.sign_up_tv_s)

//        txtloginuser.setOnClickListener(){
//            val intent = Intent(applicationContext, R::class.java)
//            startActivity(intent)
//            finish()
//        }


        auth = FirebaseAuth.getInstance()

        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("User")

        btn.setOnClickListener {

            if(name.text!!.isEmpty())
            {
                name.setError("Enter name")
                return@setOnClickListener
            }else if(password.text!!.isEmpty())
            {
                password.setError("Enter Password ")
                return@setOnClickListener
            }else if(number.text!!.isEmpty())
            {
                number.setError("Enter Contact Number")
                return@setOnClickListener
            }else if(email.text!!.isEmpty())
            {
                email.setError("Enter Email id")
                return@setOnClickListener
            }
            findViewById<ProgressBar>(R.id.pbar).visibility=View.VISIBLE


            auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener {
                    if(it.isSuccessful)
                    {
                        findViewById<ProgressBar>(R.id.pbar).visibility=View.GONE

//                        val currentuser = auth.currentUser
//                        val currentUserdb = databaseReference?.child((currentuser?.uid!!))
//                        currentUserdb?.child("name")?.setValue(name.text.toString())
//                        currentUserdb?.child("number")?.setValue(number.text.toString())

                        // Save data in SharedPreferences
                        findViewById<ProgressBar>(R.id.pbar).visibility=View.GONE
                        FancyToast.makeText(this,"Account Create Successfully", FancyToast.LENGTH_LONG,
                            FancyToast.SUCCESS,true).show()
                        auth.signInWithEmailAndPassword(email.text.toString(),password.text.toString())
                            .addOnCompleteListener {
                                if(it.isSuccessful)
                                {


                                    FancyToast.makeText(this,"Login Success", FancyToast.LENGTH_LONG, FancyToast.SUCCESS,true).show()
                                 //  var flag="user"
                                    val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                                    val fcmToken = sharedPreferences.getString("fcmToken", "")
                                    saveUserDataFirebase(this, email.text.toString(), name.text.toString(), number.text.toString(),password.text.toString(),flag.toString(),fcmToken.toString())
                                    saveUserDataSQLite(this, email.text.toString(), name.text.toString(), number.text.toString(),password.text.toString(),flag.toString(),fcmToken.toString())

                                    if(flag.equals("owner")){
                                        val intent = Intent(applicationContext, OwnerDashboard2Activity::class.java)
                                       startActivity(intent)
                                       finish()
                                   }
                                    if(flag.equals("user")){
                                        val intent = Intent(applicationContext, UserDashboard2::class.java)
                                        startActivity(intent)
                                        finish()
                                    }

                                }
                                else
                                {
                                    FancyToast.makeText(this,"Failed to login", FancyToast.LENGTH_LONG, FancyToast.ERROR,true).show()
                                }
                            }
                        //startActivity(Intent(this,HomeActivity::class.java))
                        //finish()
                    }
                    else
                    {
                        findViewById<ProgressBar>(R.id.pbar).visibility=View.GONE
                        FancyToast.makeText(this,"failed",FancyToast.LENGTH_LONG,
                            FancyToast.ERROR,true).show()
                        //Toast.makeText(applicationContext,"failed", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
    private fun saveUserDataSQLite(
        context: Context, email: String, name: String, number: String,
        password:String,
        role: String,fcmToken:String
    ) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
        Toast.makeText(this, "role $role", Toast.LENGTH_SHORT).show()
        val editor = sharedPreferences.edit()
        editor.putString("email", email)
        editor.putString("name", name)
        editor.putString("number", number)
        editor.putString("password", password)
        editor.putString("role", role.toString())
        editor.putString("fcmToken", fcmToken.toString())
        editor.apply()
    }




    private fun saveUserDataFirebase(
        context: Context, email: String, name: String, number: String,
        password: String, role: String,fcmToken: String
    )
    {
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        currentUser?.let { user ->
            val userId = user.uid // Use Firebase Authentication UID as the unique identifier

            // Initialize Firebase Database
            val database: FirebaseDatabase = FirebaseDatabase.getInstance()
            val usersRef: DatabaseReference = database.getReference("Users")

            // Create a User object
            val user = User(email, name, number, password, role,fcmToken)

            // Save the user data in Firebase Realtime Database
            usersRef.child(number).setValue(user)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "User data saved successfully.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Failed to save user data.", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context, "Failed to save user data: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }



}
data class User(
    val email: String = "",
    val name: String = "",
    val number: String = "",
    val password: String = "",
    val role: String = "",
    val token: String = ""
)