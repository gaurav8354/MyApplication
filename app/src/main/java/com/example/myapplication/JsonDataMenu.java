package com.example.myapplication;

import android.app.ProgressDialog;
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
import com.example.myapplication.Json.JsonData;
import com.example.myapplication.Json.PojoJson;
import com.example.myapplication.R;
import com.example.myapplication.RecycleViewSubList.AdapterSubMenu;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.List;


public class JsonDataMenu extends AppCompatActivity {

    Handler h=new Handler();
     Runnable runnable;
    ArrayList<PojoJson> arrayList= new ArrayList<PojoJson>();
    boolean vollyresponsecheck=false;
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
        actionbarhider();
        changeStatusBarColor("#FFFFFF");
        status_icon_color();
        idSetter();
        progress=new ProgressDialog(this);
        progress.setTitle("Loading..");
        progress.show();
        requestQueue= Volley.newRequestQueue(this);

        parseJson();


    }

    private void actionbarhider() {
        getSupportActionBar().hide();
    }

    private void parseJson() {
        String url="https://api.myjson.com/bins/1dqeqd";
                JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("jsonzzzz",response.toString());



              //  Toast.makeText(JsonDataMenu.this, "zz", Toast.LENGTH_SHORT).show();

                try {
                     jsonArray=response.getJSONArray("jsondata");
                     JSONObject tmp=jsonArray.getJSONObject(0);
                     rp=tmp.getString("description");

                   // Log.d("array",jsonArray.length()+"");
                    for(int i=0;i<jsonArray.length();++i)
                    {

                        JSONObject obj=jsonArray.getJSONObject(i);
                       // Log.d("abcd",obj.getString("plantId"));
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
                   // Log.d("jsondata",obj.getString("plantId"));
                   // test();
                    dly();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("1111json",error.getMessage());
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

                test();


            }
        }, 1000);
    }


    private void test() {
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



    private void chk(boolean vollyresponsecheck) {
        h.removeCallbacks(runnable);
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
