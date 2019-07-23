package com.example.myapplication.RecycleViewSubList;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

public class ViewHolderSubMenu extends RecyclerView.ViewHolder {
     public   ImageView imageView;
     public    TextView nme,discription;
     CardView cardView;
     public ViewHolderSubMenu(View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.img_rv_list_img);
        nme=itemView.findViewById(R.id.tv_rv_name);
        discription=itemView.findViewById(R.id.tv_rv_discription);
        cardView=itemView.findViewById(R.id.cardview_sublist);



    }
}
