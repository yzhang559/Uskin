package com.example.jenny.skincare;
import android.graphics.Color;

import java.io.Serializable;


/**
 * Created by Jenny on 14/7/2016.
 */
public class Record implements Serializable {

    private int id;
    public int color;
    public String time;
    public String improve;


    public Record(int id, int color, String time, String improve) {
        this.id = id;
        this.color = color;
        this.time = time;
        this.improve = improve;
    }


    public int getId() {
        return id;
    }

    public int getColor() {
        return color;
    }

    public String getTime(){ return time;
    }

    public String getImprove(){
        return improve;}

    //help function for white compare
    public double getY(){
        int r=Color.red(this.color);
        int g=Color.green(this.color);
        int b=Color.blue(this.color);
        double Y= 0.2126*r + 0.7152*g + 0.0722*b;
        return Y;
    }

    public void setColor(int color){
        this.color=color;

    }

    public void setTime(String time){
        this.time=time;
    }

    public void setImprove(String improve){
        this.improve=improve;
    }

}