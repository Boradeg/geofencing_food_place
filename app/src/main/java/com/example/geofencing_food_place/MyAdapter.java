package com.example.geofencing_food_place;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geofencing_food_place.Shops.MyShop;
import com.example.geofencing_food_place.Shops.prodetails;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    ArrayList<MyShop> mList;
    Context context;

    public MyAdapter(ArrayList<MyShop> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_dummy,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyShop vacancy1 = mList.get(position);
        holder.idShopName.setText(""+vacancy1.getShopName());
        holder.idShopAddress.setText("Address : "+vacancy1.getAddress());
        holder.idShopProductOffer.setText(""+vacancy1.getOffer());
        holder.idProductRating.setText(""+vacancy1.getRating().toString());
       // Glide.with(holder.img1.getContext()).load(vacancy1.getImageurl()).into(holder.img1);
        holder.likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current drawable resource ID of the like button
//                int currentDrawableId = (Integer) holder.likeBtn.getTag();
//
//                // Check the current drawable resource ID and toggle between the two images
//                if (currentDrawableId == R.drawable.btn_3) {
//                    // If the current drawable is like1, change it to like2
//                    holder.likeBtn.setImageResource(R.drawable.like2);
//                    // Update the tag to indicate the new drawable resource ID
//                    holder.likeBtn.setTag(R.drawable.like2);
//
//                } else {
//                    // If the current drawable is like2, change it back to like1
//                    holder.likeBtn.setImageResource(R.drawable.btn_3);
//                    // Update the tag to indicate the new drawable resource ID
//                    holder.likeBtn.setTag(R.drawable.btn_3);
//                }
            }
        });



        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), prodetails.class);

                intent.putExtra("ShopName", vacancy1.getShopName());
                intent.putExtra("ShopAddress",vacancy1.getAddress());
                intent.putExtra("MobileNo",vacancy1.getMobileNo());
                intent.putExtra("ProductName",vacancy1.getProductName());
                intent.putExtra("Offer",vacancy1.getOffer());
                intent.putExtra("lati",vacancy1.getLati());
                intent.putExtra("longi",vacancy1.getLongi());
                intent.putExtra("landmark",vacancy1.getLandmark());
                intent.putExtra("Rating",vacancy1.getRating());
                intent.putExtra("url",vacancy1.getImageurl());
               // intent.putExtra("Rating",vacancy1.getRating().toString());

               v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView img1;
        TextView idShopName,idShopAddress,idShopProductOffer,idProductRating;
        ImageView likeBtn;

        RelativeLayout relativeLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img1 =itemView.findViewById(R.id.shop_img);
            idShopName = itemView.findViewById(R.id.shop_name);
            idShopAddress = itemView.findViewById(R.id.address);
            idProductRating = itemView.findViewById(R.id.rating_bar);
            idShopProductOffer = itemView.findViewById(R.id.tv_offer);
            relativeLayout = itemView.findViewById(R.id.rel_l);
            likeBtn = itemView.findViewById(R.id.like_btn);
        }
    }
}
