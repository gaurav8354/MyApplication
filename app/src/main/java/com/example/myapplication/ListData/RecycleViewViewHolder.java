package com.example.myapplication.ListData;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;

public class RecycleViewViewHolder extends RecyclerView.ViewHolder {
    TextView tv_name,tv_email,tv_pass,tv_no;
    public RecycleViewViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_email=itemView.findViewById(R.id.rv_list_email);
        tv_name=itemView.findViewById(R.id.rv_list_name);
        tv_pass=itemView.findViewById(R.id.rv_list_password);
        tv_no=itemView.findViewById(R.id.rv_list_no);

    }
}
