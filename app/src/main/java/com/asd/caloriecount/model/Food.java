package com.asd.caloriecount.model;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by Name name on 2017/2/7.
 */
public class Food extends BmobObject implements Serializable{
    private String fName;
    private String fCalorie;
    private String fProtein;
    private String fFat;
    private String fCarbohydrate;
    private String weight;
    private int fId;

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getfCalorie() {
        return fCalorie;
    }

    public void setfCalorie(String fCalorie) {
        this.fCalorie = fCalorie;
    }

    public String getfProtein() {
        return fProtein;
    }

    public void setfProtein(String fProtein) {
        this.fProtein = fProtein;
    }

    public String getfFat() {
        return fFat;
    }

    public void setfFat(String fFat) {
        this.fFat = fFat;
    }

    public String getfCarbohydrate() {
        return fCarbohydrate;
    }

    public void setfCarbohydrate(String fCarbohydrate) {
        this.fCarbohydrate = fCarbohydrate;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public int getfId() {
        return fId;
    }

    public void setfId(int fId) {
        this.fId = fId;
    }

    public Food(int fId,String fName, String fCalorie, String weight, String fProtein, String fFat, String fCarbohydrate) {
        this.fId = fId;
        this.fName = fName;
        this.fCalorie = fCalorie;
        this.weight = weight;
        this.fProtein = fProtein;
        this.fFat = fFat;
        this.fCarbohydrate = fCarbohydrate;
    }
}
