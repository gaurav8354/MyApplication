package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class FinalDataRecycleView extends AppCompatActivity {
    Intent i;
    TextView tv_dis, tv_grow, tv_water, tv_id, tv_name;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_data_recycle_view);
        getSupportActionBar().hide();
        changeStatusBarColor("#FFFFFF");
        status_icon_color();
        i = getIntent();
        idsetter();
        setData();

    }

    private void idsetter() {
        imageView = findViewById(R.id.imageView_final);
        tv_dis = findViewById(R.id.tv_final_discription);
        tv_grow = findViewById(R.id.tv_growzonenumber);
        tv_name = findViewById(R.id.tv_final_name);
        tv_water = findViewById(R.id.tv_wateringInterval);
        getSupportActionBar().setTitle(i.getStringExtra("name"));


    }

    private void setData() {
        //tv_dis.setText(i.getStringExtra("dis"));
        String str = i.getStringExtra("dis");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            tv_dis.setText(Html.fromHtml(str, Html.FROM_HTML_MODE_LEGACY));
        } else {
            tv_dis.setText(Html.fromHtml(str));
        }
        tv_grow.setText(tv_grow.getText().toString() + " " + i.getIntExtra("g", 0));
        tv_water.setText(tv_water.getText().toString() + " : " + i.getIntExtra("w", 0));
        tv_name.setText(i.getStringExtra("name"));
        Glide.with(this).load(i.getStringExtra("image")).into(imageView);
    }

    private void changeStatusBarColor(String color) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(color));
        }
    }
    void status_icon_color(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            if (true) {
                decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                // We want to change tint color to white again.
                // You can also record the flags in advance so that you can turn UI back completely if
                // you have set other flags before, such as translucent or full screen.
                decor.setSystemUiVisibility(0);
            }
        }

    }

}
