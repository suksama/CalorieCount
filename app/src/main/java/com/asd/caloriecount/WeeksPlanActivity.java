package com.asd.caloriecount;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.asd.caloriecount.util.MyItemDecoration;
import com.asd.caloriecount.adapter.WeeksPlanAdapter;
import com.asd.caloriecount.model.WeeksPlan;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Name name on 2017/2/8.
 */
public class WeeksPlanActivity extends AppCompatActivity {
    private RecyclerView rv_weeksplan;
    private WeeksPlanAdapter mAdapter;
    private TextView tv_savedmoney;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weeksplan);
        ActionBar bar = getSupportActionBar();
        bar.hide();
        tv_savedmoney = (TextView) findViewById(R.id.tv_savedmoney);
        rv_weeksplan = (RecyclerView) findViewById(R.id.rv_weeksplan);
        sharedPreferences = getSharedPreferences("saved_week", MODE_PRIVATE);
        tv_savedmoney.setText(String.valueOf(sharedPreferences.getInt("savemoney", 0)));
        rv_weeksplan.setLayoutManager(new GridLayoutManager(this, 2));
        rv_weeksplan.addItemDecoration(new MyItemDecoration());
        mAdapter = new WeeksPlanAdapter(this, initData());
        final long[] mHints = new long[3];
        mAdapter.setmOnItemClickListener(new WeeksPlanAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Toast.makeText(getApplicationContext(), "你点击了" + position, Toast.LENGTH_SHORT).show();
/*
                System.arraycopy(mHints, 1, mHints, 0, mHints.length - 1);
                mHints[mHints.length - 1] = SystemClock.uptimeMillis();
                if ( SystemClock.uptimeMillis() - mHints[0] <= 500){
                    Toast.makeText(getApplicationContext(),"点击了3次",Toast.LENGTH_SHORT).show();
                }
*/
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getApplicationContext(), "你长按了" + position, Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("week" + (position + 1), true);

                int saved = Integer.parseInt(tv_savedmoney.getText().toString());
                int total_save = saved + (position + 1) * 10;
                tv_savedmoney.setText(String.valueOf(total_save));
                view.setBackgroundResource(R.color.darker);
                view.setEnabled(false);
                editor.putInt("savemoney", total_save);
                editor.commit();
            }
        });
        rv_weeksplan.setAdapter(mAdapter);
    }

    List<WeeksPlan> initData() {
        List<WeeksPlan> list = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int year = calendar.get(Calendar.YEAR);
        int weeks = getWeekNumByYear(year);
        int current_week = calendar.get(Calendar.WEEK_OF_YEAR);

        for (int i = 1; i < weeks + 1; i++) {
            WeeksPlan weeksPlan = new WeeksPlan();
            weeksPlan.setWeekNo("第" + String.valueOf(i) + "周");
            weeksPlan.setWeekDate(getYearWeekFirstDay(year, i) + "--" + getYearWeekEndDay(year, i));
            weeksPlan.setWeekPlan(String.valueOf(i * 10));
            if (i <= current_week) {
                weeksPlan.setIsPass(true);
                boolean isSaved = sharedPreferences.getBoolean("week" + i, false);
                if (isSaved) {
                    weeksPlan.setIsSaved(true);
                } else {
                    weeksPlan.setIsSaved(false);
                }
            } else {
                weeksPlan.setIsPass(false);
            }
            list.add(weeksPlan);
        }
        return list;
    }

    public int getWeekNumByYear(int year) {

        int result = 52;//每年至少有52个周 ，最多有53个周。
        String date = getYearWeekFirstDay(year, 53);
        if (date.substring(0, 4).equals(year + "")) { //判断年度是否相符，如果相符说明有53个周。
            result = 53;
        }
        return result;
    }

    /**
     * 计算某年某周的开始日期
     *
     * @param yearNum 格式 yyyy  ，必须大于1900年度 小于9999年
     * @param weekNum 1到52或者53
     * @return 日期，格式为yyyy-MM-dd
     */
    public String getYearWeekFirstDay(int yearNum, int weekNum) {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY); //设置每周的第一天为星期一
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);//每周从周一开始
//       上面两句代码配合，才能实现，每年度的第一个周，是包含第一个星期一的那个周。
        cal.setMinimalDaysInFirstWeek(7);  //设置每周最少为7天
        cal.set(Calendar.YEAR, yearNum);
        cal.set(Calendar.WEEK_OF_YEAR, weekNum);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = df.format(cal.getTime());
        //分别取得当前日期的年、月、日
        return dateStr;
    }

    /**
     * 计算某年某周的结束日期
     *
     * @param yearNum 格式 yyyy  ，必须大于1900年度 小于9999年
     * @param weekNum 1到52或者53
     * @return 日期，格式为yyyy-MM-dd
     */
    public String getYearWeekEndDay(int yearNum, int weekNum) {

        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY); //设置每周的第一天为星期一
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);//每周从周一开始
//       上面两句代码配合，才能实现，每年度的第一个周，是包含第一个星期一的那个周。
        cal.setMinimalDaysInFirstWeek(7);  //设置每周最少为7天
        cal.set(Calendar.YEAR, yearNum);
        cal.set(Calendar.WEEK_OF_YEAR, weekNum);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = df.format(cal.getTime());
        //分别取得当前日期的年、月、日
        return dateStr;
    }
}
