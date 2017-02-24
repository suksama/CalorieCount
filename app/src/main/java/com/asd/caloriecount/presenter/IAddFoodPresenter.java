package com.asd.caloriecount.presenter;

import android.content.Context;

/**
 * Created by Name name on 2017/2/14.
 */
public interface IAddFoodPresenter {
    void getFoodList(Context context);
    void getFoodListByName(Context context, String name);
}
