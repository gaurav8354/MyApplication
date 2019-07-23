package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class FinalDataRecycleView extends AppCompatActivity {
Intent i;
    TextView tv_dis,tv_grow,tv_water,tv_id,tv_name;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_data_recycle_view);
        i=getIntent();
        idsetter();
        setData();

    }

    private void idsetter() {
        imageView=findViewById(R.id.imageView_final);
        tv_dis=findViewById(R.id.tv_final_discription);
        tv_grow=findViewById(R.id.tv_growzonenumber);
        tv_name=findViewById(R.id.tv_final_name);
        tv_water=findViewById(R.id.tv_wateringInterval);
        getSupportActionBar().setTitle(i.getStringExtra("name"));


    }

    private void setData() {
        tv_dis.setText(i.getStringExtra("dis"));
        tv_grow.setText(tv_grow.getText().toString()+" "+i.getIntExtra("g",0));
        tv_water.setText(tv_water.getText().toString()+" : "+i.getIntExtra("w",0));
        tv_name.setText(i.getStringExtra("name"));
        Glide.with(this).load(i.getStringExtra("image")).into(imageView);
    }
}
