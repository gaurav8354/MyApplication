package com.example.myapplication.ListData;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.Database.SqlHelper;
import com.example.myapplication.R;

import java.util.ArrayList;

public class ListDataView extends AppCompatActivity {
TextView tv;
ArrayList<ArrayListLoad> ar=new ArrayList<ArrayListLoad>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data_view);
        idsetter();
        loaddata();
    }

    private void idsetter() {
        tv=findViewById(R.id.tv_list_data);
    }

    private void loaddata() {
        SqlHelper db=new SqlHelper(this);
        Cursor cv=db.showAllData();
        StringBuffer sf=new StringBuffer();


        while(cv.moveToNext())
        {
            ar.add(new ArrayListLoad(cv.getString(0),cv.getString(2),cv.getString(1),cv.getString(3)));

            sf.append(cv.getString(0)).append(":");
            sf.append(cv.getString(1)).append(":");
            sf.append(cv.getString(2)).append(":");
            sf.append(cv.getString(3)).append("/n");
        }
        if(!sf.toString().isEmpty())
        {
            tv.setText(sf.toString());
        }
    }
}
