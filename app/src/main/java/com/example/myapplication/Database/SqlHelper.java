package com.example.myapplication.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

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
        String create_table1="CREATE TABLE \"favourite\" (\n" +
                "\t\"plantId\"\tTEXT,\n" +
                "\t\"name\"\tTEXT,\n" +
                "\t\"description\"\tTEXT,\n" +
                "\t\"imageUrl\"\tTEXT,\n" +
                "\t\"growZoneNumber\"\tINTEGER,\n" +
                "\t\"wateringInterval\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"plantId\")\n" +
                ");";
        sqLiteDatabase.execSQL(create_table1);
sqLiteDatabase.execSQL(sql_create_table);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+DB_COLUMN);
sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+"favourite");
        //String sql_create_table= "CREATE TABLE \"Register\" (\"Name\"TEXT,\"Password\" TEXT,\"Email\" TEXT PRIMARY KEY AUTOINCREMENT);";
        String sql_create_table= "CREATE TABLE \"Register\" ( \"Name\" TEXT, \"Password\" TEXT, \"Email\"\tTEXT, \"No\" INTEGER PRIMARY KEY AUTOINCREMENT);";
        String create_table1="CREATE TABLE \"favourite\" (\n" +
                "\t\"plantId\"\tTEXT,\n" +
                "\t\"name\"\tTEXT,\n" +
                "\t\"description\"\tTEXT,\n" +
                "\t\"imageUrl\"\tTEXT,\n" +
                "\t\"growZoneNumber\"\tINTEGER,\n" +
                "\t\"wateringInterval\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"plantId\")\n" +
                ");";
        sqLiteDatabase.execSQL(sql_create_table);
        sqLiteDatabase.execSQL(create_table1);
    }
   public boolean insertData(String name,String email,String password){
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
    public  boolean insertFavData(String plantid,String name,String disc,String imageurl,int grownum,int wateringnum)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("plantId",plantid);
        cv.put("name",name);
        cv.put("description",disc);
        cv.put("imageUrl",imageurl);
        cv.put("growZoneNumber",grownum);
        cv.put("wateringInterval",wateringnum);
        long result=db.insert("favourite",null,cv);
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
    public Cursor showAllFavData(){

        SQLiteDatabase db=this.getReadableDatabase();
        String sql="select * from "+"favourite";
        Cursor res=db.rawQuery(sql,null);
        return res;

    }
    public void removeFromFav(String pid)
    {

        SQLiteDatabase db=this.getWritableDatabase();
        String query_del="DELETE from favourite where (plantId ='"+pid+"')";
        //db.delete("favourite","")
       db.execSQL(query_del);
        Log.d("1234",query_del+" Removed from fav");
    }
    public boolean favExist(String pid)
    {

        SQLiteDatabase db=this.getReadableDatabase();
        String sqlQuery="select * from favourite where (plantId ='"+pid+"')";
        Cursor cus=db.rawQuery(sqlQuery,null);
        Log.d("1234",pid+" count = "+cus.getCount()+"");
        if(cus.getCount()==0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}
