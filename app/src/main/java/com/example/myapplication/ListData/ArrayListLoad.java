package com.example.myapplication.ListData;

public class ArrayListLoad {

   public String name;
    public String email;
    public String password;
    public  String no;
    public ArrayListLoad(String name, String email, String password, String no) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }
}
