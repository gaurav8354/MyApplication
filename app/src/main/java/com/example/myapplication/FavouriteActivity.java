package com.example.myapplication;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.myapplication.Database.SqlHelper;
import com.example.myapplication.Json.PojoJson;
import com.example.myapplication.RecycleViewList.AdapterSubMenu;

import java.util.ArrayList;
public class FavouriteActivity extends AppCompatActivity {
    ArrayList<PojoJson> list=new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        recyclerView=findViewById(R.id.list_data_fav_recycleview);
        SqlHelper sqlHelper=new SqlHelper(getApplicationContext());
        Cursor cursor=sqlHelper.showAllFavData();
        while(cursor.moveToNext()!=false)
        {
            PojoJson pojoJson=new PojoJson();
            pojoJson.setDescription(cursor.getString(2));
            pojoJson.setPlantId(cursor.getString(0));
            pojoJson.setName(cursor.getString(1));
            pojoJson.setImageUrl(cursor.getString(3));
            pojoJson.setGrowZoneNumber(cursor.getInt(4));
            pojoJson.setWateringInterval(cursor.getInt(5));
            list.add(pojoJson);
        }
        recycleviewload(list);

    }

    private void recycleviewload(ArrayList<PojoJson> list) {
        layoutManager =new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter=new AdapterSubMenu(this,list);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onRestart() {
        recyclerView=findViewById(R.id.list_data_fav_recycleview);
        SqlHelper sqlHelper=new SqlHelper(getApplicationContext());
        Cursor cursor=sqlHelper.showAllFavData();
        ArrayList<PojoJson> list1=new ArrayList<>();
        while(cursor.moveToNext()!=false)
        {
            PojoJson pojoJson=new PojoJson();
            pojoJson.setDescription(cursor.getString(2));
            pojoJson.setPlantId(cursor.getString(0));
            pojoJson.setName(cursor.getString(1));
            pojoJson.setImageUrl(cursor.getString(3));
            pojoJson.setGrowZoneNumber(cursor.getInt(4));
            pojoJson.setWateringInterval(cursor.getInt(5));
            list1.add(pojoJson);
        }
        recycleviewload(list1);
        super.onRestart();
    }
}
