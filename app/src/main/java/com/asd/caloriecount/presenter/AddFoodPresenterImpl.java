package com.asd.caloriecount.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.asd.caloriecount.model.Food;
import com.asd.caloriecount.model.IFoodView;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Name name on 2017/2/14.
 */
public class AddFoodPresenterImpl extends Handler implements IAddFoodPresenter {
    private static final int GETFOOD_SUC = 1;
    private static final int GETFOOD_ERROR = 2;
    private IFoodView foodView;

    public AddFoodPresenterImpl(IFoodView foodView) {
        this.foodView = foodView;
    }

    @Override
    public void getFoodList(Context context) {

        Bmob.initialize(context, "2794ef29fbc239dfc44b9cc0fa001900");

        BmobQuery<Food> bmobQuery = new BmobQuery<>();
        //bmobQuery.setLimit(50);
        bmobQuery.findObjects(new FindListener<Food>() {
            @Override
            public void done(List<Food> list, BmobException e) {
                if (list != null) {
                    Message msg = new Message();
                    msg.what = GETFOOD_SUC;
                    msg.obj = list;
                    sendMessage(msg);

                } else {
                    Message msg = new Message();
                    msg.what = GETFOOD_ERROR;
                    sendMessage(msg);
                }
            }
        });

    }

    @Override
    public void getFoodListByName(Context context, String name) {
        Bmob.initialize(context, "2794ef29fbc239dfc44b9cc0fa001900");

        BmobQuery<Food> bmobQuery = new BmobQuery<>();
        //bmobQuery.setLimit(50);
        bmobQuery.addWhereEqualTo("fName",name);
        bmobQuery.findObjects(new FindListener<Food>() {
            @Override
            public void done(List<Food> list, BmobException e) {
                if (list != null) {
                    Message msg = new Message();
                    msg.what = GETFOOD_SUC;
                    msg.obj = list;
                    sendMessage(msg);

                } else {
                    Message msg = new Message();
                    msg.what = GETFOOD_ERROR;
                    sendMessage(msg);
                }
            }
        });
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case GETFOOD_SUC:
                foodView.getFoodListSuc((List<Food>) msg.obj);
                break;
            case GETFOOD_ERROR:
                foodView.getFoodListError("获取失败");
                break;
        }
    }
}
