package com.asd.caloriecount;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.asd.caloriecount.adapter.AddFoodAdapter;
import com.asd.caloriecount.model.Food;
import com.asd.caloriecount.model.IFoodView;
import com.asd.caloriecount.presenter.AddFoodPresenterImpl;
import com.asd.caloriecount.util.MyItemDecoration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Name name on 2017/2/14.
 */
public class AddFoodActivity extends AppCompatActivity implements IFoodView {
    private static final int TYPE_BREAKFAST = 1;
    private static final int TYPE_LUNCH = 2;
    private static final int TYPE_DINNER = 3;
    private RecyclerView rv_addfood;
    private PopupWindow pw;
    private TextView tv_add_food;
    private EditText et_add_food;
    private Button btn_add_food;
    private Button btn_add_finish;
    private TextView tv_add_num;
    List<Food> addList;
    int meals_type;
    private ProgressDialog waitingDialog;
    private AddFoodPresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfood);
        ActionBar bar = getSupportActionBar();
        bar.hide();
        TextView tv_meals = (TextView) findViewById(R.id.tv_meals);
        btn_add_finish = (Button)findViewById(R.id.btn_add_finish);
        tv_add_num = (TextView)findViewById(R.id.tv_add_num);
        rv_addfood = (RecyclerView)findViewById(R.id.rv_addfood);
        rv_addfood.setLayoutManager(new LinearLayoutManager(this));
        rv_addfood.addItemDecoration(new MyItemDecoration());
        Intent intent = getIntent();
        meals_type = intent.getIntExtra("meals",0);
        switch (meals_type){
            case TYPE_BREAKFAST:
                tv_meals.setText("添加早餐");
                break;
            case TYPE_LUNCH:
                tv_meals.setText("添加午餐");
                break;
            case TYPE_DINNER:
                tv_meals.setText("添加晚餐");
                break;
        }
        attemptToGetFoodList();
        addList = new ArrayList<>();
        btn_add_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("add_list", (Serializable) addList);
                intent.putExtra("meals",meals_type);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void attemptToGetFoodList() {
        presenter = new AddFoodPresenterImpl(this);
        presenter.getFoodList(getApplicationContext());
        showWaitingDialog();
    }

    @Override
    public void getFoodListSuc(final List<Food> foodList) {
        waitingDialog.dismiss();
        View mView = getLayoutInflater().inflate(R.layout.pop,null);
        tv_add_food = (TextView) mView.findViewById(R.id.tv_add_food);
        et_add_food = (EditText) mView.findViewById(R.id.et_add_food);
        btn_add_food = (Button) mView.findViewById(R.id.btn_add_food);
        pw = new PopupWindow(mView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
        pw.setBackgroundDrawable(getResources().getDrawable(R.color.app));
        pw.setFocusable(true);
        pw.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        AddFoodAdapter mAdapter = new AddFoodAdapter(getApplicationContext(), foodList);
        mAdapter.setmOnItemClickListener(new AddFoodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                pw.showAtLocation(AddFoodActivity.this.findViewById(R.id.et_searchfood), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                final TextView tv = (TextView) view.findViewById(R.id.tv_name);
                tv_add_food.setText(tv.getText().toString());
                btn_add_food.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String weight = et_add_food.getText().toString();
                        tv_add_num.setVisibility(View.VISIBLE);
                        tv_add_num.setText(String.valueOf(Integer.parseInt(tv_add_num.getText().toString()) + 1));
                        Food food = foodList.get(position);
                        food.setWeight(weight);
                        addList.add(food);
                        pw.dismiss();
                    }
                });

            }
        });
        rv_addfood.setAdapter(mAdapter);

    }

    @Override
    public void getFoodListError(String errorMsg) {

    }

    private void showWaitingDialog() {
        waitingDialog = new ProgressDialog(this);
        waitingDialog.setMessage("等待中...");
        waitingDialog.setIndeterminate(true);
        waitingDialog.setCancelable(false);
        waitingDialog.show();

    }

}
