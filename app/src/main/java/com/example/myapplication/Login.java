package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Database.SqlHelper;
import com.example.myapplication.ListDataForUserLogin.ArrayListLoad;
import com.example.myapplication.RecycleViewList.JsonDataMenu;

import java.util.ArrayList;

public class Login extends AppCompatActivity {
Button btLogin;
EditText et_user, et_pass;
TextView register,logo,skip;
    ArrayList<ArrayListLoad> arrayList =new ArrayList<ArrayListLoad>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        idsetter();
        listner();
        fontSetter();
        hideActiionBar();
    }
    private void hideActiionBar() {
        getSupportActionBar().hide();
    }
    private void fontSetter() {
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/cursive.ttf");
        logo.setTypeface(custom_font);
    }
    public boolean data(String email,String password) {
        StringBuffer buffer=new StringBuffer();
        boolean check=false;
        SqlHelper db=new SqlHelper(this);
        Cursor cursor=db.showAllData();
        if(cursor.getCount()==0)
        {
            Toast.makeText(this, "enter valid Email/password ", Toast.LENGTH_SHORT).show();
        }
        else
        {
           // String array[]=new String[cursor.getCount()];
            int i=0;
            while(cursor.moveToNext())
            {
                arrayList.add(new ArrayListLoad(cursor.getString(0),cursor.getString(2),cursor.getString(1),cursor.getString(3)));
                String str="";
//                str=str+cursor.getString(0)+":"+cursor.getString(1)+":"+cursor.getString(2);
               // array[i]=str;

            }

            for(int j = 0; j< arrayList.size(); ++j)
            {
                if(arrayList.get(j).email.equals(email)&& arrayList.get(j).password.equals(password))
                {
                    check=true;
                    break;
                }
                Log.d("1234", arrayList.get(j).name+":"+ arrayList.get(j).email+":"+ arrayList.get(j).password+":"+ arrayList.get(j).no);
            }
        }
        return check;
    }
    private void idsetter() {
        btLogin =findViewById(R.id.button_login);
        et_user =findViewById(R.id.edittext_username);
        et_pass =findViewById(R.id.edittext_password);
        register=findViewById(R.id.TextView_register);
        logo=findViewById(R.id.textview_logo);
        skip=findViewById(R.id.tv_skip_signin);
    }

    private void listner() {
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i=new Intent(Login.this);
                if(!et_user.getText().toString().isEmpty())
                {
                    if(!et_pass.getText().toString().isEmpty())
                    {
                       if( data(et_user.getText().toString(), et_pass.getText().toString()))
                       {
//                           Intent i=new Intent(Login.this, ListDataView.class);
//                           startActivity(i);

                           Intent i=new Intent(Login.this, JsonDataMenu.class);
                           startActivity(i);

                       }
                       else{
                           Toast.makeText(Login.this, "enter valid Email/password", Toast.LENGTH_SHORT).show();
                       }
                    }
                    else
                    {
                        et_pass.setError("please enter password");
                    }
                }
                else
                {
                    et_user.setError("please enter et_email");
                }

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Login.this,Registration.class);
                startActivity(i);
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Login.this,JsonDataMenu.class);
                startActivity(i);
            }
        });
    }
}
