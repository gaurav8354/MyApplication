package com.example.myapplication.RecycleViewSubList;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.FinalDataRecycleView;
import com.example.myapplication.Json.PojoJson;
import com.example.myapplication.ListData.ArrayListLoad;
import com.example.myapplication.ListData.RecycleViewAdapter;
import com.example.myapplication.R;

import java.util.ArrayList;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class AdapterSubMenu extends RecyclerView.Adapter<ViewHolderSubMenu> {

    ArrayList<PojoJson> ar;
    LayoutInflater layoutInflater ;
    Context cxt;
    public AdapterSubMenu(Context cxt, ArrayList<PojoJson> array) {
        this.ar=array;
        this.cxt=cxt;

    }

    @Override
    public ViewHolderSubMenu onCreateViewHolder(ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(cxt).inflate(R.layout.sublist_element_recycleview,parent,false);
        ViewHolderSubMenu viewHolderSubMenu=new ViewHolderSubMenu(view);
        return viewHolderSubMenu;
    }

    @Override
    public void onBindViewHolder(final ViewHolderSubMenu holder, final int position) {
        holder.nme.setText(ar.get(position).getName());
        holder.discription.setText(ar.get(position).getDescription().substring(0,100)+"......");
        Glide.with(cxt).load(ar.get(position).getImageUrl()).apply(RequestOptions.circleCropTransform()).into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(cxt, position+"", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(cxt, FinalDataRecycleView.class);
                i.putExtra("name",ar.get(position).getName());
                i.putExtra("dis",ar.get(position).getDescription());
                i.putExtra("image",ar.get(position).getImageUrl());
                i.putExtra("id",ar.get(position).getPlantId());
                i.putExtra("w",ar.get(position).getWateringInterval());
                i.putExtra("g",ar.get(position).getGrowZoneNumber());

                cxt.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ar.size();
    }
}
