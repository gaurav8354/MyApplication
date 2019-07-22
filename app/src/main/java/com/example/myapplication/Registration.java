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

import com.example.myapplication.Database.SqlHelper;

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
                Intent i=new Intent(Registration.this, Login.class);
                startActivity(i);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            boolean check=true;
            @Override
            public void onClick(View view) {
                if(username.getText().toString().length()<=3)
                {
                    username.setError("username should be greater then 3");
                    check=false;
                }
                if(pass.getText().toString().length()<6)
                {
                    pass.setError("password should be greater then 6");
                    check=false;
                }
                if(!isEmailValid(email.getText().toString()))
                {
                    email.setError("please enter email properly");
                    check=false;
                }
                if(check)
                {
                    if(insertSql(email.getText().toString(),pass.getText().toString(),username.getText().toString())){
                        Toast.makeText(Registration.this, "Registration Done", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(Registration.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    check=true;
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
    private boolean insertSql(String email,String pass,String name){

        SqlHelper database=new SqlHelper(this);
        return  database.insertData(name,email,pass);
    }
}
