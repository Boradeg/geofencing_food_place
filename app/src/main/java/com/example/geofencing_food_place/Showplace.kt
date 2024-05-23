package com.example.geofencing_food_place

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener

class Showplace : AppCompatActivity() {
//    private var database: FirebaseDatabase? = null
//    private var ref: DatabaseReference? = null
//    private var adapter: PlaceAdapter? = null
//    private var list: ArrayList<MyPlace>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showplace)
        val intent = intent
       // val str = intent.getStringExtra("address")
       // Toast.makeText(applicationContext, str, Toast.LENGTH_LONG).show()
//        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
//        recyclerView.setHasFixedSize(true)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        list = ArrayList()
//        database = FirebaseDatabase.getInstance()
//        val mDatabaseRef = FirebaseDatabase.getInstance().getReference("Place")
//       // val query: Query = mDatabaseRef.orderByChild("address").equalTo(str)
//        mDatabaseRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                print(dataSnapshot)
//                for (data in dataSnapshot.children) {
//                    println(data)
//                    val models: MyPlace? = data.getValue(MyPlace::class.java)
//                    println(models)
//                    if (models != null) {
//                        list!!.add(models)
//                    }
//                }
//                adapter = PlaceAdapter(list, applicationContext)
//                recyclerView.adapter = adapter
//            }
//            override fun onCancelled(databaseError: DatabaseError) {}
//        })
    }
}