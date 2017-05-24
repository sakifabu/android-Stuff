package com.example.sakif.collegenetworkappabu;

/**
 * Created by sakif on 5/17/2017.
 */

public class Profile {
    int _pid;
    String _pname;
    String name;
    String Password;
    String Email;
    String Phone;
    public Profile(){}
    public int getPid(){
        return this._pid;
    }
    public String getPname(){
        return this._pname;
    }
    public String getName(){
        return this.name;
    }
    public String getPassword(){
        return this.Password;
    }
    public String getEmail(){
        return this.Email;
    }
    public String getPhone(){
        return this.Phone;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setPassword(String name){
        this.Password = name;
    }
    public void setEmail(String name){
        this.Email = name;
    }
    public void setPhone(String name){
        this.Phone = name;
    }
}
