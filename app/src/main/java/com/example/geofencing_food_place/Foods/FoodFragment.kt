package com.example.geofencing_food_place.Foods

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.geofencing_food_place.MyAdapter
import com.example.geofencing_food_place.R
import com.example.geofencing_food_place.Shops.MyShop
import com.example.geofencing_food_place.databinding.FragmentFoodBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FoodFragment : Fragment() {
    private var database: FirebaseDatabase? = null
    private var ref: DatabaseReference? = null
    private var adapter: MyAdapter? = null
    private var list: ArrayList<MyShop>? = null
    private lateinit var binding: FragmentFoodBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFoodBinding.inflate(inflater, container, false)
        binding.showLocationButton.setOnClickListener {
            replaceFragment(FoodLocationFragment())
        }
        setRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LoadData()
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container2, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun LoadData() {
        binding.pbar.visibility = View.VISIBLE
        database = FirebaseDatabase.getInstance()
        val mDatabaseRef = database!!.getReference("Food")

        mDatabaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!isAdded) return

                list?.clear()
                for (data in dataSnapshot.children) {
                    val models: MyShop? = data.getValue(MyShop::class.java)
                    if (models != null) {
                        list!!.add(models)
                    }
                }
                adapter = MyAdapter(list!!, requireContext())
                binding.recyclerview.adapter = adapter
                binding.pbar.visibility = View.GONE
            }

            override fun onCancelled(databaseError: DatabaseError) {
                if (!isAdded) return
                binding.pbar.visibility = View.GONE
            }
        })
    }

    private fun setRecyclerView() {
        binding.recyclerview.setHasFixedSize(true)
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        list = ArrayList()
    }
}

