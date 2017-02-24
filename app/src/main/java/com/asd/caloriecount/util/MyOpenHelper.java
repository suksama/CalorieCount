package com.asd.caloriecount.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Name name on 2017/2/22.
 */
public class MyOpenHelper extends SQLiteOpenHelper{
    public static final String DBNAME = "calorie.db";
    public static final int DBVERSION = 2;
    private Context context;

    public MyOpenHelper(Context context) {
        super(context, DBNAME, null, DBVERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists "
                + "calorie "
                +"(cId integer primary key autoincrement, "
                + "cName varchar(255) not null, cCalorie varchar(255) not null, cWeight varchar(255) not null, "
                + "cProtein varchar(255) not null, cFat varchar(255) not null, cCarbohydrate varchar(255) not null, "
                + "cDate varchar(255) not null,cType integer not null);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String sql = "create table if not exists "
                + "calorie "
                +"(cId integer primary key autoincrement, "
                + "cName varchar(255) not null, cCalorie varchar(255) not null, cWeight varchar(255) not null, "
                + "cProtein varchar(255) not null, cFat varchar(255) not null, cCarbohydrate varchar(255) not null, "
                + "cDate varchar(255) not null);";
        db.execSQL(sql);
    }
}
