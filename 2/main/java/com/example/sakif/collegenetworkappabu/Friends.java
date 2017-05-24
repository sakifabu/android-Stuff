package com.example.sakif.collegenetworkappabu;

/**
 * Created by sakif on 5/17/2017.
 */

public class Friends {
    int fid;
    String _fname;
    public Friends(){}
    public int getNID(){
        return this.fid;
    }
    public void setNID(int fid){
        this.fid = fid;
    }
    public String getfName(){
        return this._fname;
    }
    public void setNName(String Name){
        this._fname = Name;
    }
}
