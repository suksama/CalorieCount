package com.asd.caloriecount.model;

import java.util.List;

/**
 * Created by Name name on 2017/2/14.
 */
public interface IFoodView {
    void attemptToGetFoodList();
    void getFoodListSuc(List<Food> foodList);
    void getFoodListError(String errorMsg);
}
