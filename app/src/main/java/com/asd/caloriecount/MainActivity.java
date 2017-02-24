package com.asd.caloriecount;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.asd.caloriecount.adapter.RecordAdapter;
import com.asd.caloriecount.dao.FoodDao;
import com.asd.caloriecount.model.Food;
import com.asd.caloriecount.util.MyItemDecoration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int TYPE_BREAKFAST = 1;
    private static final int TYPE_LUNCH = 2;
    private static final int TYPE_DINNER = 3;
    private float eaten_breakfast;
    private float eaten_lunch;
    private float eaten_dinner;
    private int total_calorie = 1632;
    private float breakfast_protein;
    private float breakfast_fat;
    private float breakfast_carbohydrate;
    private float lunch_protein;
    private float lunch_fat;
    private float lunch_catbohydrate;
    private float dinner_protein;
    private float dinner_fat;
    private float dinner_carbohydrate;
    private RecyclerView mRV_breakfast;
    private RecyclerView mRV_lunch;
    private RecyclerView mRV_dinner;
    private List<Food> list_breakfast = new ArrayList<>();
    private List<Food> list_lunch = new ArrayList<>();
    private List<Food> list_dinner = new ArrayList<>();
    private RecordAdapter adapter_breakfast;
    private RecordAdapter adapter_lunch;
    private RecordAdapter adapter_dinner;
    private FoodDao dao;
    private TextView tv_calorie_eat;
    private TextView tv_calorie_left;
    private TextView tv_breakfast_calorie;
    private TextView tv_lunch_calorie;
    private TextView tv_dinner_calorie;

    private TextView tv_breakfast_protein;
    private TextView tv_breakfast_fat;
    private TextView tv_breakfast_carbohydrate;
    private TextView tv_lunch_protein;
    private TextView tv_lunch_fat;
    private TextView tv_lunch_carbohydrate;
    private TextView tv_dinner_protein;
    private TextView tv_dinner_fat;
    private TextView tv_dinner_carbohydrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar bar = getSupportActionBar();
        bar.hide();
        dao = new FoodDao(getApplicationContext());
        initView();


        Button btn_52week = (Button) findViewById(R.id.btn_52weeks);
        btn_52week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WeeksPlanActivity.class);
                startActivity(intent);
            }
        });
        TextView tv_add_breakfast = (TextView) findViewById(R.id.tv_add_breakfast);
        tv_add_breakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddFoodActivity.class);
                intent.putExtra("meals", TYPE_BREAKFAST);
                startActivity(intent);
            }
        });
        TextView tv_add_lunch = (TextView) findViewById(R.id.tv_add_lunch);
        tv_add_lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddFoodActivity.class);
                intent.putExtra("meals", TYPE_LUNCH);
                startActivity(intent);
            }
        });
        TextView tv_add_dinner = (TextView) findViewById(R.id.tv_add_dinner);
        tv_add_dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddFoodActivity.class);
                intent.putExtra("meals", TYPE_DINNER);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        List<Food> list = (List<Food>) getIntent().getSerializableExtra("add_list");
        int meals_type = getIntent().getIntExtra("meals", 0);
        if (meals_type != 0) {
            addMeals(list, meals_type);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
        setData();
    }

    private void initView() {
        tv_calorie_eat = (TextView) findViewById(R.id.tv_calorie_eat);
        tv_calorie_left = (TextView) findViewById(R.id.tv_calorie_left);
        tv_breakfast_calorie = (TextView) findViewById(R.id.tv_breakfast_calorie);
        tv_lunch_calorie = (TextView) findViewById(R.id.tv_lunch_calorie);
        tv_dinner_calorie = (TextView) findViewById(R.id.tv_dinner_calorie);
        tv_breakfast_protein = (TextView) findViewById(R.id.tv_breakfast_protein);
        tv_breakfast_fat = (TextView) findViewById(R.id.tv_breakfast_fat);
        tv_breakfast_carbohydrate = (TextView) findViewById(R.id.tv_breakfast_carbohydrate);
        tv_lunch_protein = (TextView) findViewById(R.id.tv_lunch_protein);
        tv_lunch_fat = (TextView) findViewById(R.id.tv_lunch_fat);
        tv_lunch_carbohydrate = (TextView) findViewById(R.id.tv_lunch_carbohydrate);
        tv_dinner_protein = (TextView) findViewById(R.id.tv_dinner_protein);
        tv_dinner_fat = (TextView) findViewById(R.id.tv_dinner_fat);
        tv_dinner_carbohydrate = (TextView) findViewById(R.id.tv_dinner_carbohydrate);
        mRV_breakfast = (RecyclerView) findViewById(R.id.rv_breakfast);
        mRV_lunch = (RecyclerView) findViewById(R.id.rv_lunch);
        mRV_dinner = (RecyclerView) findViewById(R.id.rv_dinner);
        mRV_breakfast.setLayoutManager(new LinearLayoutManager(this));
        mRV_lunch.setLayoutManager(new LinearLayoutManager(this));
        mRV_dinner.setLayoutManager(new LinearLayoutManager(this));
        mRV_breakfast.addItemDecoration(new MyItemDecoration());
        mRV_lunch.addItemDecoration(new MyItemDecoration());
        mRV_dinner.addItemDecoration(new MyItemDecoration());
    }

    public void initData() {
        list_breakfast = getListData(TYPE_BREAKFAST);
        list_lunch = getListData(TYPE_LUNCH);
        list_dinner = getListData(TYPE_DINNER);
        eaten_breakfast = getEatenCalorie(list_breakfast);
        breakfast_protein = getEatenProtein(list_breakfast);
        breakfast_fat = getEatenFat(list_breakfast);
        breakfast_carbohydrate = getEatenCarbohydrate(list_breakfast);
        eaten_lunch = getEatenCalorie(list_lunch);
        lunch_protein = getEatenProtein(list_lunch);
        lunch_fat = getEatenFat(list_lunch);
        lunch_catbohydrate = getEatenCarbohydrate(list_lunch);
        eaten_dinner = getEatenCalorie(list_dinner);
        dinner_protein = getEatenProtein(list_dinner);
        dinner_fat = getEatenProtein(list_dinner);
        dinner_carbohydrate = getEatenCarbohydrate(list_dinner);
    }

    public void setData() {
        adapter_breakfast = new RecordAdapter(this, list_breakfast);
        adapter_lunch = new RecordAdapter(this, list_lunch);
        adapter_dinner = new RecordAdapter(this, list_dinner);
        adapter_breakfast.setmOnItemClickListener(new RecordAdapter.OnItemClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                showMyDialog(list_breakfast.get(position).getfId());
            }
        });
        adapter_lunch.setmOnItemClickListener(new RecordAdapter.OnItemClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                showMyDialog(list_lunch.get(position).getfId());


            }
        });
        adapter_dinner.setmOnItemClickListener(new RecordAdapter.OnItemClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                showMyDialog(list_dinner.get(position).getfId());
            }
        });

        mRV_breakfast.setAdapter(adapter_breakfast);
        mRV_lunch.setAdapter(adapter_lunch);
        mRV_dinner.setAdapter(adapter_dinner);
        tv_calorie_eat.setText(String.valueOf(eaten_breakfast + eaten_lunch + eaten_dinner));
        tv_calorie_left.setText(String.valueOf(total_calorie - eaten_breakfast - eaten_lunch - eaten_dinner));
        tv_breakfast_calorie.setText(String.valueOf(eaten_breakfast));
        tv_breakfast_protein.setText(String.valueOf(breakfast_protein));
        tv_breakfast_fat.setText(String.valueOf(breakfast_fat));
        tv_breakfast_carbohydrate.setText(String.valueOf(breakfast_carbohydrate));
        tv_lunch_calorie.setText(String.valueOf(eaten_lunch));
        tv_lunch_protein.setText(String.valueOf(lunch_protein));
        tv_lunch_fat.setText(String.valueOf(lunch_fat));
        tv_lunch_carbohydrate.setText(String.valueOf(lunch_catbohydrate));
        tv_dinner_calorie.setText(String.valueOf(eaten_dinner));
        tv_dinner_protein.setText(String.valueOf(dinner_protein));
        tv_dinner_fat.setText(String.valueOf(dinner_fat));
        tv_dinner_carbohydrate.setText(String.valueOf(dinner_carbohydrate));

    }

    public List<Food> getListData(int type) {
        List<Food> list = dao.getFoodByDate(getCurrentDate(), type);
        return list;
    }

    public void addMeals(List<Food> list, int type) {
        String dateStr = getCurrentDate();
        for (Food food :
                list) {
            dao.addFood(food, dateStr, type);
        }
    }

    public String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new java.util.Date();
        return sdf.format(date);
    }

    public float getEatenCalorie(List<Food> list) {
        float eaten = 0;
        for (Food food :
                list) {
            float weight = Float.parseFloat(food.getWeight());
            float calorie = Float.parseFloat(food.getfCalorie());
            eaten += calorie * weight / 100;
        }
        return eaten;
    }

    public float getEatenProtein(List<Food> list) {
        float eaten = 0;
        for (Food food :
                list) {
            float weight = Float.parseFloat(food.getWeight());
            float protein = Float.parseFloat(food.getfProtein());
            eaten += protein * weight / 100;
        }
        return eaten;
    }

    public float getEatenFat(List<Food> list) {
        float eaten = 0;
        for (Food food :
                list) {
            float weight = Float.parseFloat(food.getWeight());
            float fat = Float.parseFloat(food.getfFat());
            eaten += fat * weight / 100;
        }
        return eaten;
    }

    public float getEatenCarbohydrate(List<Food> list) {
        float eaten = 0;
        for (Food food :
                list) {
            float weight = Float.parseFloat(food.getWeight());
            float carbohydrate = Float.parseFloat(food.getfCarbohydrate());
            eaten += carbohydrate * weight / 100;
        }
        return eaten;
    }
    public void showMyDialog(final int id){
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("确定删除这笔记录？");
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dao.delFood(id);
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();
    }


}
