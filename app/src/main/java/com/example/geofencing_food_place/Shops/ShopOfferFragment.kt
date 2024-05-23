package com.example.geofencing_food_place.Shops

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.geofencing_food_place.MyAdapter
import com.example.geofencing_food_place.R
import com.example.geofencing_food_place.databinding.FragmentShopOfferBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ShopOfferFragment : Fragment() {
    private var database: FirebaseDatabase? = null
    private var ref: DatabaseReference? = null
    private var adapter: MyAdapter? = null
    private var list: ArrayList<MyShop>? = null
    private lateinit var binding: FragmentShopOfferBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentShopOfferBinding.inflate(inflater, container, false)

        binding.showLocationButton.setOnClickListener {
            replaceFragment(ShopLocationFragment())
        }

        setImageSlider()
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
        fragmentTransaction.addToBackStack(null) // Optional: add to back stack if you want to allow users to navigate back
        fragmentTransaction.commit()
    }

    private fun LoadData() {
        binding.pbar.visibility = View.VISIBLE
        database = FirebaseDatabase.getInstance()
        val mDatabaseRef = database!!.getReference("Shop")

        mDatabaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                list?.clear()
                for (data in dataSnapshot.children) {
                    val models: MyShop? = data.getValue(MyShop::class.java)
                    models?.let { list!!.add(it) }
                }
                adapter?.notifyDataSetChanged()
                binding.pbar.visibility = View.GONE
            }

            override fun onCancelled(databaseError: DatabaseError) {
                binding.pbar.visibility = View.GONE
            }
        })
    }

    private fun setRecyclerView() {
        binding.recyclerview.setHasFixedSize(true)
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        list = ArrayList()
        adapter = MyAdapter(list!!, requireContext())
        binding.recyclerview.adapter = adapter
    }

    private fun setImageSlider() {
        val imageSlider = binding.imgSlider
        val slideModels = ArrayList<SlideModel>()

        slideModels.add(SlideModel(R.drawable.img_for_slider, ScaleTypes.FIT))

        imageSlider.setImageList(slideModels, ScaleTypes.FIT)
    }
}

data class MyShop(
    val Landmark: String = "",
    val MobileNo: String = "",
    val Offer: String = "",
    val ProductName: String = "",
    val lati: String = "",
    val longi: String = "",
    val imageurl: String = "",
    val ShopName: String = "",
    val Address: String = "",
    val Rating: String = ""
)
