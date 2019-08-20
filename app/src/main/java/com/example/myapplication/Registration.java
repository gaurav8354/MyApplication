package com.example.myapplication;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Database.SqlHelper;

import java.util.ArrayList;

public class Registration extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<Cursor>{
TextView tv_login, tv_logo;
Button bt_register;
boolean firstTimeLoaded=false;

EditText et_username, et_pass, et_email;
AutoCompleteTextView tv_autocomplete;
ArrayList<String> apr=new ArrayList<>();
ArrayList<String> arrayListOfContact=new ArrayList<>();
    ContentResolver contentResolver;
    String[] mColoumProjection=new String[]{
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        askForContactPermission();
        abc();
        apr.add("Select number");


        hideActiionBar();
        idsetter();
        ArrayAdapter<String> tvAutoCompleteArrayAdapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,apr);
//        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        tv_autocomplete.setAdapter(tvAutoCompleteArrayAdapter);
        listner();
        fontSetter();

    }

    private void fontSetter() {
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/cursive.ttf");
        tv_logo.setTypeface(custom_font);
    }

    private void listner() {
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Registration.this, Login.class);
                startActivity(i);
            }
        });
        bt_register.setOnClickListener(new View.OnClickListener() {
            boolean check=true;
            @Override
            public void onClick(View view) {
                if(et_username.getText().toString().length()<=3)
                {
                    et_username.setError("et_username should be greater then 3");
                    check=false;
                }
                if(et_pass.getText().toString().length()<6)
                {
                    et_pass.setError("password should be greater then 6");
                    check=false;
                }
                if(!isEmailValid(et_email.getText().toString()))
                {
                    et_email.setError("please enter et_email properly");
                    check=false;
                }
                if(check)
                {
                    if(insertSql(et_email.getText().toString(), et_pass.getText().toString(), et_username.getText().toString())){
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
        tv_login =findViewById(R.id.TextView_login);
        tv_logo =findViewById(R.id.textview_register_logo);
        bt_register =findViewById(R.id.button_reg);
        et_username =findViewById(R.id.edittext_register_username);
        et_pass =findViewById(R.id.edittext_register_password);
        et_email =findViewById(R.id.edittext_register_email);
        tv_autocomplete=findViewById(R.id.et_autocomplete);

    }

    public void abc() {
        if(!firstTimeLoaded)
        {

            getLoaderManager().initLoader(0,null, this);
        }
        else
        {
            getLoaderManager().restartLoader(0,null,  this);
        }
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
    public void askForContactPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_CONTACTS)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Contacts access needed");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setMessage("please confirm Contacts access");//TODO put real question
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            requestPermissions(
                                    new String[]
                                            {Manifest.permission.READ_CONTACTS}
                                    , 1337);
                        }
                    });
                    builder.show();

                } else {


                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_CONTACTS},
                            1337);
                }
            }else{
            }
        }
        else{
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1337: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(this, "No Permissions ", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Log.d("1234","create loader");
        firstTimeLoaded=true;
        return new CursorLoader(this, ContactsContract.CommonDataKinds.Phone.CONTENT_URI,mColoumProjection,null,null,
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY);
    }
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        String str="";
        if(cursor!=null&& cursor.getCount()>0) {
            while (cursor.moveToNext())
            {
                String phoneNumber = null;
               // String name=cursor.getString(0);
                //str=str+cursor.getString(0)+"\n"+cursor.getString(1);
                String tp=cursor.getString(1).replaceAll("\\W", "");
                String str1=cursor.getString(0)+" "+tp;
                arrayListOfContact.add(str1);
            }
        }
        ArrayAdapter<String> spinnerArrayAdapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,arrayListOfContact);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        tv_autocomplete.setAdapter(spinnerArrayAdapter);
            tv_autocomplete.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

//                    tv_autocomplete.setText(number);

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            tv_autocomplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.d("1234"," no");
                    String []temp=tv_autocomplete.getText().toString().split(" ");
                    tv_autocomplete.setText(temp[temp.length-1]);
//                    Log.d("1234",);
                }
            });


        Log.d("1234","loaded");
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        loader.reset();
//        i=0;
    }
}
