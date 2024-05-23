package com.example.geofencing_food_place.Place

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.geofencing_food_place.R
import com.example.geofencing_food_place.databinding.FragmentPlaceBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PlaceFragment : Fragment() {
    private lateinit var binding: FragmentPlaceBinding
    private var database: FirebaseDatabase? = null
    private var ref: DatabaseReference? = null
    private var adapter: PlaceAdapter2? = null
    private var list: ArrayList<PlaceDataClass>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlaceBinding.inflate(inflater, container, false)
        binding.recyclerview.setHasFixedSize(true)
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        list = ArrayList()

        binding.showLocationButton.setOnClickListener {
            replaceFragment(PlaceLocationFragment())
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LoadPlaceData()
    }

    private fun LoadPlaceData() {
        binding.pbar.visibility = View.VISIBLE
        database = FirebaseDatabase.getInstance()
        val mDatabaseRef = database!!.getReference("Place")

        mDatabaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!isAdded) return

                list?.clear()
                for (data in dataSnapshot.children) {
                    val models: PlaceDataClass? = data.getValue(PlaceDataClass::class.java)
                    if (models != null) {
                        list!!.add(models)
                    }
                }
                adapter = PlaceAdapter2(list!!, requireContext())
                binding.recyclerview.adapter = adapter
                binding.pbar.visibility = View.GONE
            }

            override fun onCancelled(databaseError: DatabaseError) {
                if (!isAdded) return
                binding.pbar.visibility = View.GONE
            }
        })
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container2, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}

data class PlaceDataClass(
    val Landmark: String = "",
    val MobileNo: String = "",
    val PlaceName: String = "",
    val PlaceHistory: String = "",
    val lati: String = "",
    val longi: String = "",
    val imageurl: String = "",
    val Address: String = "",
    val Rating: String = "",
    val Time: String = ""
)
