package com.asd.caloriecount.model;

/**
 * Created by Name name on 2017/2/10.
 */
public class WeeksPlan {
    private String weekNo;
    private String weekDate;
    private String weekPlan;
    private boolean isPass;
    private boolean isSaved;

    public String getWeekPlan() {
        return weekPlan;
    }

    public void setWeekPlan(String weekPlan) {
        this.weekPlan = weekPlan;
    }

    public String getWeekNo() {
        return weekNo;
    }

    public void setWeekNo(String weekNo) {
        this.weekNo = weekNo;
    }

    public String getWeekDate() {
        return weekDate;
    }

    public void setWeekDate(String weekDate) {
        this.weekDate = weekDate;
    }

    public boolean isPass() {
        return isPass;
    }

    public void setIsPass(boolean isPass) {
        this.isPass = isPass;
    }

    public boolean isSaved() {
        return isSaved;
    }

    public void setIsSaved(boolean isSaved) {
        this.isSaved = isSaved;
    }

    public WeeksPlan() {
    }

    public WeeksPlan(String weekNo, String weekDate, String weekPlan, boolean isPass) {
        this.weekNo = weekNo;
        this.weekDate = weekDate;
        this.weekPlan = weekPlan;
        this.isPass = isPass;
    }
}
