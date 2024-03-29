package com.example.myapplication.RecycleViewList;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.FavouriteActivity;
import com.example.myapplication.Json.PojoJson;
import com.example.myapplication.R;

import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class JsonDataMenu extends AppCompatActivity {

    Handler h=new Handler();
     Runnable runnable;
    ArrayList<PojoJson> arrayList= new ArrayList<PojoJson>();
 //   boolean vollyresponsecheck=false;
    public RequestQueue requestQueue;
    ProgressDialog progress;
    String rp="";
    JSONArray jsonArray;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_data_menu);

        changeStatusBarColor("#FFFFFF");
        status_icon_color();
        idSetter();
        progress=new ProgressDialog(this);
        progress.setTitle("Loading..");
        progress.show();
        requestQueue= Volley.newRequestQueue(this);

        parseJson();


    }

//    private void actionbarhider() {
//        getSupportActionBar().hide();
//    }

    private void parseJson() {
        String url="https://api.myjson.com/bins/1dqeqd";
                JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("json",response.toString());
                try {
                     jsonArray=response.getJSONArray("jsondata");
                     JSONObject tmp=jsonArray.getJSONObject(0);
                     rp=tmp.getString("description");

                    for(int i=0;i<jsonArray.length();++i)
                    {
                        JSONObject obj=jsonArray.getJSONObject(i);
                        PojoJson p=new PojoJson();
                        p.setImageUrl(obj.getString("imageUrl"));
                        p.setDescription(obj.getString("description"));
                        p.setName(obj.getString("name"));
                        p.setPlantId(obj.getString("plantId"));
                        p.setGrowZoneNumber(obj.getInt("growZoneNumber"));
                        p.setWateringInterval(obj.getInt("wateringInterval"));
                        arrayList.add(p);
                        Log.d("abcd",arrayList.get(i).getGrowZoneNumber()+"");
                    }
                    dly();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("json",error.getMessage());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void dly() {


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                progress.cancel();

                setRecycleView();


            }
        }, 1000);
    }


    private void setRecycleView() {
        Log.d("array1",arrayList.get(3).getImageUrl()+"");
        Log.d("array1",arrayList.get(3).getDescription());
        layoutManager =new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter=new AdapterSubMenu(this,arrayList);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    private void idSetter() {
        recyclerView=findViewById(R.id.rv_sub_list);

    }


//
//    private void chk(boolean vollyresponsecheck) {
//        h.removeCallbacks(runnable);
//    }
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
                decor.setSystemUiVisibility(0);
            }
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_view_fav:
                Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(JsonDataMenu.this, FavouriteActivity.class);
                startActivity(i);

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


}
