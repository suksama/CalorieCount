package com.asd.caloriecount.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.asd.caloriecount.model.Food;
import com.asd.caloriecount.util.MyOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Name name on 2017/2/22.
 */
public class FoodDao {
    private MyOpenHelper helper;

    public FoodDao(Context context) {
        helper = new MyOpenHelper(context);
    }

    public void addFood(Food food,String date,int type){
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "insert into calorie(cName,cCalorie,cWeight,cProtein,cFat,cCarbohydrate,cDate,cType) values(?,?,?,?,?,?,?,?)";
        db.execSQL(sql,new String[]{food.getfName(),food.getfCalorie(),food.getWeight(),food.getfProtein(),food.getfFat(),food.getfCarbohydrate(),date, String.valueOf(type)});
    }
    public List<Food> getFoodByDate(String date,int type){
        List<Food> data =new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select * from calorie where cDate = ? and cType = ?";
        Cursor c = db.rawQuery(sql,new String[]{date, String.valueOf(type)});
        while (c.moveToNext()){
            int id = c.getInt(0);
            String name = c.getString(1);
            String calorie = c.getString(2);
            String weight = c.getString(3);
            String protein = c.getString(4);
            String fat = c.getString(5);
            String carbohydrate = c.getString(6);
            Food food = new Food(id,name,calorie,weight,protein,fat,carbohydrate);
            data.add(food);
        }
        return data;
    }
    public void delFood(int id){
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "delete from calorie where cId = ?";
        db.execSQL(sql,new String[]{String.valueOf(id)});
    }

}
