package com.example.myapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
Button login;
String uname,password;
EditText user,pass;
TextView register,logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //gaurav
        //saurabh
        idsetter();
        data();
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

    private void data() {
        uname="gaurav";
        password="gaurav";
    }

    private void idsetter() {
        login=findViewById(R.id.button_login);
        user=findViewById(R.id.edittext_username);
        pass=findViewById(R.id.edittext_password);
        register=findViewById(R.id.TextView_register);
        logo=findViewById(R.id.textview_logo);
    }

    private void listner() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i=new Intent(MainActivity.this);
                if(user.getText().toString().equals(uname))
                {
                    if(pass.getText().toString().equals(password))
                    {
                        Intent i=new Intent(MainActivity.this,ListDataView.class);
                        startActivity(i);
                    }
                    else
                    {
                        pass.setError("enter correct password");
                    }
                }
                else
                {
                    user.setError("please enter correct username");
                }

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,Registration.class);
                startActivity(i);
            }
        });
    }
}
