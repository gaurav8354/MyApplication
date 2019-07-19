package com.example.myapplication.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;


public class SqlHelper extends SQLiteOpenHelper {
    public static final String DB_NAME="Test.db";
    public static final String DB_COLUMN="Register";
    public static final String DB_NAME_NAME="Name";
    public static final String DB_EMAIL="Email";
    public static final String DB_PASSWORD="Password";


    public SqlHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
        SQLiteDatabase db=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
String sql_create_table= "CREATE TABLE \"Register\" ( \"Name\" TEXT, \"Password\" TEXT, \"Email\"\tTEXT, \"No\" INTEGER PRIMARY KEY AUTOINCREMENT);";
sqLiteDatabase.execSQL(sql_create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+DB_COLUMN);
        //String sql_create_table= "CREATE TABLE \"Register\" (\"Name\"TEXT,\"Password\" TEXT,\"Email\" TEXT PRIMARY KEY AUTOINCREMENT);";
        String sql_create_table= "CREATE TABLE \"Register\" ( \"Name\" TEXT, \"Password\" TEXT, \"Email\"\tTEXT, \"No\" INTEGER PRIMARY KEY AUTOINCREMENT);";
          sqLiteDatabase.execSQL(sql_create_table);
    }
   public boolean insertData(String name,String email,String password,int no){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(DB_NAME_NAME,name);
        cv.put(DB_EMAIL,email);
        cv.put(DB_PASSWORD,password);
        long result=db.insert(DB_COLUMN,null,cv);
        if(result==-1)
        {
            return  false;
        }
        else
        {
            return true;
        }
    }
    public Cursor showAllData(){
        SQLiteDatabase db=this.getReadableDatabase();
        String sql="select * from "+DB_COLUMN;
        Cursor res=db.rawQuery(sql,null);
        return res;

    }
}
