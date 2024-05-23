package com.example.geofencing_food_place.Place;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.geofencing_food_place.R;
import com.example.geofencing_food_place.databinding.ActivityAddfoodBinding;
import com.example.geofencing_food_place.databinding.ActivityAddplaceBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;

public class Addplace extends AppCompatActivity {


    private ActivityAddplaceBinding binding;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mref;
    FirebaseStorage firebaseStorage;
    private static final int code = 1;
    Uri imageurl = null;
    String lati, longi;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddplaceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseDatabase = FirebaseDatabase.getInstance();
        mref = firebaseDatabase.getReference().child("Place");
        firebaseStorage = FirebaseStorage.getInstance();

        binding.relLayoutUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, code);
            }
        });
    }
@Override
protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == code && resultCode == RESULT_OK) {
        imageurl = data.getData();
        binding.imageButton2.setImageURI(imageurl);
        binding.imageButton2.setVisibility(View.VISIBLE);
    }
    binding.btninsert.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            binding.pbar.setVisibility(View.VISIBLE);

            String address = binding.idAddress.getText().toString().trim();
            Geocoder coder = new Geocoder(getApplicationContext());
            try {
                ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName(address, 50);
                for (Address add : adresses) {
                    double longitude = add.getLongitude();
                    double latitude = add.getLatitude();
                    lati = String.valueOf(latitude);
                    longi = String.valueOf(longitude);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            StorageReference filepath = firebaseStorage.getReference().child("imagepost").child(imageurl.getLastPathSegment());
            filepath.putFile(imageurl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> downloadurl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                           DatabaseReference newpost = mref.push();
                            newpost.child("PlaceName").setValue(binding.idName.getText().toString().trim());
                            newpost.child("MobileNo").setValue(binding.idNumber.getText().toString().trim());
                            newpost.child("Address").setValue(address);
                            newpost.child("PlaceHistory").setValue(binding.placeHistory.getText().toString().trim());
                            newpost.child("Time").setValue(binding.idTime.getText().toString().trim());
                            newpost.child("Landmark").setValue(binding.idLandmark.getText().toString().trim());
                            newpost.child("lati").setValue(lati);
                            newpost.child("Rating").setValue(String.valueOf(binding.ratingBar.getRating()));
                            newpost.child("longi").setValue(longi);
                            newpost.child("imageurl").setValue(task.getResult().toString());
                            binding.pbar.setVisibility(View.GONE);

                            Toast.makeText(getApplicationContext(), "Product Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    });
}
}
