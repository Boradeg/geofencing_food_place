package com.example.geofencing_food_place.Foods

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.geofencing_food_place.R
import com.example.geofencing_food_place.Shops.ShopOfferFragment
import com.example.geofencing_food_place.databinding.FragmentFoodLocationBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Locale

class FoodLocationFragment : Fragment() {
    private var btn: Button? = null
    private var btnshow: Button? = null
    private var map: GoogleMap? = null
    private var mfire: DatabaseReference? = null
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private var lati = 0.0
    private var longi = 0.0
    private var address: String? = null
    private lateinit var binding: FragmentFoodLocationBinding
    private var supportMapFragment: SupportMapFragment? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFoodLocationBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        binding.showListButton.setText("Show List")
        binding.showListButton.setOnClickListener {
            replaceFragment(FoodFragment())
        }
        supportMapFragment =
            childFragmentManager.findFragmentById(R.id.google_map2) as SupportMapFragment?
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            getlocation()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }

        return binding.root
    }

    private fun getlocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request the necessary permissions if they are not already granted
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }

        fusedLocationProviderClient?.lastLocation?.addOnSuccessListener { location ->
            if (location != null) {
                val geocoder = Geocoder(requireContext(), Locale.getDefault())
                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)

                lati = location.latitude
                longi = location.longitude
                address = addresses?.get(0)?.subLocality

                Toast.makeText(requireContext(), address.toString(), Toast.LENGTH_LONG).show()

                supportMapFragment?.getMapAsync { googleMap ->
                    map = googleMap
                    mfire = FirebaseDatabase.getInstance().reference.child("Food")
                    mfire?.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            for (s in snapshot.children) {
                                val lat = s.child("lati").value.toString()
                                val lng = s.child("longi").value.toString()
                                try {
                                    val latitude = lat.toDouble()
                                    val longitude = lng.toDouble()
                                    val lokasi = LatLng(latitude, longitude)

                                    map?.addMarker(
                                        MarkerOptions().position(lokasi).title(s.child("shopname").value.toString())
                                    )
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {}
                    })
                    map?.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(lati, longi), 15f
                        )
                    )
                }
            }
        }?.addOnFailureListener {
            it.printStackTrace()
        }
    }
    fun replaceFragment(fragment: Fragment) {
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container2, fragment)
        fragmentTransaction.addToBackStack(null) // Optional: add to back stack if you want to allow users to navigate back
        fragmentTransaction.commit()
    }
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}
