package com.example.myapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Registration extends AppCompatActivity {
TextView login,logo;
Button register;
EditText username,pass,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        hideActiionBar();
        idsetter();
        listner();
        fontSetter();
    }

    private void fontSetter() {
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/cursive.ttf");
        logo.setTypeface(custom_font);
    }
    private void listner() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Registration.this,MainActivity.class);
                startActivity(i);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().length()<=3)
                {
                    username.setError("username should be greater then 3");
                }
                if(pass.getText().toString().length()<6)
                {
                    pass.setError("password should be greater then 6");
                }
                if(!isEmailValid(email.getText().toString()))
                {
                    email.setError("please enter email properly");
                }
            }
        });
    }

    private void idsetter() {
        login=findViewById(R.id.TextView_login);
        logo=findViewById(R.id.textview_register_logo);
        register=findViewById(R.id.button_reg);
        username=findViewById(R.id.edittext_register_username);
        pass=findViewById(R.id.edittext_register_password);
        email=findViewById(R.id.edittext_register_email);
    }

    private void hideActiionBar() {
        getSupportActionBar().hide();
    }
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
