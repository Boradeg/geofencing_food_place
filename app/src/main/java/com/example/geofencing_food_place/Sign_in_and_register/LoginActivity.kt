//package com.example.geofencing_food_place.Sign_in_and_register
//
//import android.content.DialogInterface
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.widget.EditText
//import androidx.appcompat.app.AlertDialog
//import com.example.geofencing_food_place.Activities.HomeActivity
//import com.example.geofencing_food_place.R
//import com.example.geofencing_food_place.databinding.ActivityOwnerLoginBinding
//import com.google.firebase.auth.FirebaseAuth
//import com.shashank.sony.fancytoastlib.FancyToast
//
//class LoginActivity : AppCompatActivity() {
//
//    lateinit var auth : FirebaseAuth
//    lateinit var binding:ActivityOwnerLoginBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding=ActivityOwnerLoginBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        binding.backBtnActLogin.setOnClickListener(){
//            val intent = Intent(applicationContext,HomeActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
//        binding.signUpTextview.setOnClickListener {
//            val intent = Intent(applicationContext, RegistrationActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
//
//        auth = FirebaseAuth.getInstance()
//        binding.loginBtn.setOnClickListener {
//
//            if(binding.email.text!!.isEmpty() ) {
//                binding.email.setError("Enter Email Id")
//                return@setOnClickListener
//            }
//            else if (binding.pass.text!!.isEmpty()){
//                binding.pass.setError("Enter Password")
//                return@setOnClickListener
//            }
//            auth.signInWithEmailAndPassword(binding.email.text.toString(),binding.pass.text.toString())
//                .addOnCompleteListener {
//                    if(it.isSuccessful)
//                    {
//                        FancyToast.makeText(this,"Login Success", FancyToast.LENGTH_LONG, FancyToast.SUCCESS,true).show()
//                        val intent = Intent(applicationContext, HomeActivity::class.java)
//                        startActivity(intent)
//                        finish()
//                    }
//                    else
//                    {
//                        FancyToast.makeText(this,"Failed to login", FancyToast.LENGTH_LONG, FancyToast.ERROR,true).show()
//                    }
//                }
//        }
//        //forgot password
//        binding.tvForgetPassword.setOnClickListener {
//            val builder = AlertDialog.Builder(this)
//            builder.setTitle("Forgot Password")
//            val view = layoutInflater.inflate(R.layout.dailog_forgot,null)
//            val username = view.findViewById<EditText>(R.id.ed_forgot)
//            builder.setView(view)
//            builder.setPositiveButton("Reset", DialogInterface.OnClickListener { _, _ ->
//                forgotpassword(username)
//            })
//            builder.setNegativeButton("Close", DialogInterface.OnClickListener { _, _ ->  })
//            builder.show()
//
//        }
//    }
//
//    private fun forgotpassword(username: EditText?) {
//
//        auth.sendPasswordResetEmail(username!!.text.toString())
//            .addOnCompleteListener {
//                if(it.isSuccessful)
//                {
//                    FancyToast.makeText(this,"Email Sent", FancyToast.LENGTH_LONG,
//                        FancyToast.SUCCESS,true).show()
//                    //Toast.makeText(applicationContext,"Email Sent", Toast.LENGTH_LONG).show()
//                }
//            }
//
//
//
//    }
//}